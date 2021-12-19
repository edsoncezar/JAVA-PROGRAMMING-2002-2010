import java.io.*;
import java.util.*;
import com.swath.*;
import com.swath.cmd.*;

/**
 * SmartMove Script
 *          
 * Synopsis: Performs Holo and/or Density scans while moving.
 * 
 * Description:
 *  This script takes a destination sector as a parameter.  The script will automatically
 *  move you to the destination, while optionally holo/density scanning.
 *  If you select density scan only, then the script will halt when it encounters an 
 *  anomaly(option) or a density which is not either 100 or 0.
 *  If you select density and holo, then the script will perform a holo scan when the
 *  density is not either 100 or 0, and will halt if there are figs (option), traders(not yet available),
 *  planets(not yet available), or anomalies(option).
 *  The 'Holo Trigger' parameter tells the script how many warps there have to be to
 *  force a holo scan.  For example, it may be desired to always perform a holo scan when
 *  you are in a sector with >=4 unexplored warps.  Set to 0 to never trigger, and set
 *  to 1 to always trigger.
 *
 * Todos:
 *  Prevent your own mines/whatever from halting script
 *  Allow safe sector list (to prevent holo scan of your own figs) 
 *    and/or Create 'Assume fedspace safe' parameter
 *  Allow for port trading during move
 *  Add run away option, to allow you to move further away from an online trader(or alien) when encountered.
 *  Add report for 'interesting' things found along the way (figs, anomalies, planets, etc)
 *
 * Todos which are waiting on next Swath release:
 *  -Halt script when planet or traders (or aliens/ferrengi) are in your path.  I'm waiting
 *  on the sector method to allow you to get traders in sector.
 *  -Don't halt script when mines belong to you or your corp.
 *  -Don't halt script when figs belong to your corp.
 *
 * This is my first attempt at a script, let me know what you think!
 * Please post any suggestions, bugs, or requests on the Swath forum.
 * 
 * @author Rat Bastard
 * @version 0.7, 8/5/01
 */
public class SmartMove extends UserDefinedScript {

        /*****************HELPFUL USER DEFAULTS*******************/
        protected static final int DEFAULT_HOLO_TRIG = 3;
        protected static final boolean DEFAULT_DENSITY_SCAN = true;
        protected static final boolean DEFAULT_HOLO_SCAN = true;
        protected static final boolean DEFAULT_ANOM_HALT = false;
        protected static final int DEFAULT_FIG_HALT = 10;
        /*********************************************************/

        /* Other class variables */
        protected static final int MAX_SCAN = 6;
        protected static final String TITLE = "SmartMove";
        protected boolean bHaveHScanner = false;
        protected boolean bHaveDScanner = false;
        /* Class Parameters */
        protected Parameter parDScanner;
        protected Parameter parHScanner;
        protected Parameter parDest;
        protected Parameter parHoloTrig;
        protected Parameter parAnomHalt;
        protected Parameter parFigHalt;



public String getName() 
{ 
        return TITLE; 
} /* getName() */

public boolean initScript() throws Exception 
{
        /* check prompt */
        if(!atPrompt(Swath.COMMAND_PROMPT)) {
                MessageBox.exec("Must be at command prompt.",
                                "Error",
                                MessageBox.ICON_ERROR,
                                MessageBox.TYPE_OK);
                return false;
        }

        /* Add Destination Sector Parameter */
        parDest = new Parameter("Destination sector?");
        parDest.setType(Parameter.INTEGER);
        parDest.setIntegerRange(1,20000);
        parDest.setInteger(Swath.main.currSector());
        registerParam(parDest);


        if (Swath.ship.hasDensityScanner()) {
                /* Add Density Scanner Sector Parameter */
                parDScanner = new Parameter("Use Density Scanner?");
                parDScanner.setType(Parameter.BOOLEAN);
                parDScanner.setBoolean(DEFAULT_DENSITY_SCAN);
                registerParam(parDScanner);
                bHaveDScanner = true;           

                /* Add Halt on Anomaly option */
                parAnomHalt = new Parameter("Halt on Anomalies?");
                parAnomHalt.setType(Parameter.BOOLEAN);
                parAnomHalt.setBoolean(DEFAULT_ANOM_HALT);
                registerParam(parAnomHalt);
        } 


        if (Swath.ship.hasHoloScanner()) {
                /* Add Holo Scanner Sector Parameter */
                parHScanner = new Parameter("Use Holo Scanner?");
                parHScanner.setType(Parameter.BOOLEAN);
                parHScanner.setBoolean(DEFAULT_HOLO_SCAN);
                registerParam(parHScanner);
                bHaveHScanner = true;           

                /* Add Holo Trigger Parameter */
                parHoloTrig = new Parameter("Holo Scan Trigger:");
                parHoloTrig.setType(Parameter.INTEGER);
                parHoloTrig.setIntegerRange(0,10);
                parHoloTrig.setInteger(DEFAULT_HOLO_TRIG);
                registerParam(parHoloTrig);

                /* Add Fighter halt parameter */
                parFigHalt = new Parameter("How many fighters before halting?");
                parFigHalt.setType(Parameter.INTEGER);
                parFigHalt.setInteger(DEFAULT_FIG_HALT);
                parFigHalt.setIntegerRange(1,100000);
                registerParam(parFigHalt);
        }





        return true;
} /* initScript() */

public boolean runScript() throws Exception 
{
        boolean bHScan = false;         /* Do we want to holo scan? */
        boolean bDScan = false;         /* Do we want to density scan? */
        boolean bAnomFound = false;     /* Anomaly found in path */
        boolean bUnknownDen = false;    /* Unknown density in path */
        boolean bSomethingBad = false;  /* Encountered a reason to stop (fig, trader, planet) */
        int iDest;                      /* Destination sector */
        int iCurDest, iDensity;
        String strPlot;
        Sector secCurDest;
        

        iDest = parDest.getInteger();   /* Get destination sector */
        /* Check to see if we have scanners, and if so, do we want to use them? */
        if (bHaveHScanner) {
                bHScan = parHScanner.getBoolean();      
        }
        if (bHaveDScanner) {
                bDScan = parDScanner.getBoolean();
        }
        

        /* Keep plotting course, and moving, until we've reached our destination */
        while (Swath.sector.sector() != iDest) {
                /* Get TW to plot course */
                EnterComputer.exec();
                SendString.exec("F"+SendString.RETURN_KEY);
                SendString.exec(iDest+SendString.RETURN_KEY);
                /* 
                 * Save the course in strPlot.  This will only return the first line of the
                 * course plot.  If the course is more than one line, then the enclosing 
                 * while loop will run again, to perform the move on the rest of the plot.
                 */
                strPlot = WaitForText.exec(Swath.sector.sector()+" > ");
                while(!atPrompt(Swath.COMPUTER_PROMPT)) Thread.sleep(200);
                LeaveComputer.exec();
                /*
                 * Set up a string tokenizer for parsing through the sectors. '>' and ' '
                 * are the delimiters, which will be ignored during parsing.
                 */
                StringTokenizer stPlot = new StringTokenizer(strPlot,">() ");
                stPlot.nextToken();             /* Skip the first (current) sector */
                while (stPlot.hasMoreTokens()) {
                        /* Get the next sector in the plot */
                        iCurDest = (new Integer(stPlot.nextToken())).intValue();
                        while(!atPrompt(Swath.COMMAND_PROMPT)) Thread.sleep(200);
                        /* Do we do a density scan before moving? */
                        if (bDScan) {
                                ScanSector.exec(ScanSector.DENSITY_SCAN);
                                secCurDest = Swath.getSector(iCurDest);
                                iDensity = secCurDest.density();
                                bAnomFound = secCurDest.anomaly();
                                /* Analyze the scan */
                                if ( (iDensity != 0) && (iDensity !=100) ) {
                                        bUnknownDen = true;     
                                }
                        } /* end density scan */
                        /* Is holo scanning option turned on? */
                        if (bHScan) {
                                while(!atPrompt(Swath.COMMAND_PROMPT)) Thread.sleep(200);
                                /* Get number of unexplored sectors */
                                int iUnExplored = 0;
                                int iWarps[] = Swath.sector.warpSectors();
                                for (int i=0; i < Swath.sector.warps(); i++) {
                                        Sector sec = Swath.getSector(iWarps[i]);
                                        if (sec.isUnexplored()) {
                                                iUnExplored++;
                                        }
                                }
                                /* Do we perfrom holo scan? */
                                if ( (bUnknownDen) || (iUnExplored >= parHoloTrig.getInteger()) ) {
                                        bUnknownDen = false;
                                        /* Start the scan */
                                        ScanSector.exec(ScanSector.HOLO_SCAN);
                                        secCurDest = Swath.getSector(iCurDest);
                                        if ( ((secCurDest.fighters() >= parFigHalt.getInteger()) &&
                                              (!secCurDest.ftrOwner().isYou())) ||
                                             (secCurDest.armidMines() > 0) ){
                                                bSomethingBad = true;
                                        }       
                                }
                        }
                        /* Should we abort the move? */
                        if ( ((bAnomFound) && (parAnomHalt.getBoolean())) ||
                             (bUnknownDen) || (bSomethingBad) ) {
                                PrintText.exec("\n\nSector in path fails moving conditions.  Script halted\n\n.");
                                return true;
                        }
                        /* If we haven't returned yet, then it is safe to move */
                        Move.exec(iCurDest);
                } /* while more tokens */
        } /* While not at destination */
        
        /* Output any reports */
        if (bAnomFound) {
                PrintText.exec("\n\nYou passed over a sector with an anomaly.  Don't forget to Check for limpets!\n\n");  
        }

        return true;
        
} /* runScript() */



} /* class SmartMove */
