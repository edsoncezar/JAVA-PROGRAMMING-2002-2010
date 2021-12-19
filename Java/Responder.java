// Programm 7-12a: BSP_ROOT/meinewebapp/WEB-INF/classes/meinemail/Responder.java
package meinemail;
import java.io.*; import java.util.*;
import javax.mail.*; import javax.mail.event.*; import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.*; import javax.servlet.http.*;
import java.sql.*;
public class Responder extends HttpServlet {
  final static String eigner = "jugs@uni-hohenheim.de";
  final static String host = "mailserv.uni-hohenheim.de";
  final static String user = "oops", passwd = "ax10w92Y";
  final static String mbox = "INBOX";
  static String meldungen = "";    
  static int mailzaehler;
  static int dauer = 600000;                  // 10 Minuten Mail-Polling (Default)
  final static String cfgfile = "c:\\temp\\respmonitor.cfg";  // Konfiguration
  final static String logfile = "c:\\temp\\respmonitor.log";  // Logbuch
  static boolean reply = true;
  static String betreff;
  static String text;
  static {
    new Thread(new MailMonitor()).start();
    logge("Responder geladen");
  }
  public void init() {
    String tmp = getInitParameter("dauer");  // aus Deployment-Descriptor
    dauer = (tmp == null) ? dauer : Integer.parseInt(tmp);
    logge("Responder initialisiert (Takt " + (dauer/1000) + "Sekunden)");
  }
  static class MailMonitor implements Runnable {
    Session s; Store store; Folder inbox;
    public void run() {
      try {
        s = Session.getDefaultInstance(System.getProperties());
        store = s.getStore("imap");
        initialisiere();              // inbox definieren und überwachen
        while (true) {              // Prüfe periodisch auf neu eingegangene Mail
          if (!store.isConnected()) {
            store.connect(host, user, passwd);
            inbox = store.getFolder(mbox);
            inbox.open(inbox.READ_WRITE);          // oder Folder.READ_WRITE
            mailboxbearbeiten(inbox.getMessages());
            inbox.addMessageCountListener(new MessageCountAdapter() {
              public void messagesAdded(MessageCountEvent event) {
                mailboxbearbeiten(event.getMessages()); 
              }
            });
          }
          if (!inbox.isOpen()) inbox.open(inbox.READ_WRITE);
          inbox.getMessageCount();  // Exception auslösen bei neuer Post
          Thread.sleep(dauer);
        }
      }
      catch(Exception ex) { logge("Exception in run() " + ex); }
    }
    void initialisiere() {
      try {
        BufferedReader in = new BufferedReader(new FileReader(cfgfile));
        reply = "true".equals(in.readLine()) ? true : false;
        betreff = in.readLine();
        text = ""; String tmp;
        while ((tmp = in.readLine()) != null) { text += tmp + "\n"; }
        in.close();
      }
      catch (IOException ex) { logge("Fehler bei Initialisierung!"); }
    }
    void mailboxbearbeiten(Message[] m) {
      try {
        for (int i = 0; i < m.length; i++) {
          if (! m[i].isSet(Flags.Flag.FLAGGED)) {
            if (reply) {
              Message antwort = m[i].reply(false);
              antwort.setFrom(new InternetAddress(eigner));
              antwort.setSubject(betreff + " [" + m[i].getSubject() + "]");
              antwort.setText(text);
              Transport.send(antwort);
            }
            m[i].setFlag(Flags.Flag.FLAGGED, true);
            logge("" + betreff + " [" + m[i].getSubject() + "]");
            mailzaehler++;
          }
        }
      }
      catch (Exception ex) { 
        logge("Fehler beim Bearbeiten der Mailbox! " + ex);
      }
    }
  }
  static void logge(String log) {// Logbuch (Datei und Speicher)
    try {
      FileWriter out = new FileWriter(logfile, true);
      String datum = new java.util.Date().toString();
      meldungen += datum + ": " + log + "<BR>";  // im Speicher
      out.write(datum + ": " + log + "\n");      // in Datei
      out.flush(); out.close();
    }
    catch (Exception ex) {}
  }
  public void doGet(HttpServletRequest rq, HttpServletResponse rs)
                                           throws ServletException, IOException {
    rs.setContentType("text/html");
    String tmp = rq.getParameter("reply");
    if ("Einschalten".equals(tmp)) reply = true;
    if ("Ausschalten".equals(tmp)) reply = false;
    tmp = rq.getParameter("betreff");
    betreff = tmp == null ? betreff : tmp ;
    tmp = rq.getParameter("text");
    text = tmp == null ? text : tmp ;
    PrintWriter html = rs.getWriter();
    html.print("<B>Responder ist " + (reply ? "" : "in") + "aktiv!</B><P>");
    html.print("<FORM>Betreff:<BR>" + 
               "<INPUT Type=text Name=betreff Value=" + betreff + " Size=60><P>" +
               "Antwort:<BR>" +
               "<TEXTAREA Name=text Cols=60 Rows=4>" + text + "</TEXTAREA><P>" +
               "<INPUT Type=submit Name=reply Value=\"Nachricht Ändern\"> " +
               "Responder <INPUT Type=submit Name=reply Value=Einschalten>" +
               "<INPUT Type=submit Name=reply Value=Ausschalten>" +
               "</FORM>"    );
    html.print(mailzaehler + " neue Mail(s)!<BR>");
    FileWriter cnf = new FileWriter(cfgfile);
    cnf.write("" + reply + "\n");      // in Datei
    cnf.write("" + betreff + "\n");      // in Datei
    cnf.write("" + text + "\n");      // in Datei
    cnf.close();
    if (!"".equals(meldungen)) html.print("<P>" + meldungen + "</P>");
  }
}