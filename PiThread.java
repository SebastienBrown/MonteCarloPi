import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;
public class PiThread implements Runnable {

  //creates class attributes for PiThread
  private int id;              //thread id
  private int[] array;         //array used to store the number of hits of each thread

  private int numThreads;   //total number of threads being run
  private long numPoints;;  //total number of "darts thrown"

  //creates a class constructor for the PiThread class
  // specifies the number of points to test, the number of threads to use, as well as the id of each thread and the final destination array
  public PiThread (long numPoints, int numThreads,int id, int[] array) {
    this.numPoints=numPoints;
    this.numThreads=numThreads;
    this.id = id;
    this.array = array;
  }

  //defines the action of each thread when it is run
  //implements the Monte Carlo pi estimation calculation
  public void run () {

    int hits=0;     //define a hit variable to count the number of succesful dart throws

    for (int i=0;i<numPoints/numThreads;i++){   //calculates the number of "throws" to attempt based on the number of threads
      double x=ThreadLocalRandom.current().nextDouble();    //randomly generate a double x such that 0<x<1
      double y=ThreadLocalRandom.current().nextDouble();    //randomly generate a double y such that 0<y<1
      double distance=(Math.sqrt(Math.pow((x-0.5),2)+Math.pow((y-0.5),2))); //this uses the standard distance formula between two points to determine the distance between (x,y) and the point (0.5,0,5) 
                                                                                  //if the distance is smaller than 0.5, then (x,y) is contained within the target circle of radius 0.5 and center (0.5,0.5) inscribed in a square of side length 1 with its lower-left vertex at (0,0): this is a dart "hit"
      if(distance<0.5){     //if the target is hit, increment the hit variable
        hits++;
      }
    }
    array[id]=hits;   //updates the relevant array cell based on thread id
  }
}