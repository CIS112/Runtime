import  edu.colorado.collections.IntArrayBag;
import  edu.colorado.collections.IntLinkedBag;
import java.util.Random;

/**
*    @author CIS112  
*   
*    <A HREF="mailto:wowoc9751@students.mcs.edu"> (wowoc9751@students.mc3.edu) </A>
*    @version
*    September 16, 2011
**/
public class RuntimeTest {

	/**
	 * 
	 */
	
	private IntArrayBag testIABag;
	//var to hold the number of times to run test, is also used to average the runtime
	private int numIterations = 1000;
	//String array to automate chart printing
	private String[] methods = {"constructor", "countOccurance","getCapacity","remove","size","trimToSize","union"};
	//2d array to hold runtime average results, again used to automate
	private long[][] resultsIAB = new long[7][5];
	//array to hold the size of the collection ADT
	private int[] numElements ={100,1000,10000,100000,1000000};
	private long startTime;
	private long runTime = 0;
	///random num generator
	Random rand = new Random();
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		RuntimeTest newTest = new RuntimeTest();
		newTest.go();
	}
	
	public void go()
	{	   
			//sets up column headers 
			System.out.printf("%15s", " ");
		    for(int size : numElements)
			System.out.printf("%15s",size);
			System.out.println("\nIAB");
		
			//for loop to call constructor for n-size bag
			int a = 0;
			for(int size : numElements)
			 
			{
				IntArrayBag unionBag = new IntArrayBag(size);
				unionBag.add(rand.nextInt(size));
				
				
				//for loop to run test multiple times to smooth data
				for(int i = 0; i < numIterations; i++)
				{
					//get system counter
					startTime =	System.nanoTime();
					testIABag = new IntArrayBag(size);
					//add elapsed time to runtime var
					runTime += System.nanoTime() - startTime;
				}
				//average over number iterations
				runTime = runTime/numIterations;
				resultsIAB[0][a] =  runTime;
			
				//fill the bag for testing, this operation does not require data reduction 	
				for(int i = 0; i <= size; i++)
					testIABag.add(rand.nextInt(size));
				    runTime = 0;
				
				///test time of countOccurance()    
				for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					testIABag.countOccurrences(rand.nextInt(size));
					runTime += System.nanoTime() - startTime;
				}
				resultsIAB[1][a] = ( runTime/numIterations);
				runTime = 0;
				
				
				//test time of getCapacity
				for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					testIABag.getCapacity();
					runTime += System.nanoTime() - startTime;
				}
				resultsIAB[2][a] = ( runTime/numIterations);
				runTime = 0;
				
				//test time of remove
				for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					testIABag.remove(rand.nextInt(size));
					runTime += System.nanoTime() - startTime;
				    if(testIABag.size() <testIABag.getCapacity());
					 testIABag.add(rand.nextInt(size));
				}
				resultsIAB[3][a] = ( runTime/numIterations);
				runTime = 0;
				
				// time of size method
				for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					testIABag.size();
					runTime += System.nanoTime() - startTime;
				}
				resultsIAB[4][a] = ( runTime/numIterations);
				runTime = 0;
				
				//time of trimeToSizeMethod
				// trim  re-instantiates the bag and fills it to a random size
				//this is done to simulate different trim times to get an average case
				for(int i = 0; i < numIterations; i++)
				{
					testIABag = new IntArrayBag(size);
					//generate random sizes to trim to
					intArrayBagFill(testIABag,rand.nextInt(size), size );
					
					startTime =	System.nanoTime();
					testIABag.trimToSize();
					runTime += System.nanoTime() - startTime;
					
				}
				resultsIAB[5][a] = ((int)runTime/numIterations);
			    runTime = 0;
				
			    
			    //test time of union
			    for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					//union is static so it is called with the "class." syntax
					IntArrayBag.union(testIABag, unionBag);
					runTime += System.nanoTime() - startTime;
				
				}
				resultsIAB[6][a] = ( runTime/numIterations);
			
			a++;//increment results array counter
			}//end of IAB test loop
			
			//print results to the standard IO
			
			int m = 0;
			for(long[] r :resultsIAB)
			
			{
				System.out.printf("%-15s", methods[m]);
				
				for(long c : r)
					System.out.printf("%15s", c);
				
				System.out.println();
				m++;
			}
		
		////////////////////////////////////////////////////////IntLinkedBag//////////////////////////////////////
			
		 
				 
				System.out.println("\nILB");
				for(int size : numElements)
				{
					System.out.printf("%15s","N/A");
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
