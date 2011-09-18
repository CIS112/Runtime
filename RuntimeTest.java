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
	private long [][] resultsILB = new long[7][5];
	
	//array to hold the size of the collection ADT
	private int[] numElements ={100,1000,10000,100000,1000000};
	
	//timer references
	private long startTime = 0;
	private long runtimer1 = 0;
	private long runtimer2 = 0;
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
					startTime =	System.nanoTime();
					testIABag = new IntArrayBag(size);
					//add elapsed time to runtimer1 var
					runtimer1 += System.nanoTime() - startTime;
				}
				//average over number iterations
				runtimer1 = runtimer1/numIterations;
				resultsIAB[0][a] =  runtimer1;
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
					startTime =	System.nanoTime();
					testIABag.countOccurrences(rand.nextInt(size));
					runtimer1 += System.nanoTime() - startTime;
					
					startTime =	System.nanoTime();
					testILBag.countOccurrences(rand.nextInt(size));
					runtimer2 += System.nanoTime() - startTime; 
				}
				resultsIAB[1][a] = ( runtimer1/numIterations);
				resultsILB[1][a] = ( runtimer2/numIterations);
				
				
				
				
				
				//test time of getCapacity
				runtimer1 = 0;
				runtimer2 = 0;
				for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					testIABag.getCapacity();
					runtimer1 += System.nanoTime() - startTime;
				
					
				}
				resultsIAB[2][a] = ( runtimer1/numIterations);
				resultsILB[2][a] = (long) Double.NaN;
				
				
				
				//test time of remove
				runtimer1 = 0;
				runtimer2 = 0;
				for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					testIABag.remove(rand.nextInt(size));
					runtimer1 += System.nanoTime() - startTime;
				    if(testIABag.size() <testIABag.getCapacity());
					 testIABag.add(rand.nextInt(size));
				
					startTime =	System.nanoTime(); 
					testILBag.remove(rand.nextInt(size));
					runtimer2 += System.nanoTime() - startTime;
					if(testILBag.size() < size)
						testILBag.add(rand.nextInt(size));
				
				
				}
				resultsIAB[3][a] = ( runtimer1/numIterations);
				resultsILB[3][a] = ( runtimer2/numIterations);
				
				
				
				// time of size method
				runtimer1 = 0;
				runtimer2 = 0;
				for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					testIABag.size();
					runtimer1 += System.nanoTime() - startTime;
				
					startTime =	System.nanoTime();
					testILBag.size();
					runtimer2 += System.nanoTime() - startTime;
				
				}
				resultsIAB[4][a] = ( runtimer1/numIterations);
				resultsILB[4][a] = ( runtimer2/numIterations);
				
				
				
				//time of trimeToSizeMethod
				// trim  re-instantiates the bag and fills it to a random size
				//this is done to simulate different trim times to get an average case
				runtimer1 = 0;
				for(int i = 0; i < numIterations; i++)
				{
					testIABag = new IntArrayBag(size);
					//generate random sizes to trim to
					intArrayBagFill(testIABag,rand.nextInt(size), size );
					
					startTime =	System.nanoTime();
					testIABag.trimToSize();
					runtimer1 += System.nanoTime() - startTime;
					
				}
				resultsIAB[5][a] = ((int)runtimer1/numIterations);
				resultsILB[5][a] = (long) Double.NaN;
				runtimer1 = 0;
				
			    
			    //test time of union
			    for(int i = 0; i < numIterations; i++)
				{
					startTime =	System.nanoTime();
					//union is static so it is called with the "class." syntax
					IntArrayBag.union(testIABag, unionBag);
					runtimer1 += System.nanoTime() - startTime;
				
					startTime =	System.nanoTime();
					IntLinkedBag.union(testILBag, unionILBag);
					runtimer2 += System.nanoTime() - startTime;
				}
				resultsIAB[6][a] = ( runtimer1/numIterations);
				resultsILB[6][a] = ( runtimer2/numIterations);
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
				int n = 0;
				for(long[] r :resultsILB)
				
				{
					System.out.printf("%-15s", methods[n]);
					
					for(long c : r)
						System.out.printf("%15s", c);
					
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
