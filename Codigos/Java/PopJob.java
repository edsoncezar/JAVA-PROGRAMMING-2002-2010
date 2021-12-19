// Name: Christoph Preining
// Prog: PopJob (POP3)
// Date: 2003-11-24

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.net.*;

/* ineffizient bei Spam (wann ma alle sofort holt)
class Mail
{
	private int nr;
	private String header;
	private String subject;
	private String from;
	private String to;
	private String text;

	public static Mail parse(String s)
	{
		Mail m = new Mail();
		// ...
		return m;
	}
	public void setNr(int nr)
	{
		this.nr = nr;
	}
	public void setHeader(String header)
	{
		this.header = header;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public void setFrom(String from)
	{
		this.from = from;
	}
	public void setTo(String to)
	{
		this.to = to;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public int getNr()
	{
		return nr;
	}
	public String getHeader()
	{
		return header;
	}
	public String getSubject()
	{
		return subject;
	}
	public String getFrom()
	{
		return from;
	}
	public String getTo()
	{
		return to;
	}
	public String getText()
	{
		return text;
	}
}
*/

public class PopJob extends JFrame
	implements ActionListener,ListSelectionListener
{
	private String title;
	private JTextField server;
	private JTextField user;
	private JPasswordField pass;
	private JButton BtnConnect;
	private JButton BtnDisconnect;
	private DefaultListModel listModel;
	private JList mailList;
	private JTextArea actMail;
	private JTextArea log;
	private Vector mails;
	private int countMails;
	private String old_server;
	private String old_user;
	private Socket socket;
	private int port;
	private BufferedReader in;
	private BufferedWriter out;

    public static void main(String args[])
	{
        PopJob pj = new PopJob();
	}

    private PopJob()
	{
		title         = "PopJob (simple mail program)";
		server        = new JTextField(10);
		user          = new JTextField(10);
		pass          = new JPasswordField(10);
		BtnConnect    = new JButton("connect");
		BtnDisconnect = new JButton("disconnect");
		listModel     = new DefaultListModel();
		mailList      = new JList(listModel);
		actMail       = new JTextArea(20,40);
		log           = new JTextArea(3,40);
		countMails    = 0;
		mails         = null;
		old_server    = "";
		old_user      = "";
		socket        = null;
		port          = 110;
		in            = null;
		out           = null;
        initGUI();
	}

    private void initGUI()
	{
        Container c               = getContentPane();
        JPanel login              = new JPanel(new FlowLayout());
		JPanel main               = new JPanel(new BorderLayout());
		JLabel LblServer          = new JLabel("server:");
		JLabel LblUser            = new JLabel("user:");
		JLabel LblPass            = new JLabel("pass:");
		JScrollPane scrollMailList= new JScrollPane(mailList);
		JScrollPane scrollActMail = new JScrollPane(actMail);
		JScrollPane scrollLog     = new JScrollPane(log);
		JSplitPane splitMain      = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,scrollMailList,scrollActMail);
		JSplitPane splitLog       = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,main,scrollLog);

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
		c.setLayout(new BorderLayout());

		login.add(LblServer);
		login.add(server);
		login.add(LblUser);
		login.add(user);
		login.add(LblPass);
		login.add(pass);
		login.add(BtnConnect);
		login.add(BtnDisconnect);

		main.add(splitMain);

		c.add(login,BorderLayout.NORTH);
		c.add(splitLog,BorderLayout.CENTER);

		actMail.setFont(new Font("Courier New",Font.PLAIN,14));
		actMail.setEditable(true);
		actMail.setText("");

		log.setFont(new Font("Courier New",Font.PLAIN,12));
		log.setEditable(false);
		log.setText("");
		log.setAutoscrolls(true);

		BtnConnect.addActionListener(this);
		BtnDisconnect.addActionListener(this);
		BtnDisconnect.setEnabled(false);

		mailList.addListSelectionListener(this);
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				try {
					disconnect();
				} catch (Exception ex) {
					ex.printStackTrace();
					log.append("\n"+ex.toString());
				}
				dispose();
				System.exit(0);
			}
		});
		
		getRootPane().setDefaultButton(BtnConnect);
		this.pack();
		this.setVisible(true);
	}

	public boolean sameConnection()
	{
		boolean theSame = server.equals(old_server) && (user.equals(old_user));
		if (theSame)
			log.append("\nalready connected!");
		return theSame;
	}

	public void connect(String s,String u,char[] p)
		throws Exception
	{
		String line = "";
		socket = new Socket(s,port);
		in  = new BufferedReader(
			new InputStreamReader(
				socket.getInputStream()
			)
		);
		out = new BufferedWriter(
			new OutputStreamWriter(
				socket.getOutputStream()
			)
		);
		line = in.readLine();	log.append("\n"+line);
		if (!line.startsWith("+OK"))
			throw new Exception("POP3:init");
		out.write("user "+u+"\n");
		out.flush();
		line = in.readLine();	log.append("\n"+line);
		if (!line.startsWith("+OK"))
			throw new Exception("POP3:user");
		out.write("pass "+new String(p)+"\n");
		out.flush();
		line = in.readLine();	log.append("\n"+line);
		if (!line.startsWith("+OK"))
			throw new Exception("POP3:pass");
		this.setTitle(title+" [connected to "+s+"]");
		BtnDisconnect.setEnabled(true);
	}
	
	public void disconnect()
		throws Exception
	{
		String line = "";
		if (socket.isConnected())
		{
			out.write("\nquit");
			out.flush();
			line = in.readLine();	log.append("\n"+line);
			if (!line.startsWith("+OK"))
				throw new Exception("POP3:quit");
			if (socket.isConnected())
				socket.close();
			this.setTitle(title+" [disconnected]");
			BtnDisconnect.setEnabled(false);
		}
	}

	public Vector getMails()
		throws Exception
	{
		String mail = "";
		if (!socket.isConnected())
			throw new Exception("not connected!");
		Vector vec = new Vector();
		String line = "";
		String tmp_title = this.getTitle();
		this.setTitle(tmp_title+" [looking for mails ...]");
		out.write("list\n");
		out.flush();
		line = in.readLine();	log.append("\n"+line);
		while (!line.startsWith("."))	// Mails zählen
		{
			if (!line.startsWith("+OK"))
			{
				//* vec.addElement(Mail.parse(line));
				countMails++;
			}
			line = in.readLine();	log.append("\n"+line);
		}
		for (int i=1; i<=countMails; i++)
		{
			out.write("top "+i+" 0\n");
			out.flush();
			line = in.readLine();	log.append("\n"+line);
			while (!line.startsWith("."))
			{
				if (line.startsWith("Return-Path:"))
					mail = line.substring(new String("Return-Path: ").length());
				if (line.startsWith("Subject:"))
					mail += " "+line.substring(new String("Subject: ").length());
				line = in.readLine();	log.append("\n"+line);
			}
			if (!mail.equals(""))
				vec.addElement(mail);
			mail = "";
		}
		this.setTitle(tmp_title);
		return vec;
	}
	
	public void showMail(int selected)
		throws Exception
	{
		String line = "";
		actMail.setText("");
		if (!socket.isConnected())
			throw new Exception("not connected!");
		out.write("retr "+selected+"\n");
		out.flush();
		line = in.readLine();	log.append("\n"+line);
		while (!line.startsWith("."))
		{
			if (!line.equals("+OK"))
				actMail.append(line+"\n");
			else
				log.append("\n"+line);
			line = in.readLine();
		}
	}

	public void valueChanged(ListSelectionEvent e)
	{
		/*
		Object selected = mailList.getSelectedValue();
		if (selected != null)
			showMail((Mail)selected);
		*/
		try {
			int selected = mailList.getSelectedIndex();
			if (selected != -1)
				showMail(selected+1);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.append("\n"+ex.toString());
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if ((e.getSource() == BtnConnect) && (!sameConnection()))
		{
			try {
				log.setText("");
				old_server = server.getText();
				old_user   = user.getText();
				connect(server.getText(),user.getText(),pass.getPassword());
				mails = getMails();
				mailList.setListData(mails);
				pass.setText("");
				//socket.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				log.append("\n"+ex.toString());
			}
		}
		else if (e.getSource() == BtnDisconnect)
		{
			try {
				disconnect();
				mailList.setListData(mails = new Vector());
				actMail.setText("");
			} catch (Exception ex) {
				ex.printStackTrace();
				log.append("\n"+ex.toString());
			}
		}
	}
}