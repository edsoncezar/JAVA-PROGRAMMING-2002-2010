// Programm 7-11a: BSP_ROOT/meinewebapp/WEB-INF/classes/meinemail/Subscriber.java
package meinemail;
import java.io.*; import java.util.*;
import javax.mail.*; import javax.mail.event.*; import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.*; import javax.servlet.http.*;
import java.sql.*;
public class Subscriber extends HttpServlet {
  final static String host = "mailserv.uni-hohenheim.de";
  final static String user = "jugs", passwd = "smaltalk";
  final static String mbox = "INBOX";
  static String meldungen = "";    
  static int mailzaehler;
  static int dauer = 600000;                  // default 10 Minuten Mail-Polling
  final static String treiber = "org.gjt.mm.mysql.Driver";
  final static String jdbcurl = 
      "jdbc:mysql://localhost:3306/jdbcbuch?user=root&passwd=";
  final static String email = "email";        // Name des DB-Felds mit Mail-Adresse
  static Connection dbconnect; 
  static Statement statement;
  static {
    new Thread(new MailMonitor()).start();
    logge("Mail-Monitor gestartet");
  }
  public void init() {
    String tmp = getInitParameter("dauer");
    dauer = (tmp == null) ? dauer : Integer.parseInt(tmp);
    try {
      Class.forName(treiber);
      dbconnect = DriverManager.getConnection(jdbcurl);
      statement = dbconnect.createStatement();
    }
    catch (Exception ex) { 
      logge("Fehler beim Verbindungsaufbau mit DBS!");
    }
  }
  static class MailMonitor implements Runnable {
    Session s; Store store; Folder inbox;
    public void run() {
      try {
        s = Session.getDefaultInstance(System.getProperties());
        store = s.getStore("imap");
        while (true) {              // Prüfe periodisch auf neu eingegangene Mail
          if (!store.isConnected()) {
            store.connect(host, user, passwd);
            inbox = store.getFolder(mbox);
            inbox.open(inbox.READ_WRITE);             // oder Folder.READ_WRITE
            mailboxbearbeiten(inbox.getMessages());
            inbox.addMessageCountListener(new MessageCountAdapter() {
              public void messagesAdded(MessageCountEvent event) {
                mailboxbearbeiten(event.getMessages());     // nur neue Mail(s)
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
    void mailboxbearbeiten(Message[] m) {
      try {
        String from, betreff;
        for (int i = 0; i < m.length; i++) {
          from =
               ((InternetAddress) m[i].getFrom()[0]).getAddress();
          betreff = m[i].getSubject();
          if ("unsubscribe".equals(betreff.toLowerCase()))
            unsubscribe(from);
          else if ("subscribe".equals(betreff.toLowerCase()))
            subscribe(from);
          else 
            other(from, betreff);
          mailzaehler++;
          m[i].setFlag(Flags.Flag.DELETED, true);
          inbox.expunge();
        }
      }
      catch (Exception ex) { 
        logge("Fehler beim Lesen der Mailbox!");
      }
    }
    void subscribe(String from) throws SQLException {
      String sql = "SELECT Count(*) AS anzahl FROM abo WHERE " +
                  email + "='" + from.toLowerCase() + "'";
      logge("SQL " + sql);
      if (statement.execute(sql)) {;
        ResultSet rs = statement.getResultSet();
        rs.next();
        int anzahl = Integer.parseInt(rs.getString("anzahl"));
        if (anzahl < 1) {
          sql = "INSERT INTO abo(" + email + ") VALUES ('" +
               from.toLowerCase() + "')";
          statement.execute(sql);
          logge("SQL " + sql);
        }
      }
      logge("<B>neue Subscription</B> " + from);
    }
    void unsubscribe(String from) throws SQLException {
      String sql = "DELETE FROM abo WHERE " + email + "='" +
                  from.toLowerCase() + "'";
      statement.execute(sql); logge("SQL " + sql);
      logge("<B>gelöschte Subscription</B> " + from);
    }
    void other(String from, String betreff) {
      logge("<B>ungültiger Betreff</B> (" + betreff + ") " + from);
    }
  }
  static void logge(String log) {// Logbuch (Datei und Speicher)
    try {
      FileWriter out =
            new FileWriter("c:\\temp\\mailmonitor.log", true);
      String datum = new java.util.Date().toString();
      meldungen += datum + ": " + log + "<BR>";  // im Speicher
      out.write(datum + ": " + log + "\n");      // in Datei
      out.close();
    }
    catch (Exception ex) {}
  }
  public void doGet(HttpServletRequest rq, HttpServletResponse rs)
                                           throws ServletException, IOException {
    rs.setContentType("text/html");
    PrintWriter aus = rs.getWriter();
    aus.print("<DIV Align=center><H1>" + mailzaehler + "</H1>");
    aus.print("<H1>neue Mail(s)!</H1></DIV>");
    if (!"".equals(meldungen)) 
      aus.print("<P>" + meldungen + "</P>");
  }
}