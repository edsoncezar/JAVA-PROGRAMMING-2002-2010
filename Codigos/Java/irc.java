//	irc.java andibabi
//command line:java irc <channel> <nick> <server> <port>

//	call method propose(string) from textanalyzer to submit a output line
//	original program:
/*	Simple IRC Client
	by Pat Niemeyer (pat@icon-stl.net)

	Attributes are :
		rows, cols, 	- The size of the box
		backlines  		- number of lines of stored history for scroll back
		server, port 	- IRC server and port number
		channel			- IRC channel name
		nick 		- If you don't specify nick a random one is generated.
		bigfont 	- Set font to 24 point
		keepalive	- Respond to PINGs from server.  This is turned off
					whenever you leave the applet, causing the connection
					to be dropped after a few minutes of inactivity.
		The IRC RFC can be found at:

			http://www.cis.ohio-state.edu/htbin/rfc/rfc1459.html
*/

/* stuff to do, or ?maybe do
 debugging
 ?identd
 ?server message by number switch
 ?\002 = bold  \022=italic \031=underline
 ?action
 ?names window
*/

import java.awt.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.util.*;
//import TextAnalyzer;

public class irc extends Applet implements Runnable {//, TextCallBack {
	String version="0.40";
	boolean inAnApplet = true, ctcptog=true;
	Thread runner;
	Socket nc;
	PrintStream out;
	InputStream in;
	channel mainwindow;
	Hashtable channels, channelnick;
	TextAnalyzer taz;
	String server, channeldef, nick;
	int port;
	boolean bigfont = false, 
		keepalive = true;
	Font font;
	int backlines;
	private boolean STOPPED, DEBUG=true;
	int rows, cols;

	public String[] aRgh;

	public String getParameter(String name) {
		if (inAnApplet) return super.getParameter(name);
		//command line:java irc channel nick server port
		//matchlist inputparams=new matchlist("channel nick server port");
		//int r[]= inputparams.find(cmd);
		//if (r[0]<0) return null;
		//if (aRgh.length>=r[0]+1) return aRgh[r[0]];
		if (aRgh.length>=1 && name.equals("channel")) return aRgh[0];
		if (aRgh.length>=2 && name.equals("nick")) return aRgh[1];
		if (aRgh.length>=3 && name.equals("server")) return aRgh[2];
		if (aRgh.length>=4 && name.equals("port")) return aRgh[3];
		return null;}

	public void init() {
		String s;
		add(new Label("Java Irc"));
		s = getParameter("bigfont");
		if (s != null) bigfont=true;
		s = getParameter("keepalive");
		if (s != null) keepalive=true;
		s = getParameter("channel");
		if (s != null) channeldef = s; else channeldef = "#philosophy2";
		s = getParameter("nick"); 
		if (s != null) nick = s; else nick = randnick();
		s = getParameter("server");
		if (s != null) server = s; else server = "irc.calpoly.edu";
		s = getParameter("port");
		if (s != null) port = Integer.parseInt(s); else port = 6667;
		s = getParameter("backlines");
		if (s != null) backlines = Integer.parseInt(s); else backlines=20;
		s = getParameter("rows");
		if (s != null) rows = Integer.parseInt(s); else rows = 20;
		s = getParameter("cols");
		if (s != null) cols = Integer.parseInt(s); else cols = 80;		
		s = getParameter("textanalyzer");
		if (s != null) try {taz = (TextAnalyzer)Class.forName(s).newInstance();}
			catch (Exception e)  {System.out.println("Textanalyzer load exception: "+e);}
		else taz = new noanalyzer();

		System.out.println("Server: "+server+", port: "+port);

		if (bigfont)	font = new Font("Courier", Font.BOLD, 18);
		else		font = new Font("Courier", Font.BOLD, 12);

		mainwindow = new channel(this,nick, font, rows, cols, backlines);
		channels=new Hashtable();
		channelnick=new Hashtable();
		channels.put(nick,mainwindow);
			
		newchannel(channeldef);
			
	}

	boolean waiting=true,registered=false;

	private synchronized boolean connect() {
		System.out.println("Connecting...\n");
		route(nick,"Connecting to "+server+" port "+port+"\n");
		
		try {	nc = new Socket(server, port); }
		catch (Exception e) {
			System.out.println("Socket/NetworkClient exception: "+e);}
		
		if ( nc == null ) {
			mainwindow.print("Error connecting to "+server+"\n");
			return false; }

	        try {	out = new PrintStream(nc.getOutputStream());
			in = nc.getInputStream();}
		catch (Exception e) {System.out.println("Stream conversion error");};
		

		System.out.println("Sending registration...\n");
		output("user "+nick+" machine.net irc.server :http://www2.cruzio.com/~ababian/");
		output("nick "+nick);
		output("join "+channeldef);
		registered=false;

		System.out.println("Waiting for data...");
		mainwindow.print("Waiting for data...");

		return true;	}


	public void run() {
		if ( nick.equals("PROMPT")) 
		mainwindow.print("Enter your nickname below");
		while ( nick.equals("PROMPT") )
			try {wait();}
			catch (Exception e) {System.out.println("wait error");};

		connect();
		String str;
		while  (runner != null) {
			str = getline(); 
			if (str==null) { 
				route(nick,"Connection dropped"); 
				close(); 
				//while (nc==null) {};
				try{runner.sleep(3000);} catch (Exception e) {};//auto reconnect
				connect();
				continue;}

			if ( waiting ) 	waiting = false;

			System.out.println( str );
			try {parse(str);}
			catch (Exception e) {System.out.print("Parse exception:"+e+":"+str);} }

		System.out.println("Closing...");
		close();}


	static matchlist ctcpkeywords=new matchlist("PING FINGER USERINFO TIME VERSION CLIENTINFO SOURCE");

	private void ctcp(String nick,String line) {
		//reply to incoming ctcp message,  personal only, no channel ctcp
		if (!ctcptog) return;
		String s="";
		String n="NOTICE "+ nick+" :";
		route(this.nick,"["+nick+" "+line+"]");
		System.out.println("in ctcp:"+line);

		int r[]= ctcpkeywords.find(line.toUpperCase());
		//"PING FINGER USERINFO TIME VERSION CLIENTINFO SOURCE");//change in int
		System.out.println("ctcp whichkey:"+r[0]);
		switch(r[0]){
		case 0:	s=n+line;break; //ping
		case 1:	s=n+"\001FINGER finger this\001";break;
		case 2:	s=n+"\001USERINFO http://www2.cruzio.com/~ababian/index.html\001";break;
		case 3:	s=n+"\001TIME the time is now\001";break;
		case 4:	s=n+"\001VERSION my own private java irc "+version+", dude\001";break;
		case 5:	s=n+"\001CLIENTINFO PING FINGER USERINFO TIME VERSION CLIENTINFO SOURCE\001"; break;
		case 6:	s=n+"\001SOURCE http://www2.cruzio.com/~ababian/index.html\001"; break;
		default:} //ERRMSG
			output(s); 
		return; }


	static matchlist irckeywords=new matchlist("PING JOIN PART QUIT NICK MODE KICK PRIVMSG ERROR TOPIC NOTICE");

	private void parse(String str) {
		String head=null, body=null; //left over from old version, it also used nick
		String prefix, nick, whois, chan, command, params,pars[], trailing;
		String main=this.nick;
		prefix=nick=whois=chan=command=params=trailing=null;
	
		int sep;
		StringTokenizer sit=new StringTokenizer(str);
		if (sit.countTokens()==0) {route(main,"<?> "+str);return;}

		//parse according to rfc1459 
		//message = [":"  prefix " "] command params "\n"

		// Trim leading :, and it should, according to rfc
		if ( str.startsWith(":") )  {
			prefix=sit.nextToken().substring(1);

			//prefix = servername | nick [ "!" user ] [ "@" host] 
			if ( (sep = prefix.indexOf((int)'!')) !=- 1  ) {
				nick  = prefix.substring(0, sep).toLowerCase();
				whois = prefix.substring(sep+1); }}

		command=sit.nextToken();
		if (sit.hasMoreTokens()) params=sit.nextToken("");
			else params="*Error";//get all of rest

//		System.out.println("Prefix = :" + prefix+":");
//		System.out.println("command = :" + command+":");
//		System.out.println("params = :" + params);


		//params= [":" trailing | middle params]
		sep=params.indexOf(":");// trailing is after ":"
		if ( sep != -1 && params.length()>sep+1) {
			trailing=params.substring(sep+1);
			int tlast=trailing.length()-1;
			if (tlast>=0 && trailing.charAt(tlast)==13) 
				trailing=trailing.substring(0,tlast);
			params = params.substring(0, sep);} //may be length 0
		
		sit =new StringTokenizer(params);//parse middle params to an array
		int c=sit.countTokens();
		pars=new String[c];
		for (int i=0;i<c;i++) 
			pars[i]=sit.nextToken().trim();
		
		// variables using output
		if (c==0 ||pars[0]==null) chan=this.nick;
		else chan=pars[0];
		head=prefix+" "+command+" "+params;
		trailing=undospecialchars(trailing);
		body=trailing;


//		System.out.println("Chan = :" + chan+":");
//		System.out.println("Head = " + head);
//		System.out.println("Body = " + body);
//		System.out.println("Nick = " + nick);
		Hashtable nickhash;

		String n="***<"+nick+"> ";
		int r[]= irckeywords.find(command.toUpperCase());
		//"PING JOIN PART QUIT NICK MODE KICK PRIVMSG ERROR TOPIC NOTICE");
		switch(r[0]){
		case 0://ping
			System.out.println("Got PING request");
			if ( keepalive && !STOPPED ) output("pong "+body); return;
		case 1://join, Body has channel, but doesn't on PART
			chan=body.trim();
			joinchannel(nick,chan);
			route(chan,n+"<"+whois+"> has joined the channel"); return;
		case 2://part
			leavechannel(nick, chan,n+"has left the channel ("+body+")");return;
		case 3://quit
			if (nick==this.nick) return;
			for (Enumeration e = channelnick.elements() ; e.hasMoreElements() ;) { 
				nickhash=(Hashtable)e.nextElement();
				if (nickhash.containsKey(nick)){
					chan=(String)nickhash.get(nick);
					leavechannel(nick,chan,n+"has quit ("+body+")");}}
			 return;
		case 4://nick
			if (nick==null) nick=prefix;
			if (nick.equals(this.nick))  {//change my nick
				registered=true;
				changemainnick(body);}
			
			else //change other nick on every channel he's in 
			   for (Enumeration e = channelnick.elements() ; e.hasMoreElements() ;) { 
				nickhash=(Hashtable)e.nextElement();
				if (nickhash.containsKey(nick)){
					chan=(String)nickhash.get(nick);
     					leavechannel(nick,chan,n+"has changed his nickname to <"+body+">"); 
					joinchannel(body,chan);	}}
			return;
		case 5://mode
			if (nick != null) route(chan,n+"change mode: ["+params+"]"); return;
		case 6://kick
			leavechannel(pars[1],chan,n+"has kicked "+pars[1]+" off the channel ***"+body);return;
		case 7://privmsg
			//need do redo this conditional,  ctcp can be sent to channel, trailing starts with ctrl char
			if (chan.equals(this.nick)) {ctcp(nick, body); return;};
			route(chan,"<"+nick+"> "+body);	taz.put("<"+head+"> "+body);return;
		case 8://error
			if ( body.indexOf("Closing") != -1 ) route(chan, "*** Connection Closed ***"+body);return;
		case 9://TOPIC
			route(chan,"*** Topic changed by "+nick+" to "+body);
			if (channels.containsKey(chan)) 
				((channel)channels.get(chan)).setTitle(chan+": "+body);
			else System.out.println("***channel error"); return;
		case 10://Notice
			if (pars[0].equals("AUTH")) {route(main,"auth:"+body);return;}
			route(main, "["+body+"]"); return;
		}
		
		char[] co=command.toCharArray();
		//three digit numbers are server message codes
		if (pars.length>0 && co.length==3 && Character.isDigit(co[0]) && 
			Character.isDigit(co[1]) && Character.isDigit(co[2])) {
			int messagenumber=Integer.parseInt(command);
//			System.out.println("Numeric ");
			if (!registered && messagenumber<400) {//this is our nick confirmation
				changemainnick(pars[0]);
				registered=true;}
			if (pars.length>1 && messagenumber==332 && channels.containsKey(pars[1])){ //topic set 
				((channel)channels.get(pars[1])).setTitle(pars[1]+": "+body);}

			if (pars[0].equals(this.nick)){
				if (pars.length==1) {
					route(main,"<"+prefix+"> "+body);
					return;}
				if ("=@*".indexOf(pars[1])>-1 &&pars.length>2) {
					route(pars[2],"Names: "+body); 
					sit=new StringTokenizer(body);
					while (sit.hasMoreTokens()) 
						joinchannel(sit.nextToken(),pars[2]);
					return;}
				if (channels.containsKey(pars[1])){
					route(pars[1],"***"+body); 
					return;}}
			route(main,"<"+prefix+"> "+params+body); 
			return;}  //end numeric

		if (nick==null) nick="?";
		if (body!=null) {
			route(chan,"<"+nick+"> "+body);
			taz.put("<"+head+"> "+body);}
		else	route(chan,"["+head+"] ");}



	static 	matchlist inputcommands=new matchlist("join part quit msg ctcp names connect me nick");

	public synchronized void EntryLine(String channelname,String s) {

//		System.out.println("In EntryLine, s = "+s);
		if ( (s == null) || ( s.length() == 0 ) )
			return;

		if ( nick.equals("PROMPT") ) {
			String n = new String(s); 
			System.out.println("nick = PROMPT, got entry = "+n);
			if ( n.length() > 0 ) {
				nick = n;
				notify();
				System.out.println("Did notify from Entryline");}
			return; }

		if ( !s.startsWith("/") ) {
			route(channelname,">>>> "+s);
			output("privmsg "+channelname+" :"+s); }
		else {//commands ******2do:part....
			s=s.substring(1);
			StringTokenizer sit=new StringTokenizer(s);
			String cmd = sit.nextToken();
			int r[]= inputcommands.find(cmd);
			Hashtable nickhash;

			switch(r[0]){
			case 0://join
				channelname=sit.nextToken().toLowerCase().trim();
				newchannel(channelname);
				s="join "+channelname; break; 
			case 1://part
			case 2://quit
				break;//these are already in correct form,  could do window processing
			case 3://msg
				s="privmsg "+sit.nextToken()+" :"+sit.nextToken("").trim();break;
			case 4://ctcp
				String arg1=sit.nextToken();
				if (arg1.equals("tog")){
					ctcptog=!ctcptog;
					mainwindow.print("ctcp tog"+ctcptog);
					return;}
				s="privmsg "+arg1+" :\001"+sit.nextToken("").trim()+"\001";break;
			case 5://names
				nickhash=(Hashtable)channelnick.get(channelname);
				String nameslist="Names:";
				if (nickhash==null) {System.out.println("Names error:"+channelname); return;}
				for (Enumeration e = nickhash.keys() ; e.hasMoreElements() ;) 
					nameslist=nameslist+" "+(String)e.nextElement();
				route(channelname,nameslist);
				return;
			case 6://connect
				close();
				if (sit.hasMoreTokens()) server=sit.nextToken();
				if (sit.hasMoreTokens()) port=Integer.parseInt(sit.nextToken());
				connect();
				//notify();
				return;
			case 7://me (action)
				s="privmsg "+channelname+" :\001ACTION "+sit.nextToken("").trim()+"\001";break;
			case 8://nick
				if (nc==null) {//otherwise send message and change after confirmation
					changemainnick(sit.nextToken());
					return;}
			}
			output(s);
			route(channelname,"***"+s);}}



	private void newchannel(String channelname){//what about channel failure?  kill manually
		if (channels.containsKey(channelname)) 
			if (channels.get(channelname)!=null) return;  //already exitted
			else channels.remove(channelname); 

		channelname=channelname.toLowerCase();
		channels.put(channelname,new channel(this,channelname,font,rows,cols,backlines));
		Hashtable nickhash=new Hashtable();
		channelnick.put(channelname,nickhash);}


	private void changemainnick(String newnick){
		newnick=newnick.trim();
		if (channels.containsKey(this.nick)) channels.remove(this.nick);
		channels.put(newnick,mainwindow);
		mainwindow.setTitle(newnick);
		this.nick=newnick;}


	private void joinchannel(String nick, String chan){
		nick=nick.toLowerCase();
		chan=chan.toLowerCase();
		if ("@+".indexOf(nick.charAt(0))!=-1) nick=nick.substring(1);
		if (!channelnick.containsKey(chan)) {System.out.println("join bogus channel:"+nick+":"+chan);}else
		((Hashtable)(channelnick.get(chan))).put(nick,chan);}//addnick to channelnick list
		//watch out for duplications on reentries
		

	private void leavechannel(String nick, String channelname, String mess){
		channelname=channelname.toLowerCase().trim();
		nick=nick.toLowerCase();
		if (nick.equals(this.nick)) {
			if (channels.containsKey(channelname))
				route(channelname,mess+" :"+channelname+"ooh i hate that!");
			else route(this.nick,mess+" :"+channelname);//channel may be gone!
			channelnick.put(channelname,new Hashtable());}//clear nick hastable on exit;
		else route(channelname,mess);
		Hashtable nickhash=(Hashtable)channelnick.get(channelname);
		if (nickhash==null || !nickhash.containsKey(nick)) 
			System.out.println("***leave channel error"+nick+channelname); else
		nickhash.remove(nick);}//addnick to channelnick list


	private void route(String chan,String s){
		chan=chan.toLowerCase().trim();
		if (channels.containsKey(chan)) ((channel)channels.get(chan)).print(s); 
		else {mainwindow.print(s); System.out.println("***channel error:"+chan);}
		}
	
	public void propose(String s) {//proposed inputline,//need to do a route
		mainwindow.propose(s); }

	private void output(String s){//may eventually save to file or stop printing twice
		try {out.println(specialchars(s));} catch (Exception e) {System.out.println("Output error:"+e);}
		System.out.println(s);}
	

	private String specialchars(String s){ //evaluate spacial chars in line starting with '\'
		//  eg \999,
		int i=0;
		while ((i=s.indexOf("\\",i))!=-1){
			if ((i+3<s.length()) && Character.isDigit(s.charAt(i+1)) &&
				Character.isDigit(s.charAt(i+2)) && Character.isDigit(s.charAt(i+3))) {
				if (i+4==s.length()) 
					s=s.substring(0,i)+(char)(Integer.parseInt(s.substring(i+1)));
				else 	s=s.substring(0,i)+(char)(Integer.parseInt(s.substring(i+1,i+4)))+s.substring(i+4);
				continue;}
			i++;}
		return s;}

	private String undospecialchars(String s){ //unconvert spacial chars to '\ddd'
		if (s==null) return s;
		int i;
		char ch;

		StringBuffer s2=new StringBuffer();
		for (i=0;i<s.length();i++){
			ch=s.charAt(i);
			if (ch>=32 || ch==13) s2.append(ch); //Character.isDefined(ch) is not defined!
			else s2.append("\\"+((int)ch));}
				
		return s2.toString();}



	private String getline() {
	    StringBuffer buff = new StringBuffer();
		int ch;
		if (in==null) return (null);

		try {	while ( (ch = in.read()) != -1 ) {
				if (ch == '\n')	break;
				else buff.append((char)ch);}}
		catch (Exception e) {	System.out.println("in.read exception: " + e);}

		if ( buff.length() > 0 )
			return(buff.toString());
		else	return ( null );}


	public void start() {
		STOPPED=false;
		if ( runner == null ) {
			runner = new Thread(this);
			runner.start();}}

	public void stop() {
		STOPPED=true;}

	void close() {
		if (nc==null) return;
		try {	out.close(); in.close(); nc.close();}
		catch (Exception e) {System.out.println("close server exception: "+e);}
		nc = null;   }

	// (almost) random 9 char nickname
	private String randnick() {
		StringBuffer s = new StringBuffer("Foo");
		for (int i=0; i<6; i++) {
			char ch = (char)(Math.random()*26 + 65);
			s.append(new Character(ch).toString());	}
		return(s.toString());	}


	public boolean handleEvent(Event event) {
//		System.out.print(" event:"+event+".  ");
                  if (event.id == Event.WINDOW_DESTROY) {
			if (channels.contains(event.arg) && event.arg!=mainwindow){
				channel c=(channel)event.arg;
				output("PART "+c.name);
				channels.remove(c.name);
				leavechannel(this.nick,c.name,this.nick+" has exited channel "+c.name); 
				c.dispose();}
			else if (inAnApplet) mainwindow.dispose();
			else System.exit(0);}
                  return super.handleEvent(event);}


	public static void main(String args[]) {
		irc i=new irc();
		if (args.length==0){System.out.println("command line:java irc <channel> [<nick> [<server> [<port>]]]");
			System.exit(0);}

		else if (args[0]!=null && "-?_-help_/?".indexOf( args[0].toLowerCase())!=-1){
			System.out.println("command line:java irc <channel> [<nick> [<server> [<port>]]]");
			System.exit(0);}
		i.aRgh=args;
                i.inAnApplet = false;
		Frame f=new Frame();
		f.add(i);
		i.init();
		i.start();}

}


//******************************************

class channel extends Frame {
/* to do
   auto focus, 
   destroy
   history
*/ //a window to display an irc channel
	irc ircclient;
	TextArea ta;
	TextField tf;
	String title;
	int	backlines;
	String[] history;
	int	historycurrent,historynext;
	FontMetrics fm;
	public String name="main";

	channel(irc irccli,String named,Font font,int rows,int cols,int backlines){
		super(named);
		ircclient=irccli;
		name=new String(named);
		setFont(font);
		fm=getFontMetrics(font);
		setLayout(new BorderLayout());
		setResizable(true);
		setTitle(name);
		this.backlines=backlines;
		history=new String[backlines];
		for (int i=0;i<backlines;history[i++]="");
		historynext=historycurrent=0;
 		ta = new TextArea(rows,cols);
		ta.setBackground(Color.white);
		ta.setEditable(false);
		tf=new TextField(cols);
		ta.setFont(font);
		tf.setFont(font);
		add("Center",ta);
		add("South",tf);
		show();
		pack();

		tf.requestFocus();
		}

public synchronized void print(String s){
	StringTokenizer st=new StringTokenizer(s," ");
	int c=ta.size().width-25;

	// *****kludge alert  textarea keeps filling up,  so periodically clear it
	String alltext=ta.getText();
//	System.out.println( "chars in box:"+alltext.length()+", width"+c );
	if (alltext.length()>20000) //  ****i was i knew what this should be
		ta.setText(alltext.substring(19000));

	String w;
	StringBuffer line=new StringBuffer("");

	// cut up into lines
	while (st.hasMoreTokens()){
		w=new String(st.nextToken());
		if (fm.stringWidth(line.toString()+w) >c ){
			ta.appendText(line.toString()+"\n");

			line.setLength(0);}
		line.append(" "+w);}
	ta.appendText(line.toString()+"\n");}


	public boolean handleEvent(Event event) {
                  if (event.id == Event.WINDOW_DESTROY) {
			ircclient.handleEvent(new Event(ircclient,Event.WINDOW_DESTROY,this));}
                  return super.handleEvent(event);}


	public void propose(String s) {//proposed inputline,
	tf.setText(s); }


	public void update(Graphics g) {
		ta.update(g);
		tf.update(g);
	}
	public void paint(Graphics g) {
		ta.paint(g);
		tf.paint(g);
	}


	public boolean gotFocus(Event evt,Object what){
		if (what!=tf){
			tf.requestFocus(); 
			return true;}
		return super.gotFocus(evt,what);}


	public boolean mouseDown(Event eve,int x, int y) {
		ta.mouseDown(eve,x,y);
		tf.mouseDown(eve,x,y); 
//		tf.requestFocus();
		return true;
	}
	public boolean mouseUp(Event eve,int x, int y) {
		tf.mouseUp(eve,x,y); 
		return true; 
	}
	public boolean mouseDrag(Event eve,int x, int y) {
		ta.mouseDrag(eve,x,y); 
		return true;
	}
	public boolean keyDown(Event evt, int key) {
		if (key==Event.UP){
			historycurrent=(historycurrent-1+backlines)%backlines;
			tf.setText(history[historycurrent]);
			return true;}
		if (key==Event.DOWN){
			historycurrent=(historycurrent+1)%backlines;
			tf.setText(history[historycurrent]);
			return true;}
		if (key !='\n') return false;
		String text = tf.getText();
		history[historynext]=text;
		historycurrent=historynext=(historynext+1)%backlines;
		ircclient.EntryLine(name,text);
		tf.setText("");
		return true;}

}


//*********************************
class matchlist {
/**a class to do simple text matching 
 store a list of keywords to look for

 constructor(string of all keywords [, separator default=" "]
 findin(S) returns int[ index of keyword(or -1), its position in <line>, and length of result]
 sub(int[],S line,S insert) substitutes <insert> in line according to find info int[]
 sub(S line,S insert,B forall) finds and matches all instances(T) or once(F)
 key(int) return the indexed keyword
 add(S) add a key
*/

String keyword[];  //storing all items to match rules, each array knows its length
Hashtable hashkey;

public String keyword(int i) {return keyword[i];};

matchlist(String keys, String separator){
	keyword=parsetoarray(keys,separator); 
	hashkey=new Hashtable();
	for (int i=0;i<keyword.length;i++)
		hashkey.put(keyword[i],new Integer(i));}

public int[] find(String line) {
/** returns array of [which element matched, position of match, length of match]
 sub(r,line [,insert])  to substitute a stored element
 sub(line,insert,[forall]) does find and insert
 there may be multiples matches stored
a hashtable lookup on each word in line for key, whole words only*/
	int[] r={-1,-1,-1};
	if (line==null) return r;

	StringTokenizer sly=new StringTokenizer(line," \001\002\003\004\005\006");
	String thisword;
	while (sly.hasMoreTokens()) {
		thisword=sly.nextToken();
		r=findoneword(line, thisword);
		if (r[0]>-1) return r;}
	return r;}

protected int[] findoneword(String line,String word){
		int[] r={-1,-1,-1};
		if (hashkey.containsKey(word)) {
			r[0]=((Integer)hashkey.get(word)).intValue();
			r[1]=line.indexOf(word);
			r[2]=word.length();}
		return r;}


public int[] slowfind(String line) {
//**slowfind is the 
	int[] r=new int[3];
	r[2]=r[1]=r[0]=-1;
	if (line==null) return r;
	int sep,i;
	for (i=0;i<keyword.length;i++)
		if ( (sep  = line.indexOf(keyword[i])) != -1 ) {
			r[0]=i;
			r[1]=sep;
			r[2]=keyword[i].length();
			return r;}
	return r;}


public String sub(int[] r,String line, String insert){
	// insert into found spot: r[1]=position of beginning, r[2]=length of word
	return (line.substring(r[1])+insert+line.substring(r[1]+r[2]));};

public String sub(String line,String insert, boolean manytimes){
	String out=new String(line);
	int[] r=find(line);
	while (r[0]>=0) {//found
		out=sub(r,line,insert);
		if (!manytimes) break;
		r=find(line);}
	return out;}
	

//default argument forms:

matchlist(String keys) {this(keys," ");};

public String sub(int[] r,String line) {return sub(r,line,"");};

public String sub(String line,String insert) {return sub(line,insert,false);};

public String sub(String line) {return sub(line,"",false);};

// helper subfunctions:

	protected String[] parsetoarray(String rawlist,String separator){
//  break the string into an array of strings, which were separated by..
StringTokenizer sit=new StringTokenizer(rawlist, separator);
int c=sit.countTokens();
String[] stimpy=new String[c];
for (int i=0;i<c; i++) 
	stimpy[i]=new String(sit.nextToken()); 
return stimpy;}



matchlist add(String key) {
	String[] ktemp=new String[keyword.length+1];
	ktemp[keyword.length]=key;
	keyword=ktemp;
	return this;} // add a rule; uses matche's


matchlist delete(String[] rule) {return this;}//doesn't work


}

//****************************************************
//TextAnalyzer class interface
//the irc loads a TextAnalyzer class
// receives data of form "<nick!user@host> line of info"

 interface TextAnalyzer {
	void put(String s); }
//****************************************************
//the irc loads a TextAnalyzer class 
// receives data of form "<nick!user@host> line of info"
// this one does nothung

class noanalyzer implements TextAnalyzer {
	public synchronized  void put(String s) {}; }

