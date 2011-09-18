// File: IntLinkedBag.java from the package edu.colorado.linked
// Complete documentation is available from the IntLinkedBag link in:
//   http://www.cs.colorado.edu/~main/docs

package edu.colorado.collections;
import edu.colorado.nodes.IntNode; 

/******************************************************************************
* An IntLinkedBag is a collection of int numbers.
*
* @note
*
*   (1) Beyond Int.MAX_VALUE elements, countOccurrences,
*   size, and grab are wrong.
*   <p>
*   (2) Because of the slow linear algorithms of this class, large bags will have
*   poor performance.
*
* @see
*   <A HREF="../../../../edu/colorado/collections/IntLinkedBag.java">
*   Java Source Code for this class
*   (www.cs.colorado.edu/~main/edu/colorado/collections/IntLinkedBag.java)
*   </A>
*
* @author Michael Main 
*   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
*
* @version
*   March 6, 2002
*
* @see IntArrayBag
* @see LinkedBag
******************************************************************************/
public class IntLinkedBag implements Cloneable
{
   // Invariant of the IntLinkedBag class:
   //   1. The elements in the bag are stored on a linked list.
   //   2. The head reference of the list is in the instance variable 
   //      head.
   //   3. The total number of elements in the list is in the instance 
   //      variable manyNodes.
   private IntNode head;
   private int manyNodes;   


   /**
   * Initialize an empty bag.
   * @param - none
   * @postcondition
   *   This bag is empty.
   **/
   public IntLinkedBag( )
   {
      head = null;
      manyNodes = 0;
   }
        
 
   /**
   * Add a new element to this bag.
   * @param element
   *   the new element that is being added
   * @postcondition
   *   A new copy of the element has been added to this bag.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntNode.
   **/
   public void add(int element)
   {      
      head = new IntNode(element, head);
      manyNodes++;
   }


   /**
   * Add the contents of another bag to this bag.
   * @param addend
   *   a bag whose contents will be added to this bag
   * @precondition
   *   The parameter, addend, is not null.
   * @postcondition
   *   The elements from addend have been added to this bag.
   * @exception NullPointerException
   *   Indicates that addend is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of the bag.
   **/
   public void addAll(IntLinkedBag addend)
   {
      IntNode[ ] copyInfo;
      
      // The precondition indicates that addend is not null. If it is null,
      // then a NullPointerException is thrown here.
      if (addend.manyNodes > 0)
      {
         copyInfo = IntNode.listCopyWithTail(addend.head);
         copyInfo[1].setLink(head); // Link the tail of copy to my own head... 
         head = copyInfo[0];        // and set my own head to the head of the copy.
         manyNodes += addend.manyNodes;
      }
   }

     
   /**
   * Add new elements to this bag. If the new elements would take this
   * bag beyond its current capacity, then the capacity is increased
   * before adding the new elements.
   * @param elements
   *   (a variable-arity argument)
   *   one or more new elements that are being inserted
   * @postcondition
   *   A new copy of the element has been added to this bag.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of the bag.
   **/
   public void addMany(int... elements)
   {
      // Activate the ordinary add method for each integer in the
      // elements array.
      for (int i : elements)
          add(i); 	       
   }

     
   /**
   * Generate a copy of this bag.
   * @param - none
   * @return
   *   The return value is a copy of this bag. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to an IntLinkedBag before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public Object clone( )
   {  // Clone a nIntLinkedBag object.
      IntLinkedBag answer;
      
      try
      {
         answer = (IntLinkedBag) super.clone( );
      }
      catch (CloneNotSupportedException e)
      {  // This exception should not occur. But if it does, it would probably
         // indicate a programming error that made super.clone unavailable.
         // The most common error would be forgetting the "Implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException
         ("This class does not implement Cloneable");
      }
      
      answer.head = IntNode.listCopy(head);
      
      return answer;
   }
   

   /**
   * Accessor method to count the number of occurrences of a particular element
   * in this bag.
   * @param target
   *   the element that needs to be counted
   * @return
   *   the number of times that target occurs in this bag
   **/
   public int countOccurrences(int target)
   {
      int answer;
      IntNode cursor;

      answer = 0;
      cursor = IntNode.listSearch(head, target);
      while (cursor != null)
      {  // Each time that cursor is not null, we have another occurrence of
         // target, so we add one to answer and then move cursor to the next
         // occurrence of the target.
         answer++;
         cursor = cursor.getLink( );
         cursor = IntNode.listSearch(cursor, target);
      }
      return answer;
   }

    
   /**
   * Accessor method to retrieve a random element from this bag.
   * @param - none
   * @precondition
   *   This bag is not empty.
   * @return
   *   a randomly selected element from this bag
   * @exception IllegalStateException
   *   Indicates that the bag is empty.
   **/
   public int grab( )
   {
      int i;
      IntNode cursor;
      
      if (manyNodes == 0)
         throw new IllegalStateException("Bag size is zero");
         
      i =  (int)(Math.random( ) * manyNodes) + 1;
      cursor = IntNode.listPosition(head, i);
      return cursor.getData( );
   }
   
             
   /**
   * Remove one copy of a specified element from this bag.
   * @param target
   *   the element to remove from the bag
   * @postcondition
   *   If target was found in the bag, then one copy of
   *   target has been removed and the method returns true. 
   *   Otherwise the bag remains unchanged and the method returns false. 
   **/
   public boolean remove(int target)
   {
      IntNode targetNode; // The node that contains the target

      targetNode = IntNode.listSearch(head, target);
      if (targetNode == null)
         // The target was not found, so nothing is removed.
         return false;
      else
      {  // The target was found at targetNode. So copy the head data to targetNode
         // and then remove the extra copy of the head data.
         targetNode.setData(head.getData( ));
         head = head.getLink( );
         manyNodes--;
         return true;
      }
   }
    
      
   /**
   * Determine the number of elements in this bag.
   * @param - none
   * @return
   *   the number of elements in this bag
   **/                           
   public int size( )
   {
      return manyNodes;
   }
   

   /**
   * Create a new bag that contains all the elements from two other bags.
   * @param b1
   *   the first of two bags
   * @param b2
   *   the second of two bags
   * @precondition
   *   Neither b1 nor b2 is null.
   * @return
   *   the union of b1 and b2
   * @exception IllegalArgumentException
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new bag.
   **/   
   public static IntLinkedBag union(IntLinkedBag b1, IntLinkedBag b2)
   {       
      // The precondition requires that neither b1 nor b2 is null.
      // If one of them is null, then addAll will throw a NullPointerException.  
      IntLinkedBag answer = new IntLinkedBag( );
      
      answer.addAll(b1);
      answer.addAll(b2);     
      return answer;
   }
      
}
           
