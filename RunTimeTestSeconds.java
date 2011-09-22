import  edu.colorado.collections.IntArrayBag;
import  edu.colorado.collections.IntLinkedBag;
import java.util.Random;

/**
*   Times how long it takes different methods to run for IntArrayBag and 
*       for IntLinkedBag under different sizes of Bags.
*    @author CIS112  
*   
*    <A HREF="mailto:wowoc9751@students.mcs.edu"> (wowoc9751@students.mc3.edu) </A>
*    @version
*    September 16, 2011
**/
public class RunTimeTestSeconds {

	/**
	 * 
	 */
	//declare references to the bag objects used for testing
	private IntArrayBag testIABag;
	private IntArrayBag unionBag;
	private IntLinkedBag testILBag;
	private IntLinkedBag unionILBag;
	
	//var to hold the number of times to run test, is also used to average the runtimer1
	private int numIterations = 100;
	//String array to automate chart printing
	private String[] methods = {"constructor", "countOccurance","getCapacity","remove","size","trimToSize","union"};
	
	//2d arrays to hold runtimer1 & 2 average results, again used to automate
	private long [][] resultsIAB = new long [7][5];
	private long [][] resultsILB = new long [7][5];
	
	//array to hold the size of the collection ADT
	private int[] numElements ={100,1000,10000,100000,1000000};
	
	//timer references
	private long startTime = 0;
	private float runtimer1 = 0;
	private float runtimer2 = 0;
	///random num generator
	Random rand = new Random();
	
	
	/**
         * Main method to instantiate a RuntimeTest and call go
	 * @param args
         *      the command line arguments
	 */
	public static void main(String[] args) 
	{
            RuntimeTest newTest = new RuntimeTest();
            newTest.go();
	}
	
        /**
         * Runs the test
         * @param - none
         */
	public void go()
	{	  
            System.out.println("Running Test....");

            //for loop to construct n-size bags and run benchmark testing
            int a = 0;
            for(int size : numElements)
            {
                unionBag = new IntArrayBag(size);
                unionBag.add(rand.nextInt(size));
                testILBag = new IntLinkedBag();
                unionILBag = new IntLinkedBag();

                //for loop to run test multiple times to smooth data
                for(int i = 0; i < numIterations; i++)
                {
                    //get system counter
                    startTime =	System.currentTimeMillis();
                    testIABag = new IntArrayBag(size);
                    //add elapsed time to runtimer1 var
                    runtimer1 += System.currentTimeMillis() - startTime;
                }
                //average over number iterations
                float elapsedTimeSec = runtimer1/1000F;
                runtimer1 = elapsedTimeSec/numIterations;
                resultsIAB[0][a] =  (long) runtimer1;
                resultsILB[0][a] =(long) Double.NaN;


                //fill the bags for testing, this operation does not require data reduction 	
                for(int i = 0; i <= size; i++)
                {
                    testIABag.add(rand.nextInt(size));
                    testILBag.add(rand.nextInt(size));
                    unionILBag.add(rand.nextInt(size));
                }	

                ///test time of countOccurance()   
                runtimer1 = 0;
                runtimer2 = 0;
                for(int i = 0; i < numIterations; i++)
                {
                    startTime =	System.currentTimeMillis();
                    testIABag.countOccurrences(rand.nextInt(size));
                    runtimer1 += System.currentTimeMillis() - startTime;

                    startTime =	System.currentTimeMillis();
                    testILBag.countOccurrences(rand.nextInt(size));
                    runtimer2 += System.currentTimeMillis() - startTime; 
                }
                elapsedTimeSec = runtimer1/1000F;
                float elapsedTimeSec2 = runtimer2/1000F;
                runtimer1 = elapsedTimeSec/numIterations;
                runtimer2 = elapsedTimeSec2/numIterations;   
                resultsIAB[1][a] = (long) ( runtimer1);
                resultsILB[1][a] = (long) ( runtimer2);





                //test time of getCapacity
                runtimer1 = 0;
                runtimer2 = 0;
                for(int i = 0; i < numIterations; i++)
                {
                    startTime =	System.currentTimeMillis();
                    testIABag.getCapacity();
                    runtimer1 += System.currentTimeMillis() - startTime;
                }
                elapsedTimeSec = runtimer1/1000F;
                elapsedTimeSec = runtimer2/1000F;
                runtimer1 = elapsedTimeSec/numIterations;
                resultsIAB[2][a] = (long) ( runtimer1);
                resultsILB[2][a] = (long) Double.NaN;



                //test time of remove
                runtimer1 = 0;
                runtimer2 = 0;
                for(int i = 0; i < numIterations; i++)
                {
                    startTime =	System.currentTimeMillis();
                    testIABag.remove(rand.nextInt(size));
                    runtimer1 += System.currentTimeMillis() - startTime;
                    if(testIABag.size() <testIABag.getCapacity());
                    {
                        testIABag.add(rand.nextInt(size));                        
                    }
                         

                    startTime =	System.currentTimeMillis(); 
                    testILBag.remove(rand.nextInt(size));
                    runtimer2 += System.currentTimeMillis() - startTime;
                    if(testILBag.size() < size)
                    {
                        testILBag.add(rand.nextInt(size));                        
                    }                              
                }
                elapsedTimeSec = runtimer1/1000F;
                elapsedTimeSec2 = runtimer2/1000F;
                runtimer1 = elapsedTimeSec/numIterations;
                runtimer2 = elapsedTimeSec2/numIterations;
                resultsIAB[3][a] = (long) ( runtimer1/numIterations);
                resultsILB[3][a] = (long) ( runtimer2/numIterations);



                // time of size method
                runtimer1 = 0;
                runtimer2 = 0;
                for(int i = 0; i < numIterations; i++)
                {
                    startTime =	System.currentTimeMillis();
                    testIABag.size();
                    runtimer1 += System.currentTimeMillis() - startTime;

                    startTime =	System.currentTimeMillis();
                    testILBag.size();
                    runtimer2 += System.currentTimeMillis() - startTime;
                }
                elapsedTimeSec = runtimer1/1000F;
                elapsedTimeSec2 = runtimer2/1000F;
                runtimer1 = elapsedTimeSec/numIterations;
                runtimer2 = elapsedTimeSec2/numIterations;
               
                resultsIAB[4][a] = (long) ( runtimer1/numIterations);
                resultsILB[4][a] = (long) ( runtimer2/numIterations);



                //time of trimToSizeMethod
                // trim  re-instantiates the bag and fills it to a random size
                //this is done to simulate different trim times to get an average case
                runtimer1 = 0;
                for(int i = 0; i < numIterations; i++)
                {
                    testIABag = new IntArrayBag(size);
                    //generate random sizes to trim to
                    intArrayBagFill(testIABag,rand.nextInt(size), size );

                    startTime =	System.currentTimeMillis();
                    testIABag.trimToSize();
                    runtimer1 += System.currentTimeMillis() - startTime;
                }
                elapsedTimeSec = runtimer1/1000F;
                runtimer1 = elapsedTimeSec/numIterations;
                resultsIAB[5][a] = ((int)runtimer1/numIterations);
                resultsILB[5][a] = (long) Double.NaN;
                runtimer1 = 0;


                //test time of union
                for(int i = 0; i < numIterations; i++)
                {
                    startTime =	System.currentTimeMillis();
                    //union is static so it is called with the "class." syntax
                    IntArrayBag.union(testIABag, unionBag);
                    runtimer1 += System.currentTimeMillis() - startTime;

                    startTime =	System.currentTimeMillis();
                    IntLinkedBag.union(testILBag, unionILBag);
                    runtimer2 += System.currentTimeMillis() - startTime;
                }
                elapsedTimeSec = runtimer1/1000F;
                elapsedTimeSec2 = runtimer2/1000F;
                runtimer1 = elapsedTimeSec/numIterations;
                runtimer2 = elapsedTimeSec2/numIterations;
                resultsIAB[6][a] = (long) ( runtimer1/numIterations);
                resultsILB[6][a] = (long) ( runtimer2/numIterations);
                a++;//increment results array counter
            }//end of IAB test loop

            // Print results to the standard IO
            print();			
	}
        
        /**
         * Prints results to the standard IO
         * @param - none
         * @postcondition
         *      results are printed to the standard IO
         */
        public void print()
        {
            //sets up column headers 
            System.out.printf("%15s", " ");
            for(int size : numElements)
            {
                System.out.printf("%15s",size);
            }
            System.out.println("\nIAB");
            

            // Print IAB results
            int m = 0;
            for(long[] r :resultsIAB)
            {
                System.out.printf("%-15s", methods[m]);

                for(long c : r)
                {
                    System.out.printf("%15s", c);                        
                }

                System.out.println();
                m++;
            }
		
		
            // Print ILB results
            System.out.println("\nILB");
            int n = 0;
            for(long[] r :resultsILB)
            {
                System.out.printf("%-15s", methods[n]);

                for(long c : r)
                {
                    System.out.printf("%15s", c);                        
                }

                System.out.println();
                n++;
            }
            
        }
	
	/**
	   * Fill a bag with random integers
	   * @param bag
	   *   the IntArrayBag that is to be added to
	   *  @param howmany
	   *  	the number of integers to add 
	   *  @param
	   *  	the range(maximum) value of an integers allowed 
	   * 
	   * @postcondition
	   *   The bag has had the specified number of integers added
	   * 
	   **/
	public void intArrayBagFill(IntArrayBag bag,int howMany, int range)
	{
            for(int i = 0; i <= howMany; i++)
            {
                bag.add(rand.nextInt(range));
            }
	}

}
