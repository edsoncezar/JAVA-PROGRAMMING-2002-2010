import java.util.*;
import java.rmi.*;
import net.agmodel.physical.*;
import net.agmodel.weatherData.*;

/**
 * Demonstrates program access to MetBroker.
 * Retrieves one month of daily data from Chiba Prefectural Research Station
 * Compile using
 *       javac -classpath .;genericbroker.jar;weatherdata.jar SequenceTester.java
 *
 * Run using
 *       java -classpath .;genericbroker.jar;weatherdata.jar SequenceTester
 *
 */

public class SequenceTester {
  static final String delimiter="\t";
  static final String url = "rmi://mb1.dc.affrc.go.jp/";
  //static final String url = "rmi://127.0.0.1/";


  //Construct the application
  public SequenceTester() {
    try {
   // Use RMI to get a connection to MetBroker
      MetBroker myBroker = (MetBroker) Naming.lookup(url+MetBroker.RMINAME);
    // Ask for a connection for an anonymous, English language user.
      String sessionID=myBroker.getConnection("MetBroker test demo","en");
    // Build the data request

     // get an ID for the database of interest
      String testSource=MetBroker.PREFMETDB;
      // specify the station (Chiba Prefectural Research Station)
      String stationID="1";
     // first the date range
      Calendar c=Calendar.getInstance(TimeZone.getTimeZone("JST"));
      c.clear();
      c.set(1999,0,1);
      Interval interval=new Interval(c.getTime(),new Duration(31.0,DurationUnit.DAY));
      // then a set of weather elements to get
      Set elements=new HashSet();
      elements.add(MetElement.RAIN);
      elements.add(MetElement.AIRTEMPERATURE);
      elements.add(MetElement.WIND);
      elements.add(MetElement.RADIATION);
      // and the resolution
       MetDuration requestResolution=MetDuration.DAILY;
      // now put it all together in a request
      StationMetRequest request=new StationMetRequest(interval,elements,requestResolution,testSource,stationID,true,false);

  // Log in to the Prefectural Weather database, supplying usercode and password
      myBroker.loginToDataSource(sessionID, testSource,"chiba","chiba");

   // Get the data
      StationDataSet result=myBroker.supplyMetData(sessionID,request);

      if (result!=null) {//if there is no data at all, result will be null

        // Get references to the sequences in the ClientStation
        Rain rainSequence           = (Rain) result.getSequence(MetElement.RAIN);
        AirTemperature tempSequence = (AirTemperature) result.getSequence(MetElement.AIRTEMPERATURE);
        Wind windSequence           = (Wind) result.getSequence(MetElement.WIND);
        SolarRadiation radSequence  = (SolarRadiation) result.getSequence(MetElement.RADIATION);
        // use the result interval as a time base rather than the query interval
        // MetBroker will align the output data with the daily measurement time of the station (eg 9am)
	  Duration resultResolution=result.getResolution();
        int nValues= (int) result.getInterval().containsTimeQuantities(resultResolution);
        Date measurementStart=result.getInterval().getStart();
        Date measurementEnd=null;
        for (int i=0;i<nValues;i++) {
          measurementEnd=resultResolution.addToDate(measurementStart);
          System.out.print(measurementEnd+delimiter);
          Interval ofInterest=new Interval(measurementStart,measurementEnd);
          System.out.print(tempSequence.getMaximum(ofInterest)+delimiter);
          System.out.print(tempSequence.getMinimum(ofInterest)+delimiter);
          System.out.print(tempSequence.getAverage(ofInterest)+delimiter);
          System.out.print(rainSequence.getTotal(ofInterest)+delimiter);
          System.out.print(windSequence.getAverageSpeed(ofInterest)+delimiter);
          System.out.println(radSequence.getAverage(RadiationKind.GLOBAL,ofInterest));
          measurementStart.setTime(measurementEnd.getTime());
        } // for i
      }   // if result==null
      else
        System.out.println("No data matched the query.");

//      myBroker.disconnect(sessionID);
    }   // try
    catch (Exception e){
      e.printStackTrace();
      System.exit(0);
    }
  }  // constructor

  //Main method
  public static void main(String[] args) {
    new SequenceTester();
  }
}    // SequenceTester
