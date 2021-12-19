import org.jibble.pircbot.*;
import org.ilrt.inkling.api.*;
import org.ilrt.inkling.query.*;
import java.util.*;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.Transliterator;

public class RDFBot extends PircBot {
    
    public RDFBot() {
        this.setName("wh4");
    }


    public static void main(String[] args) throws Exception {
        
        // Now start our bot up.
        RDFBot bot = new RDFBot();
	bot.setEncoding("UTF-8");

        // Enable debugging output.
        bot.setVerbose(false);
        
        // Connect to the IRC server.
        bot.connect("irc.freenode.net");

        // Join the #cs channel.
        bot.joinChannel("#whwhwhwh");
        bot.joinChannel("#foaf");
       bot.joinChannel("#swhack");
        bot.joinChannel("#rdfig");
//        bot.joinChannel("#colston2004");
    }
    
    public void onPrivateMessage(String sender, String login, String
hostname, String message) {

	onMessage(sender,sender,login,hostname,message);


    }

    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {

/*

more ideas:
everything about someone by nick/name 
done wordnet
who is mentioning me
paths interface - no workie for some reason

*/

	        if((message.startsWith("!j2l"))||(message.startsWith("!translate"))){
		transJ2L(message,channel);
		}
	        if (message.startsWith("!l2h")) {
		transL2J(message,channel,false);
		}
	        if (message.startsWith("!l2k")) {
		transL2J(message,channel,true);
		}

	        if (message.startsWith("!enc")) {
		setEnc(message,channel);
		}
	        if (message.startsWith("!getenc")) {
		getEnc(message,channel);
		}

	        if (message.startsWith("!airport")) {
		getAirportsByLocation(message,channel);
		}

	        if (message.startsWith("!city")) {
		getCitiesByLocation(message,channel);
		}

	        if (message.startsWith("!near ")) {
		getCitiesByLatLong(message,channel);
		}

	        if (message.startsWith("!nearto")) {
		getCitiesByNearCity(message,channel);
		}

	        if (message.startsWith("!iata")) {
		getAirportsByIata(message,channel);
		}

	        if (message.startsWith("!pic ")) {
		getPicsByName(message,channel);
		}

	        if (message.startsWith("!wn")) {
		getWordnet(message,channel);
		}

	        if (message.startsWith("!knows ")) {
		getKnows(message,channel);
		}
/*
	        if (message.startsWith(".knownby")) {
		getKnownBy(message,channel);
		}
*/
	        if (message.startsWith("!path")) {
		sm(channel,"try !picpath <name1>,<name2> to "+
"find connections between people via images");

		}


	        if (message.startsWith("!picpath")) {
		getPathsByNames(message,channel,false);
		}

/*

	        if (message.startsWith("!knowspath")) {
		getKnowsPathsByNames(message,channel,false);
		}

*/
	        if (message.startsWith("!pathf")) {
		getPathsByNames(message,channel,true);
		}

	        if (message.startsWith("!phone")) {
		getPhoneByCountry(message,channel);
		}

	        if (message.startsWith("!bydate")) {
		getEvents(message,channel,true);
		}
	        if (message.startsWith("!event")) {
		getEvents(message,channel,false);
		}

	        if (message.startsWith("!foaf:")) {
		getFoafData(message,channel);
		}

	        if (message.startsWith("!foafinv:")) {
		getFoafDataRev(message,channel);
		}

	        if (message.startsWith("!interest")) {
		getInterest(message,channel);
		}

	        if (message.startsWith("!chump")) {
		getChumpUrl(message,channel);
		}

	        if (message.startsWith("!london")) {
		getPubUrl(message,channel);
		}

	        if (message.startsWith("!source")) {
		getSource(message,channel);
		}

	        if (message.startsWith("!help")) {
		help(channel);
		}
	        if (message.startsWith("wh4, help")) {
		help(channel);
		}

	        if (message.startsWith("!bye")) {
//		quitServer("so...tired...");
		quitServer("I..know...EVERYTHING!!!");
		System.exit(1);
		}

    }


/**

help

*/

   public void help(String channel){

	sm(channel,"I'm a very experimental RDF-webservice-based bot by libby.miller@bristol.ac.uk, based on the pircbot api. More info at http://swordfish.rdfweb.org/discovery/2003/10/whwhwhwh/");
 	sm(channel,"you can find pictures of people, places, things and "+
"taken on dates, e.g. !pic sean, some beer or !pic in bristol, on 2004-03 "+ 
"or combinations. !picpath <name1>, <name2> gives you codepiction paths "+
"between people. There's also: !airport <place>;  !iata <iata code> "+
"!wn <wordnet term>; !phone <countryname>; !pic <name>; !chump <url>; "+
"!london <tube station>");

   }


/**

Get RDF from a url using a remote source and then query it using a
squish query and write back to the channel.

*/

    public void getRDF(String url, String q, String channel){

	getRDF(url,q,channel,-1);
    }

    public void getRDF(String url, String q, String channel, int limit){

	Graph g = new Graph(url, Util.RDFXML);
	if(q!=null && (!q.equals(""))){

	int count=0;
	if(limit==-1){
	limit=count;
	}

		try{

		g.load();

		java.sql.ResultSet r=g.askSquish(q);
		while(r.next()){

		if(count<=limit){
		count++;
//		StringBuffer sb = new StringBuffer();

		String sb="";
			Vector v=((org.ilrt.inkling.query.ResultSet)r).getColumnNames();
			Enumeration e=v.elements();
			while(e.hasMoreElements()){
			String var=(String)e.nextElement();
//			sb.append((String)r.getString(var)+" ");
			String gg=(String)r.getString(var);
			//System.out.println(gg);
			sb=sb+gg+" ";
			}

			if(!sb.trim().equals("")){
			sm(channel," "+sb);
			}
//else{
//			sendMessage(channel,"no results found, sorry");
//			}


		}

		}

		if(count==0){
			sm(channel,"no results found, sorry");
		}

		}catch(Exception e){
		System.err.println(e);
		e.printStackTrace();
		sm(channel,"nothing found");
		}


	}

    }


/**

for pubs


*/

    public void getPubRDF(String url, String q, String channel, int
limit){

	Graph g = new Graph(url, Util.RDFXML);
	if(q!=null && (!q.equals(""))){

	int count=0;
	if(limit==-1){
	limit=count;
	}

		try{

		g.load();

		java.sql.ResultSet r=g.askSquish(q);

		StringBuffer sb = new StringBuffer();
		while(r.next()){

		if(count<=limit){
		count++;

			Vector v=((org.ilrt.inkling.query.ResultSet)r).getColumnNames();
			Enumeration e=v.elements();
			while(e.hasMoreElements()){
			String var=(String)e.nextElement();
			sb.append((String)r.getString(var)+"; ");
			}


		   }

			if(!sb.toString().trim().equals("")&&count%5==0){
			sm(channel,sb.toString());
			sb=new StringBuffer();
			}

		}

		sm(channel,sb.toString());

		if(count==0){
			sm(channel,"no results found, sorry");
		}

		}catch(Exception e){
		System.err.println(e);
		e.printStackTrace();
		sm(channel,"nothing found");
		}

	}

    }


/**

get any foaf term with domain Person or agent where you expect a name to
also be present

*/


public void getFoafData(String message, String channel){


boolean bynick=false;

//System.out.println(message+"[1]");

int z=message.indexOf("by nick");
if (z!=-1){
bynick=true;
message=message.substring(0,z)+message.substring(z+7);
}


int i=message.indexOf(" ");
int j=message.indexOf(":");
String q=null;
String f=null;

	if(i!=-1 && j!=-1){

	q=message.substring(i+1).trim();
	f=message.substring(j+1,i).trim();

	

String sq="";

if(f.equals("name")){

	if(bynick){
	sq="select ?name, ?nick where "+
	" (foaf:name ?per ?name) "+
	" (foaf:nick ?per ?nick) "+
	" and ?nick ~ '"+q+"' ";
	}else{
	sq="select ?name where "+
	" (foaf:name ?per ?name) "+
	" and ?name ~ '"+q+"' ";
	}
	sq=sq+" using foaf for http://xmlns.com/foaf/0.1/";

}else{

	sq="select ?foafterm, ?name where "+
	" (foaf:"+f+" ?per ?foafterm) ";

	if(bynick){
	sq=sq+" (foaf:nick ?per ?name) "+
	" and ?name ~ '"+q+"' ";
	}else{
	sq=sq+" (foaf:name ?per ?name) "+
	" and ?name ~ '"+q+"' ";
	}
	sq=sq+" using foaf for http://xmlns.com/foaf/0.1/";
}


	String
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/rdfxmlquery.jsp?query="+java.net.URLEncoder.encode(sq);

	if(!f.equals("mbox")){
///fixme - ailto etc

	getRDF(url,sq,channel,10);

	}

	}else{
	sm(channel,"try e.g. !foaf:weblog <name> to find weblogs of people");
	}


}


/**

reverse of foaf->pred, pred->foaf

*/

public void getFoafDataRev(String message, String channel){

int i=message.indexOf(" ");
int j=message.indexOf(":");
String q=null;
String f=null;

	if(i!=-1 && j!=-1){

	q=message.substring(i+1);
	f=message.substring(j+1,i);

	

String sq="select ?foafrev, ?name where "+
" (foaf:name ?per ?name) "+
" (foaf:"+f+" ?foafrev ?per) "+
" and ?name ~ '"+q+"' "+
" using foaf for http://xmlns.com/foaf/0.1/";

System.out.println(sq);

	String
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/rdfxmlquery.jsp?query="+java.net.URLEncoder.encode(sq);

	if(!f.equals("mbox")){
///fixme - ailto etc

	getRDF(url,sq,channel,10);

	}

	}else{
	sm(channel,"try !foaf:weblog name to find weblogs of people");
	}


}


/**

people who have a foaf:interest in something

*/

public void getInterest(String message, String channel){

int i=message.indexOf(" ");
String q=null;

	if(i!=-1){

	q=message.substring(i+1);
	

String sq="select ?interest, ?name where "+
" (foaf:name ?per ?name) "+
" (foaf:interest ?per ?interest) "+
" and ?interest ~ '"+q+"' "+
" using foaf for http://xmlns.com/foaf/0.1/";

System.out.println(sq);

	String
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/rdfxmlquery.jsp?query="+java.net.URLEncoder.encode(sq);

	getRDF(url,sq,channel,10);

	}else{
	sm(channel,"try !foaf:weblog name to find weblogs of people");
	}


}


/**

Takes a message and then searches the remote data base by string for
airports, finding lat/long, location and country

*/

public void getAirportsByLocation(String message, String channel){

int i=message.indexOf(" ");
String q=null;

	if(i!=-1){
	q=message.substring(i+1);
	String
url="http://swordfish.rdfweb.org/discovery/2003/06/airports/xmlrdf.jsp?query="+java.net.URLEncoder.encode(q);

	String sq="select ?loc, ?la, ?lo, ?co, ?iata where "+
	" (air:location ?airport ?loc) "+
	" (pos:lat ?airport ?la) "+
	" (pos:long ?airport ?lo) "+
	" (air:iataCode ?airport ?iata) "+
	" (vcard:country ?airport ?co) "+
	" using air for http://www.daml.org/2001/10/html/airport-ont# "+
	" pos for http://www.w3.org/2003/01/geo/wgs84_pos# "+
	" vcard for http://www.w3.org/vcard-rdf/3.0# ";

	getRDF(url,sq,channel,10);

	}else{
	sm(channel,"try !airport bristol to find airports near Bristol");
	}


}


/**

cities by name

*/
public void getCitiesByLocation(String message, String channel){

int i=message.indexOf(" ");
String q=null;

	if(i!=-1){
	q=message.substring(i+1);
	String
url="http://swordfish.rdfweb.org/discovery/2003/11/cities/xmlrdf.jsp?query="+java.net.URLEncoder.encode(q);

	String sq="select ?loc, ?la, ?lo, ?co where "+
	" (air:location ?city ?loc) "+
	" (pos:lat ?city ?la) "+
	" (pos:long ?city ?lo) "+
	" (vcard:country ?city ?co) "+
	" using air for http://www.daml.org/2001/10/html/airport-ont# "+
	" pos for http://www.w3.org/2003/01/geo/wgs84_pos# "+
	" vcard for http://www.w3.org/vcard-rdf/3.0# ";

	getRDF(url,sq,channel,10);

	}else{
	sm(channel,"try !city bristol names and locations of cities with a name like 'bristol'");
	}


}



/**

cities by lat and long

*/
public void getCitiesByLatLong(String message, String channel){

int i=message.indexOf(" ");
String lat=null;
String lo=null;

	if(i!=-1){
	lat=message.substring(i+1);

	int j=lat.indexOf(",");

	if(j!=-1){

	lo=lat.substring(j+1);
	lat=lat.substring(0,j).trim();
	lo=lo.trim();

	String
url="http://swordfish.rdfweb.org/discovery/2003/11/cities/nearxmlrdf.jsp?lat="+lat+"&lo="+lo;

	String sq="select ?loc, ?la, ?lo, ?co where "+
	" (air:location ?city ?loc) "+
	" (pos:lat ?city ?la) "+
	" (pos:long ?city ?lo) "+
	" (vcard:country ?city ?co) "+
	" using air for http://www.daml.org/2001/10/html/airport-ont# "+
	" pos for http://www.w3.org/2003/01/geo/wgs84_pos# "+
	" vcard for http://www.w3.org/vcard-rdf/3.0# ";

	getRDF(url,sq,channel,10);


	}else{
	sm(channel,"try !near 51,-2 to find locations near that lat/long");
	}

	}else{
	sm(channel,"try !near 51,-2 to find locations near that lat/long");
	}


}

/**

cities by name

*/
public void getCitiesByNearCity(String message, String channel){

int i=message.indexOf(" ");
String q=null;
String qq=null;
	String
url="http://swordfish.rdfweb.org/discovery/2003/11/cities/nearcityxmlrdf.jsp?query=";

	if(i!=-1){
	q=message.substring(i+1);

	int j=q.indexOf(",");

	if(j!=-1){

	qq=q.substring(j+1).trim();
	q=q.substring(0,j).trim();


url=url+java.net.URLEncoder.encode(q)+"&degree="+java.net.URLEncoder.encode(qq);

	}else{

	url=url+java.net.URLEncoder.encode(q);

	}


	String sq="select ?loc, ?la, ?lo, ?co where "+
	" (air:location ?city ?loc) "+
	" (pos:lat ?city ?la) "+
	" (pos:long ?city ?lo) "+
	" (vcard:country ?city ?co) "+
	" using air for http://www.daml.org/2001/10/html/airport-ont# "+
	" pos for http://www.w3.org/2003/01/geo/wgs84_pos# "+
	" vcard for http://www.w3.org/vcard-rdf/3.0# ";

	getRDF(url,sq,channel,10);

	}else{
	sm(channel,"try !nearto bristol for names and locations of cities which are near by bristol");
	}


}




/**

similar to above but search by iata code

*/

public void getAirportsByIata(String message,String channel){

int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1);
	String url="http://jibbering.com/rdf/airports.1?"+q;

	String sq="select ?loc, ?la, ?lo where "+
	" (air:location ?airport ?loc) "+
	" (pos:lat ?airport ?la) "+
	" (pos:long ?airport ?lo) "+
	" using air for http://www.daml.org/2001/10/html/airport-ont# "+
	" pos for http://www.w3.org/2003/01/geo/wgs84_pos# "+
	" vcard for http://www.w3.org/vcard-rdf/3.0# ";

	getRDF(url,sq,channel);

	}else{
	sm(channel,"try .iata BRS to find location info for an airport IATA code");

	}


}

/**

better version - muliple people, plus places and things

something like: !pic <name>, and <name>, a <thing>, at <place>, on <date>



*/


public void getPicsByName(String message,String channel){

int limit=4;
Vector v = Util.splitByString(message, ",");

Vector names= new Vector();
Vector things= new Vector();
String place=null;
String date=null;

Enumeration e = v.elements();
int i=0;
	while(e.hasMoreElements()){

	String part = (String)e.nextElement();

//System.out.println("part "+part);

		if(i==0){

		part=part.substring(5).trim();

		}

			if(part.startsWith("and ")){
			names.addElement(part.substring(4).trim());
			}
			else if(part.startsWith("a ")){
			things.addElement(part.substring(2).trim());
			}
			else if(part.startsWith("the ")){
			things.addElement(part.substring(4).trim());
			}
			else if(part.startsWith("some ")){
			things.addElement(part.substring(5).trim());
			}
			else if(part.startsWith("at ")){
			place=part.substring(3).trim();
			}
			else if(part.startsWith("near ")){
			place=part.substring(5).trim();
			}
			else if(part.startsWith("in ")){
			place=part.substring(3).trim();
			}
			else if(part.startsWith("on ")){
			date=part.substring(3).trim();
			}else{
			names.addElement(part);//??
			}

//		}
	
	i++;

	}


String q=null;

//create the query

String sq="select ?pic,";

for (int j=0;j<names.size();j++){

	sq=sq+" ?name"+j+",";
}

//for (int j=0;j<things.size();j++){

//	sq=sq+" ?thing"+j+",";
//}

if(place!=null){
sq=sq+" ?place,";
}
if(date!=null){
sq=sq+" ?dat,";
}

sq=sq.trim();

System.out.println("sq is "+sq+"....");

if(sq.endsWith(",")){
sq=sq.substring(0,sq.length()-1);
}

sq=sq+" where ";

for (int k=0;k<names.size();k++){
sq=sq+" (foaf:depicts ?pic ?person"+k+") ";
sq=sq+" (foaf:name ?person"+k+" ?name"+k+") ";
//sq=sq+" (foaf:depiction ?person"+k+" ?pic) ";
//sq=sq+" (foaf:name ?person"+k+" ?name"+k+") ";
}

String wn="http://xmlns.com/wordnet/1.6/";
if(things.size()>0){
	for (int k=0;k<things.size();k++){
	String thingy=(String)things.elementAt(k);

	thingy=thingy.substring(0,1).toUpperCase()+thingy.substring(1).toLowerCase();

	String type=wn+thingy;
	sq=sq+" (foaf:depicts ?pic ?thing"+k+") ";
	sq=sq+" (rdf:type ?thing"+k+" "+type+") ";	
	}
}


if(place!=null){
sq=sq+" (foaf:creationEvent ?pic ?event) (contact:nearestAirport ?event "+
"?airport) (doa:location ?airport ?place) ";
}

if(date!=null){
sq=sq+" (foaf:creationEvent ?pic ?event) (ical:date ?event ?dat) ";
}



for (int l=0;l<names.size();l++){
String name=(String)names.elementAt(l);
if(name.trim().equals("*")){
}else{
sq=sq+" and ?name"+l+" ~ '"+name+"' ";
}
}
if(place!=null){
sq=sq+" and ?place"+" ~ '"+place+"' ";
}

if(date!=null){
sq=sq+" and ?dat"+" ~ '"+date+"' ";
}

sq=sq+" using foaf for http://xmlns.com/foaf/0.1/ rdfs for "+
"http://www.w3.org/2000/01/rdf-schema# rdf for "+
"http://www.w3.org/1999/02/22-rdf-syntax-ns# "+
"contact for http://www.w3.org/2000/10/swap/pim/contact# "+
"ical for http://www.w3.org/2002/12/cal/ical# "+
"doa for http://www.daml.org/2001/10/html/airport-ont#";

String 
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/rdfxmlquery.jsp?query="+java.net.URLEncoder.encode(sq);

	getRDF(url,sq,channel,limit);

//	sm(channel,"query was "+sq);


}



/**

get pics by person name

*/

/*
public void getPicsByName(String message,String channel){

int limit=2;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1);
	String
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/peoplepicrdfxml.jsp?query="+java.net.URLEncoder.encode(q);

	String sq="select ?name, ?pic where "+
	" (foaf:name ?person ?name) "+
	" (foaf:depiction ?person ?pic) "+
	" using foaf for http://xmlns.com/foaf/0.1/";

	getRDF(url,sq,channel,4);

	}else{
	sm(channel,"try !iata BRS to find location info for an airport IATA code");

	}


}
*/

/**

get source files contaiing the data;

*/

public void getSource(String message,String channel){

int limit=5;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1);
	String
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/sourcerdfxml.jsp?query="+java.net.URLEncoder.encode(q);

	String sq="select ?term, ?source where "+
	" (test:sourceOf ?source ?term) "+
	" using test for http://xmlns.com/test/";

	getRDF(url,sq,channel,4);

	}else{
	sm(channel,"try !source 035a92bbdbf7c39a72b5534cc79058d25d0e7d6 "+
"to find where instances of that string are held. Exact match only");

	}


}


/**

get phone codes by country name.

*/

public void getPhoneByCountry(String message,String channel){

int limit=5;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1);

	String
	url="http://swordfish.rdfweb.org/discovery/2003/10/whwhwhwh/e164.owl";

	String sq="select ?name, ?phone where "+
	" (isoont:name ?country ?name) "+
	" (e164:telephoneCode ?country ?phone) "+
	" and ?name ~ '"+q+"' "+
	" using isoont for http://www.daml.org/2001/09/countries/iso-3166-ont# "+
	" e164 for http://www.daml.org/2003/02/e164/e164#";

	getRDF(url,sq,channel,limit);

	}else{
	sm(channel,"try !phone uk to get country phone codes");

	}


}

/**

event by sbustring match

*/

public void getEvents(String message,String channel, boolean isdate){

int limit=5;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1);


	String sq="select ?std, ?dd, ?url "+ 
" where "+
" (ical:summary ?ev ?dd) "+
" (ical:url ?ev ?url) "+
" (ical:dtstart ?ev ?da) "+
" (ical:date ?da ?std) ";
if(isdate){
sq=sq+" and ?std ~ '"+q+"' ";
}else{
sq=sq+" and ?dd ~ '"+q+"' ";
}
sq=sq+" using foaf for http://xmlns.com/foaf/0.1/ ical  for "+
" http://www.w3.org/2002/12/cal/ical# "+
" rss for http://purl.org/rss/1.0/ ";

System.out.println(sq);

String 
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/rdfxmlquery.jsp?query="+java.net.URLEncoder.encode(sq);

	getRDF(url,sq,channel,limit);

	}else{
	sm(channel,"try !bydate date to get events on that date");

	}


}


/**

chump search

*/

public void getChumpUrl(String message,String channel){

int limit=5;
int i=message.indexOf(" ");
String q=null;
boolean substr=false;

	if(i!=-1){
	q=message.substring(i+1);
	if(q.trim().startsWith("~")){
	q=q.substring(1);
	substr=true;
	}

	String
url="http://swordfish.rdfweb.org/discovery/2001/04/rdfig/byurlrdfxml.jsp?urlexact="+java.net.URLEncoder.encode(q);

	if(substr){
	url="http://swordfish.rdfweb.org/discovery/2001/04/rdfig/byurlrdfxml.jsp?url="+java.net.URLEncoder.encode(q);
	}

String sq="";
	if(substr){
	sq="select ?url where "+
	" (chump:link ?url ?chumplink) ";
	}else{
	sq="select ?chumplink where "+
	" (chump:link ?url ?chumplink) ";
	}

	sq=sq+" using rss for http://purl.org/rss/1.0/ "+
	" chump for http://usefulinc.com/ns/chump#";

	getRDF(url,sq,channel,limit);

	}else{
	sm(channel,"try !chump <url> to see if and when a url has been "+
"chumped before on #rdfig. .chump uses an exact match.");

	}


}

/**

pubs, open guide

*/


public void getPubUrl(String message,String channel){

int limit=25;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1).trim();

	q=q.substring(0,1).toUpperCase()+q.substring(1).toLowerCase();

	int k=q.indexOf(" ");

	if(k!=-1){
q=q.substring(0,k+1)+q.substring(k+1,k+2).toUpperCase()+q.substring(k+2).toLowerCase();
	}

String 
url="http://london.openguides.org/index.cgi?action=index;index_type=locale;index_value="+java.net.URLEncoder.encode(q)+";format=rdf";

	String sq="select ?title where "+
	" (dc:title ?url ?title) "+
	" using dc for http://purl.org/dc/1.0/";

	getPubRDF(url,sq,channel,limit);

	}else{
	sm(channel,"try !london <tube name> to find useful places (e.g. pubs) in that locale from london.openguides.org");

	}


}


/**

gets people they know

*/


public void getKnows(String message,String channel){

int limit=10;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){
	q=message.substring(i+1).trim();

	String sq="select ?name2 where "+
	" (foaf:name ?person2 ?name2) "+
	" (foaf:name ?person1 ?name1) "+
	" (foaf:knows ?person1 ?person2) "+
	" and ?name2 ~ '"+q+"' "+
	" using foaf for http://xmlns.com/foaf/0.1/";

	String
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/knowsrdfxml.jsp?query="+java.net.URLEncoder.encode(q);

	getKnowsRDF(url,channel,q,true);

	}else{
	sm(channel,"try !knows <name> to find the names of people known by this person");

	}


}




public void getKnownBy(String message,String channel){

int limit=-1;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1);


	String sq="select ?name2 where "+
	" (foaf:name ?person2 ?name2) "+
	" (foaf:name ?person1 ?name1) "+
	" (foaf:knows ?person2 ?person1) "+
	" and ?name2 ~ '"+q+"' "+
	" using foaf for http://xmlns.com/foaf/0.1/";

	String
url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/knowsrdfxml.jsp?query2="+java.net.URLEncoder.encode(q);

	getKnowsRDF(url,channel,q,false);


	}else{
	sm(channel,"try !knownby <name> to find the names of people who know this person");

	}


}


/**

*/
    public void getKnowsRDF(String url, String channel, String que,
boolean isknows){

	Graph g = new Graph(url, Util.RDFXML);

	int countt=0;
	int counttt=0;

		try{

		g.load();

		String sq="select ?mb, ?nm, ?per where "+
		" (foaf:mbox_sha1sum ?per ?mb) (foaf:name ?per ?nm) "+
		" and ?nm ~ '"+que+"' using foaf for http://xmlns.com/foaf/0.1/";

		java.sql.ResultSet r=g.askSquish(sq);

		Vector bla = new Vector();

		while(r.next()){

		String nm=(String)r.getString("nm");
		String mb=(String)r.getString("mb");
		String per=(String)r.getString("per");

		  if(!bla.contains(mb)){
			bla.addElement(mb);

		String qqq="select ?name where ";
if(isknows){
		qqq=qqq+" (foaf:knows "+per+" ?person1) ";
		qqq=qqq+" (foaf:name ?person1 ?name) using foaf for http://xmlns.com/foaf/0.1/";
}else{
		qqq=qqq+" (foaf:knows ?person1 ?per) ";
//		qqq=qqq+" (foaf:mbox_sh1sum ?per '"+mb+"') ";
		qqq=qqq+" (foaf:name ?person1 ?name) using foaf for http://xmlns.com/foaf/0.1/";
}

		java.sql.ResultSet rr=g.askSquish(qqq);

		countt=0;
		int count2=0;
//System.out.println("size "+rr.size());

		StringBuffer sb = new StringBuffer();
		while(rr.next()){
		count2++;

			if(count2<5){
			String nn=(String)rr.getString("name");

//System.out.println("count "+countt);
			if(countt%20==0){
				if(countt<25&&(!sb.toString().trim().equals(""))){
				sm(channel,sb.toString());
				sb=new StringBuffer(nn+" ");
				}
			}else{
			sb.append(nn+", ");
			}
		countt++;
			}
		}

			counttt=countt;
			String lasty=sb.toString().trim();
			if(!lasty.equals("")){
			sm(channel,lasty+" knows "+nm+" ("+(countt-1)+" found)");
			}

		  }//end if

		}//end while

		}catch(Exception e){
		System.err.println(e);
		e.printStackTrace();
		sm(channel,"nothing found");
		}

    }



/**


public void getPicsByName(String message,String channel){

int limit=2;
int i=message.indexOf(" ");
String q=null;

	if(i!=-1){q=message.substring(i+1);
	
String url="http://swordfish.rdfweb.org/discovery/2001/08/codepict/peoplepicrdfxml.jsp?query="+q;

	String sq="select ?name, ?pic where "+
	" (foaf:name ?person ?name) "+
	" (foaf:depiction ?person ?pic) "+
	" using foaf for http://xmlns.com/foaf/0.1/";

	getRDF(url,sq,channel,2);

	}else{
	sm(channel,"try !pic name to get pictures of people");

	}


}
*/


/**

try for a wordnet term

*/

public void getWordnet(String message,String channel){

int i=message.indexOf(" ");
String q=null;

	if(i!=-1){
	q=message.substring(i+1).trim();
	q=q.toLowerCase();
	String st=q.substring(0,1);
	st=st.toUpperCase();
	q=st+q.substring(1);

	if(q.indexOf(" ")!=-1){
	q=q.substring(0,q.indexOf(" "))+"_"+q.substring(q.indexOf(" ")).trim();
	}

	String url="http://xmlns.com/wordnet/1.6/"+q;

	String sq="select ?desc where "+
	" (rdfs:description "+url+" ?desc) "+
	" using rdfs for http://www.w3.org/2000/01/rdf-schema#";

	getRDF(url,sq,channel,2);

	}else{
	sm(channel,"try !wn dog to find descriptions of wordnet terms");
	}


}





public void getPathsByNames(String message,String channel, boolean
full){

int limit=2;
int i=message.indexOf(" ");
String name=null;

	if(i!=-1){
	name=message.substring(i).trim();
	int j=name.indexOf(",");

	if(j!=-1){

	String name2=name.substring(j+1);
	name=name.substring(0,j).trim();
	name2=name2.trim();

	name=java.net.URLEncoder.encode(name);
	name2=java.net.URLEncoder.encode(name2);

	String url="http://swordfish.rdfweb.org/discovery/2002/02/paths/bynamerdfxml.jsp?name="+name+"&name2="+name2;
	String
htmlurl="http://swordfish.rdfweb.org/discovery/2002/02/paths/byname.jsp?name="+name+"&name2="+name2;

	getPathRDF(url,channel,htmlurl,full);

	}else{
	sm(channel,"try !paths name1, name2 to find images linking people. Use the fullest form of name possible for best results");
	}

	}else{
	sm(channel,"try !paths name1, name2 to find images linking people. Use the fullest form of name possible for the best results");
	}

}


/**

knows paths

*/

public void getKnowsPathsByNames(String message,String channel, boolean
full){

int limit=2;
int i=message.indexOf(" ");
String name=null;

	if(i!=-1){
	name=message.substring(i).trim();
	int j=name.indexOf(",");

	if(j!=-1){

	String name2=name.substring(j+1);
	name=name.substring(0,j).trim();
	name2=name2.trim();

	name=java.net.URLEncoder.encode(name);
	name2=java.net.URLEncoder.encode(name2);

	String
url="http://swordfish.rdfweb.org/discovery/2002/02/paths/knowsbynamerdfxml.jsp?name="+name+"&name2="+name2;

	String sq="select ?ddd where (dc:description ?bla ?ddd) "+
" using dc for http://purl.org/dc/elements/1.1/";
	getRDF(url,sq,channel,2);

	}else{
	sm(channel,"try !knowspath name1, name2 to find paths"+
" between people via knows");
	}

	}else{
	sm(channel,"try !knowspath name1, name2 to find paths"+
" between people via knows");
	}

}

/**

getrdf specifically for paths

*/


    public void getPathRDF(String url, String channel,String
htmlurl,boolean full){

	Graph g = new Graph(url, Util.RDFXML);

	int countt=0;
	int counttt=0;

		try{

		g.load();

Vector r=g.ask(null,"http://purl.org/dc/elements/1.1/description",null);


		Vector bla =new Vector();

		Enumeration e = r.elements();
		while(e.hasMoreElements()){
		Statement st = (Statement)e.nextElement();
		Node desc = st.getObject();
		Node path = st.getSubject();

		sm(channel,desc.getContent());
		Vector rr=null;
		rr=g.askForObjects(path,"http://xmlns.com/test/pathComponent",null);
//System.out.println("size "+rr.size());

		Enumeration ee=rr.elements();
		countt=0;

			while(ee.hasMoreElements()){
			Node img = (Node)ee.nextElement();

//check!
				if(full && (countt > counttt)){
				sm(channel,img.getContent());
				}
			countt++;
			}

			if(!full){
			sm(channel,"in "+(countt-counttt)+" steps");
//			System.out.println(countt+" "+counttt);
			}
			counttt=countt;


		}//end while

		if(r.size()>0){
			sm(channel,"see "+htmlurl);
		}else{
			sm(channel,"no results found, sorry");
		}

		}catch(Exception e){
		System.err.println(e);
		e.printStackTrace();
		sm(channel,"nothing found");
		}

    }


public void setEnc(String message,String channel){

int i=message.indexOf(" ");
String q=null;

	if(i!=-1){
	q=message.substring(i+1).trim();
	try{
	setEncoding(q);
	sm(channel,"set encoding to "+q);
	}catch(Exception e){
	sm(channel,"sorry, couldn't set encoding to "+q);
	System.out.println(e);
	}

	}else{
	sm(channel,"sorry, couldn't set encoding to "+q);

	}

}

public void getEnc(String message,String channel){

	try{
	String s=getEncoding();
	sm(channel,"encoding is "+s);
	}catch(Exception e){
	sm(channel,"sorry, couldn't get encoding "+e);
	System.out.println(e);
	}


}

///testy

public void sm(String channel,String msg) {

String enc="";

   try{
	enc=getEncoding();
   }catch(Exception e){
	enc="UTF-8";
   }

	if(enc.equals("UTF-8")){
	sendMessage(channel,msg);
	}else{

		try{
		String normmsg=Normalizer.normalize(msg,Normalizer.DEFAULT);
		String normmsg2=new String(normmsg.getBytes(enc));
		sendMessage(channel,normmsg2);
		}catch(Exception ez){
		sendMessage(channel,msg);
		}
	}

}




public void transJ2L(String msg,String channel){


int i=msg.indexOf(" ");
String q=null;

q=msg.substring(i+1).trim();

//q=transCharset(q);

	try{
	Transliterator
j2lh=Transliterator.getInstance("Hiragana-Latin");
	Transliterator
j2lk=Transliterator.getInstance("Katakana-Hiragana");

	String ss=j2lk.transliterate(q);
	String s=j2lh.transliterate(ss);
	sm(channel,s);
	}catch(Exception e){
	System.out.println("translation error "+e+" for "+q);
	}

}


public void transL2J(String msg,String channel,boolean katakana){


int i=msg.indexOf(" ");
String q=null;

q=msg.substring(i+1).trim();

//q=transCharset(q);

	Transliterator j2l=null;
	try{
	if(katakana){
	j2l=Transliterator.getInstance("Latin-Katakana");
	}else{
	j2l=Transliterator.getInstance("Latin-Hiragana");
	}

	String s=j2l.transliterate(q);
	sm(channel,s);
	}catch(Exception e){
	System.out.println("translation error "+e+" for "+q);
	}

}


}
