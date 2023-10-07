/**
  * Description: This PiEstimator class uses the PiThread class to implement a multithreaded Monte Carlo simulation estimation of the value of pi.
  The program creates an array of threads of length numThreads, where each thread (using the analogy of the dartboard explore in class readings) throws
  numPoints/numThreads darts. The number of succesful "throws" for each thread is stored within a corresponding array cell of "arr", and once all threads have
  been fully executed, the sum of the succesful "throws" is used to calculate the estimate of pi. This program uses Java's built-in thread local random number 
  generator ThreadLocalRandom.
  *
  * @author Sebastien Brown
  */

  public class PiEstimator {
    
    private long numPoints;
    private int numThreads;

    // creates a clas constructor for the PiEstimator class specifying the number of sample points (numPoints) 
    // and the number of threads used to compute the estimate (numThreads)
    public PiEstimator (long numPoints, int numThreads) {
      this.numPoints=numPoints;
      this.numThreads=numThreads;
    }

    // this method uses multithreading to calculate an estimate of the value of pi using the
    //Monte Carlo simulation technique
    public double getPiEstimate () {
    
    //creates a shared final array for storing the results of the individual threads
	  int[] arr = new int[numThreads];

    //creates an array of threads of length numThreads
    Thread[] threads = new Thread[numThreads];
    
    //populates the array of threads populated with PiThread objects
    for (int i = 0; i < numThreads; i++) {
      threads[i] = new Thread(new PiThread(numPoints,numThreads,i,arr));
      }

    //starts all the threads
    for (Thread t : threads) {
        t.start();
    }

    //waits for all the threads to complete (so that arr is no longer being modified)
	  for (Thread t : threads) {
      try {
        t.join();
      }
      catch (InterruptedException ignored) {
      //no changes if the thread is interrupted
      }
  }

  //calculates the sum of all the values in arr (which corresponds to the total number of hits)
  long sum=0;
  for (int i=0; i<arr.length;i++){
    sum+=arr[i];
  }


  //using the Monte Carlo simulation formula, calculates the estimate for the value of pi
  double estimate=(sum/(double)numPoints*4.0);

//return the estimate for pi
return estimate;
}
}