
import umontreal.iro.lecuyer.simevents.*;
import umontreal.iro.lecuyer.rng.*;
import umontreal.iro.lecuyer.randvar.*;
import umontreal.iro.lecuyer.probdist.*;
import umontreal.iro.lecuyer.stat.*;
import umontreal.iro.lecuyer.util.*;
import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.*;
import cern.colt.matrix.impl.*;
import cern.colt.matrix.linalg.*;
import cern.jet.stat.Descriptive;
import java.io.*;
import java.util.StringTokenizer;

// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

class CallCenter {

   static final double HOUR = 3600.0;  // Time is in seconds. 

   // Parameters and data for the call center model.
   // Arrival rates are per hour, service and patience times are in seconds.
   double openingTime;    // Opening time of the center (in hours).
   int numPeriods;        // Number of working periods (hours) in the day.
   int[] numAgents;       // Number of agents for each period.   
   double[] lambda;       // Base arrival rate lambda_j for each j.
   double alpha0;         // Parameter of gamma distribution for B.
   double p;              // Probability that patience time is 0.
   double nu;             // Parameter of exponential for patience time.
   double alpha, beta;    // Parameters of gamma service time distribution. 
   double s;              // Want stats on waiting times smaller than s.

   // Variables
   double busyness;       // Current value of B.
   double arrRate = 0.0;  // Current arrival rate.
   double nCallsExpected; // Expected number of calls per day.
   double workExpected;   // Expected sum of service times.
   int nAgents;           // Number of agents in current period.
   int nBusy;             // Number of agents occupied;
   int nArrivals;         // Number of arrivals today;
   int nAbandon;          // Number of abandonments today.
   int nGoodQoS;          // Number of waiting times less than s today.
   double sumServTimes;   // Sum of service times today.

   Event nextArrival = new Arrival();           // The next Arrival event.

   RandomStream streamB        = new RandMrg(); // For B.
   RandomStream streamArr      = new RandMrg(); // For arrivals.
   RandomStream streamPatience = new RandMrg(); // For patience times.
   GammaGen genServ;      // For service times; created in readData().
 
   List waitList = new List ("Waiting calls");
   Tally statWaitsDay = new Tally ("Waiting times within a day");


   // Constructor reads data from file "CallCenter.dat".
   public CallCenter() throws IOException {
      readCenterData();
   }

   // ****************************
   // One call in the call center.
   class Call { 

      double arrivTime, servTime, patienceTime;

      // Constructor of a call that just arrives.
      public Call() {
         servTime = genServ.nextDouble(); // Generate service time.
         sumServTimes += servTime;
         if (nBusy < nAgents) {           // Start service immediately.
            nBusy++;
            nGoodQoS++;
            statWaitsDay.add (0.0);
            new CallCompletion().schedule (servTime);
         } else {                         // Join the queue.
            patienceTime = generPatience();
            arrivTime = Sim.time();
            waitList.addLast (this);
         }
      }

      // This call has just completed its wait.
      public void endWait() {
         double wait = Sim.time() - arrivTime;
         // sumProbAbandon += p + (1.0-p) * ExponentialDist.cdf (nu, wait);
         if (patienceTime < wait) { // Caller has abandoned.
            nAbandon++;
            wait = patienceTime;    // Effective waiting time.
         }
         else {
            nBusy++;
            new CallCompletion().schedule (servTime);
         }
         if (wait < s) nGoodQoS++;
         statWaitsDay.add (wait);
      }
   } 

   // A new period starts; the arrival rate and number of agents may change.
   class NextPeriod extends Event {
      int j;     // Number of the new period.
      public NextPeriod (int period) { j = period; }
      public void actions() {
         if (j < numPeriods) {
            nAgents = numAgents[j];
            arrRate = busyness * lambda[j] / HOUR;
            if (j == 0) 
               nextArrival.schedule 
                  (ExponentialDist.inverseF (arrRate, streamArr.nextDouble()));
            else {
               checkQueue();
               nextArrival.reschedule ((nextArrival.time() - Sim.time()) 
                                       * lambda[j-1] / lambda[j]);
            }
            new NextPeriod(j+1).schedule (1.0 * HOUR);
         }
         else
            nextArrival.cancel();  // End of the day.
      }
   }

   // Arrival of a new call.
   class Arrival extends Event {
      public void actions() {
         nextArrival.schedule 
            (ExponentialDist.inverseF (arrRate, streamArr.nextDouble()));
         nArrivals++;
         Call call = new Call();               // Call just arrived.
      }
   }

   // End of a call.
   class CallCompletion extends Event {
      public void actions() { nBusy--;   checkQueue(); }
   }

   // Start answering new calls if agents are free and queue not empty.
   public void checkQueue() {
      while ((waitList.size() > 0) && (nBusy < nAgents))
         ((Call)waitList.removeFirst()).endWait();
   }

   // Generates the patience time for a call.
   public double generPatience() {
      double u = streamPatience.nextDouble();
      if (u <= p) 
         return 0.0;
      else 
         return ExponentialDist.inverseF (nu, (1.0-u) / (1.0-p));
   }

   // Simulates one day of operation of the center, with B = busyness.
   public void simulOneDay (double busyness) { 
      Sim.init();        statWaitsDay.init();
      nArrivals = 0;     nAbandon = 0;     
      nGoodQoS = 0;      nBusy = 0;
      sumServTimes = 0.0;
      this.busyness = busyness;
      new NextPeriod(0).schedule (openingTime * HOUR);
      Sim.start();
      // Here the simulation is running...
   }

   // Generates B and simulates one day of operation of the center.
   public void simulOneDay () {
      simulOneDay (GammaDist.inverseF (alpha0, alpha0, 8, 
                                       streamB.nextDouble()));
   }

   // Reads data from file and construct some arrays + genServ.
   public void readCenterData() throws IOException {
      BufferedReader input = new BufferedReader (new FileReader ("CallCenter.dat"));
      StringTokenizer line = new StringTokenizer (input.readLine());
      openingTime = Double.parseDouble (line.nextToken());
      line = new StringTokenizer (input.readLine());
      numPeriods  = Integer.parseInt (line.nextToken());

      numAgents = new int[numPeriods];
      lambda = new double[numPeriods];
      nCallsExpected = 0.0;
      for (int j=0; j < numPeriods; j++) {
         line = new StringTokenizer (input.readLine());
         numAgents[j] = Integer.parseInt (line.nextToken());
         lambda[j]    = Double.parseDouble (line.nextToken());
         nCallsExpected += lambda[j];
      }
      line = new StringTokenizer (input.readLine());
      alpha0 = Double.parseDouble (line.nextToken());
      line = new StringTokenizer (input.readLine());
      p = Double.parseDouble (line.nextToken());
      line = new StringTokenizer (input.readLine());
      nu = Double.parseDouble (line.nextToken());
      line = new StringTokenizer (input.readLine());
      alpha = Double.parseDouble (line.nextToken());
      line = new StringTokenizer (input.readLine());
      beta = Double.parseDouble (line.nextToken());
      // genServ can be created only after its parameters are known.
      genServ = new GammaGen (new RandMrg(), new GammaDist (alpha, beta));
      genServ.useAcceptanceRejection();   // Faster than inversion.
      workExpected = nCallsExpected * alpha / beta;
      line = new StringTokenizer (input.readLine());
      s = Double.parseDouble (line.nextToken());
      input.close();
   }


   public double getAlpha0 () {
      return alpha0;
   }

   public double getBusyness () {
      return busyness;
   }

   public double getNumCallsExpected () {
      return nCallsExpected;
   }

   public double getWorkExpected () {
      return workExpected;
   }

   public int getNumArrivals () {
      return nArrivals;
   }

   public double getSumServTimes () {
      return sumServTimes;
   }

   public int getNumAbandons () {
      return nAbandon;
   }

   public int getNumGoodQoS () {
      return nGoodQoS;
   }

   public StatProbe getStatWaits () {
      return statWaitsDay;
   }
}



// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

public class CallStrat {

   CallCenter center = new CallCenter(); 

   RandomStream streamB2 = new RandMrg(); // To generate the busyness B.

   public static double beta0, beta1, beta2;  // Quadratic function beta^*(u).
   public static double sigma0, sigma1, sigma2; // Quadratic function sigma(u).
   static double hatbeta;

   Tally statGoodQoS  = new Tally ("Proportion of waiting times < s");

   // Constructor.
   public CallStrat() throws IOException { }


   // Applies straigthforward MC for numDays days to estimate g(s) and 
   // the estimator's variance.
   public void simulMC (int numDays) {
      double busyness;
      double alpha0 = center.getAlpha0();
      Tally statMeanG = new Tally();  // Statistics on G(s).
      System.out.println ("----------------------------------------------");
      System.out.println ("Ordinary MC simulation:\n");
      for (int i=0; i < numDays; i++) {
         center.simulOneDay ();
         statMeanG.add (center.getNumGoodQoS());
         }
      System.out.println (" Total number of days =      " + numDays);
      System.out.println (" Expected number of arrivals            = " + 
         PrintfFormat.format (8, 2, 2, center.getNumCallsExpected()));
      System.out.println (" Estimated value of g(s)                = " + 
         PrintfFormat.format (8, 2, 2, statMeanG.average()));
      System.out.println (" Variance of G(s)                         = " + 
         PrintfFormat.format (10, 2, 2, statMeanG.variance()));
      System.out.println ();
   }


   // Applies MC with stratification on B with numStrat strata, daysInStrat
   // days per strata, and the CV (A - E[A|B=b]) with coefficient 
   // beta = beta0 + beta1 * U + beta2 * U * U where U = F(B) and F is the  
   // distribution function of B, to estimate g(s) and the estimator's variance.
   public void simulStratCV (int daysInStrat, int numStrat) {
      double x, u;
      double busyness, cv, gs;
      double alpha0 = center.getAlpha0();
      int numDays = 0;                   // Total number of days.
      int daysInThisStrat;               // Number of days in current stratum
      double beta;                       // CV coefficient.
      double variance;                   

      Tally statInStrat   = new Tally(); // Statistics within a stratum.
      Tally statInStratCV = new Tally(); // Statistics within a stratum, with CV.
      Tally statMeanG     = new Tally(); // Means for different strata.
      Tally statVarG      = new Tally(); // Variances for different strata.
      Tally statSdevG     = new Tally(); // Standard dev. for diff. strata.
      Tally statMeanGCV   = new Tally(); // Means for different strata, with CV.
      Tally statVarGCV    = new Tally(); // Variances for different strata, with CV.
      Tally statSdevGCV   = new Tally(); // Standard dev. for diff. strata, with CV.

      System.out.println ("----------------------------------------------");
      System.out.println ("Stratification w.r.t. B and CV.\n");
      statMeanG.init();
      statVarG.init();
      for (int s=0; s < numStrat; s++) {
         statInStrat.init();
         statInStratCV.init();
         u = ((double)(s) + 0.5) / (double)numStrat;
         daysInThisStrat = (int)Math.round ((double)daysInStrat 
                           * (sigma0 + u * (sigma1  + u * sigma2)));
         if (daysInThisStrat < 2) daysInThisStrat = 2;
         numDays += daysInThisStrat;
         for (int i=0; i < daysInThisStrat; i++) {
	    u = ((double)(s) + streamB2.nextDouble()) / (double)numStrat;

            ....

         }
         statMeanG.add (statInStrat.average());
         statVarG.add (statInStrat.variance());
         statSdevG.add (statInStrat.standardDev());
         statMeanGCV.add (statInStratCV.average());
         statVarGCV.add (statInStratCV.variance());
         statSdevGCV.add (statInStratCV.standardDev());
      }
      System.out.println (" Number of strata     =      " + numStrat);
      System.out.println (" Total number of days =      " + numDays);
      System.out.println (" Estimated value of g(s) with strat.    = " + 
         PrintfFormat.format (8, 2, 2, statMeanG.average()));
      System.out.println (" Estimate of g(s) with strat. opt. + CV = " + 
         PrintfFormat.format (8, 2, 2, statMeanGCV.average()));
      System.out.println ();

      double facteurVariance = (double)(numStrat-1) / (double)numStrat;
      variance = statVarG.average();
      System.out.println (" Variance of G with strat. prop.          = " + 
         PrintfFormat.format (10, 2, 2, variance));
      variance = statSdevG.average();
      System.out.println (" Variance of G with strat. optim.         = " + 
         PrintfFormat.format (10, 2, 2, variance * variance));
      variance = statMeanG.variance() * facteurVariance;
      System.out.println (" Variance of the means across strata      = " + 
         PrintfFormat.format (10, 2, 2, variance));
      variance = statSdevG.variance() * facteurVariance;
      System.out.println (" Variance of sigmas across strata         = " + 
         PrintfFormat.format (10, 2, 2, variance));
      System.out.println ();

      variance = statVarGCV.average();
      System.out.println (" Variance of G with strat. prop. + CV     = " + 
         PrintfFormat.format (10, 2, 2, variance));
      variance = statSdevGCV.average();
      System.out.println (" Variance of G with strat. optim. + CV    = " + 
         PrintfFormat.format (10, 2, 2, variance * variance));
      variance = statMeanGCV.variance() * facteurVariance;
      System.out.println (" Variance of means across strata with CV  = " + 
         PrintfFormat.format (10, 2, 2, variance));
      variance = statSdevGCV.variance() * facteurVariance;
      System.out.println (" Variance of sigmas across strata with CV = " + 
         PrintfFormat.format (10, 2, 2, variance));
      System.out.println ();
   }

   // To store results of method simulForFixedU.
   class ResultsU {
       double varG;     // Variance of G
       double varGCV;   // Variance of G with CV
       double betahat;  // Estimate of optimal CV coefficient beta.
   }       

   // Returns an estimate of the optimal CV coefficient beta for the 
   // control variate A - E[A|B=b] when F(b)=u, for this particular u.
   public ResultsU simulForFixedU (int numDays, double u) {
      ResultsU res = new ResultsU();
      double busyness;
      double alpha0 = center.getAlpha0();
      DoubleArrayList valC = new DoubleArrayList(); // Values of C.
      DoubleArrayList valG = new DoubleArrayList(); // Values of G(s).
      valC.clear();
      valG.clear();
      valC.ensureCapacity (numDays);
      valG.ensureCapacity (numDays);
      System.out.println ("----------------------------------------------");
      System.out.println ("Estimation of CV coefficient.");
      busyness = GammaDist.inverseF (alpha0, alpha0, 8, u);
      for (int i=0; i < numDays; i++) {
         center.simulOneDay (busyness);

          ....

         }
      System.out.println (" u = " + PrintfFormat.format (10, 5, 4, u));
      System.out.println (" B = " + PrintfFormat.format (10, 5, 4, busyness)); 
      res.varG = Descriptive.covariance (valG, valG);
      System.out.println (" Variance of G(s)         = " + 
         PrintfFormat.format (10, 2, 2, res.varG));
      res.betahat = Descriptive.covariance (valC, valG) 
                    / Descriptive.covariance (valC, valC);
      System.out.println (" hat beta = " + res.betahat);

      for (int i=0; i < numDays; i++) {
	  valG.set (i, valG.get(i) - res.betahat * valC.get(i));
      } 
      res.varGCV = Descriptive.covariance (valG, valG);
      System.out.println (" Variance of G(s) with CV = " + 
         PrintfFormat.format (10, 2, 2, res.varGCV));
      return res;
   }


   // Simulates the center for numDays to approximate the optimal CV 
   // coefficient beta^*(u) when B = F^{-1}(u), for u = u0, u1, u2.
   // Then, fits a quadratic curve
   // beta(u) = beta0 + beta1 * u + beta2 * u * u to the three points
   // (u, beta^*(u)) obtained. 
   // Also fits a quadratic curve
   // sigma(u) = sigma0 + sigma1 * u + sigma2 * u * u to the three points
   // (u, sigma(u)), where sigma^2(u) is the variance as a function of u.
   public void estimBeta3 (DoubleMatrix1D u, int numDays) {
      DoubleMatrix1D b = new DenseDoubleMatrix1D (3);
      DoubleMatrix1D v = new DenseDoubleMatrix1D (3);
      for (int j=0; j < 3; j++) {
         ResultsU res = simulForFixedU (numDays, u.get(j));
         b.set (j, res.betahat);
         v.set (j, Math.sqrt (res.varGCV));
      }
      DoubleMatrix2D m = new DenseDoubleMatrix2D (3, 3);
      for (int j=0; j < 3; j++) {
         m.set (j, 0, 1);  
         m.set (j, 1, u.get(j));  
         m.set (j, 2, u.get(j) * u.get(j));  
      }
      Algebra A = new Algebra();
      DoubleMatrix1D beta = A.mult (A.inverse (m), b);
      beta0 = beta.get(0);   beta1 = beta.get(1);   beta2 = beta.get(2); 
      System.out.println();
      System.out.print ("(beta0, beta1, beta2)    = ");
      System.out.print (PrintfFormat.format (10, 4, 4, beta.get(0)));
      System.out.print (PrintfFormat.format (10, 4, 4, beta.get(1)));
      System.out.println (PrintfFormat.format (10, 4, 4, beta.get(2)));
      System.out.println();

      DoubleMatrix1D sigma = A.mult (A.inverse (m), v);
      // Compute normalization constant, equal to the integral of sigma(u).
      double meanSigma = sigma.get(0) + sigma.get(1) / 2.0 + sigma.get(2) / 3.0; 
      sigma0 = sigma.get(0) / meanSigma;
      sigma1 = sigma.get(1) / meanSigma;
      sigma2 = sigma.get(2) / meanSigma; 
      System.out.print ("(sigma0, sigma1, sigma2) = ");
      System.out.print (PrintfFormat.format (10, 4, 4, sigma0));
      System.out.print (PrintfFormat.format (10, 4, 4, sigma1));
      System.out.println (PrintfFormat.format (10, 4, 4, sigma2));
      System.out.println();
   }

   // Same as estimBeta3, except that beta1 is forced to be 0.
   // We get a quadratic curve beta(u) = beta0 + beta2 * u * u,
   // fitted to the two points (u, beta^*(u)), for u = u0 and u2.
   public void estimBeta2 (DoubleMatrix1D u, int numDays) {
      DoubleMatrix1D b = new DenseDoubleMatrix1D (2);
      DoubleMatrix1D v = new DenseDoubleMatrix1D (2);
      ResultsU res = new ResultsU();
      for (int j=0; j < 2; j++) {
         res = simulForFixedU (numDays, u.get(j));
         b.set (j, res.betahat);
         v.set (j, Math.sqrt (res.varGCV));
      }
      DoubleMatrix2D m = new DenseDoubleMatrix2D (2, 2);
      for (int j=0; j < 3; j++) {
         m.set (j, 0, 1);  
         m.set (j, 1, u.get(j) * u.get(j));  
      }
      DoubleMatrix1D beta = new DenseDoubleMatrix1D (2);
      Algebra A = new Algebra();
      beta = A.mult (A.inverse (m), b);
      beta0 = beta.get(0);   beta1 = 0.0;   beta2 = beta.get(1);

      System.out.println();
      System.out.print ("(beta0, beta2) = ");
      System.out.print (PrintfFormat.format (10, 4, 4, beta0));
      System.out.print (PrintfFormat.format (10, 4, 4, beta2));
      System.out.println();
   }


   static public void main (String[] args) throws IOException { 
      int n0 = 10000;             // Number of days to estimate beta^*.
      int n  = 1000000;           // Target number of days to simulate.
      int s  = 10000;             // Number of days per strata.
      CallStrat cc = new CallStrat();
      Chrono timer = new Chrono();

      DoubleMatrix1D u = new DenseDoubleMatrix1D (3);
      u.set(0, 0.05);  u.set(1, 0.5);  u.set(2, 0.9);  
      cc.estimBeta3 (u, n0);

      timer.init();
      cc.simulStratCV (s, n/s);   // MC with stratification and CV.
      System.out.println (" CPU time = " + timer.format());
   }
  
}

