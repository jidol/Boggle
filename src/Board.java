import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.HashSet;

/**
 * Boggle Board proof of design
 * @author Jeff Idol
 *
 */
public class Board {
	
	/**
	 * Basic constructor
	 * @param squareSideCount Only allowing square board to allow for inverse
	 */
	public Board(int squareSideCount)
	{
		mySide = squareSideCount;
		myBoard = new String[mySide][mySide];
	}
	
	/**
	 * Generate a random board of characters
	 */
	public void roll()
	{
		//Not allow duplicates for now
		HashSet<String> all = new HashSet<String>();
		int count = getSide() * getSide();
		Stack<String> data = new Stack<String>();
		for(int i=0; i < count; ++i)
		{
			Random r = new Random();
			String c = null;
			do {
				c = "" + (char)(r.nextInt(26) + 'a');
			} while (all.contains(c));
			all.add(c);
			data.push(c);
		}
		for(int i=0; i < getSide(); ++i)
		{
			for(int j=0; j < getSide(); ++j)
			{
				myBoard[i][j] = data.pop();
			}
		}
		
		data = null;
	}
	
	/**
	 * Get all non-duplicated substrings from the board
	 * @return Vector of strings with all substrings
	 */
	public Vector<String> getAllSubstrings()
	{
		int side = getSide();
		
		// Final result, without duplicates
		Vector<String> substrings = new Vector<String>();
		
		// Store all parts with duplicates here then remove duplicates
		// Start with all vertical substrings
		Vector<String> working = this.getVerticalSubstrings(side, side);
		
		// Add all horizontal substrings
		working.addAll(this.getHorizontalSubstrings(side, side));
		
		// Get all L shape substrings
		working.addAll(getL());
		
		// Simple remove all duplicates
		for(String E : working)
		{
			addUnique(substrings, E);
		}

		return substrings;
	}
	
	public int getSide()
	{
		return mySide;
	}
	
	public String[][] getBoard()
	{
		return myBoard;
	}
	
	public void printBoard()
	{
		printBoardWorker(myBoard);
	}
	
	/**
	 * Get all vertical substrings from the board between vertical indexes in
	 * forward direction
	 * @param endPosition Ending vertical position (inclusive)
	 * @return All vertical substrings (inclusive)
	 */
	private Vector<String> getVerticalSubstringsForward(int startPosition, int endPosition)
	{
		// Result vector
		Vector<String> substrings = new Vector<String>();
		
		// Move forward in V direction
		for(int vPos=startPosition; vPos <= endPosition; ++vPos)
		{
			// Forward in H direction outer loop to get all start H position
			// starting locations
			for(int hStart=0; hStart < getSide(); ++hStart)
			{
				// Buffer for substrings
				StringBuffer buffer = new StringBuffer();

				// Inner loop moving forward in H position from the outer loop
				// start position
				for(int hPos=hStart; hPos < getSide(); ++hPos)
				{
					buffer.append(myBoard[hPos][vPos]);
					addUnique(substrings, buffer.toString());
				}
			}
		}
		
		return substrings;
	}
	
	/**
	 * Get all vertical substrings from the board between vertical indexes in
	 * backwards direction
	 * @param endPosition Ending vertical position (inclusive)
	 * @return All vertical substrings (inclusive)
	 */
	private Vector<String> getVerticalSubstringsBackward(int startPosition, int endPosition)
	{
		// Result array
		Vector<String> substrings = new Vector<String>();
		
		// Forward in V direction
		for(int vPos=startPosition; vPos < endPosition; ++vPos)
		{
			// Backwards in H position, outer loop to get all start
			// positions in H direction
			for(int hStart=getSide() - 1; hStart >= 0; --hStart)
			{
				// Buffer to make substrings
				StringBuffer buffer = new StringBuffer();

				// Inner loop moving backward based on out loop start position
				for(int hPos= hStart; hPos >= 0; --hPos)
				{
					buffer.append(myBoard[hPos][vPos]);
					addUnique(substrings, buffer.toString());
				}
			}
		}
		
		return substrings;
	}
	
	/**
	 * Helper function to get all Vertical substrings, forward and backward
	 * @param startPosition Starting Position
	 * @param endPosition Ending Position
	 * @return Vector of all unique vertical substrings
	 */
	private Vector<String> getVerticalSubstrings(int startPosition, int endPosition)
	{
		// Result Vector
		Vector<String> substrings = new Vector<String>();

		// Start with forward facing vertical substrings
		Vector<String> directions = getVerticalSubstringsForward(startPosition, endPosition-1);
		
		// Add backward facing
		directions.addAll(getVerticalSubstringsBackward(startPosition, endPosition));
		
		// Remove duplicates from result vector
		for(String e : directions)
		{
			addUnique(substrings, e);
		}
		
		return substrings;
	}
			
	
	/**
	 * Get all horizontal substrings from the board between horizontal indexes in
	 * forward direction
	 * @param endPosition Ending horizontal position (inclusive)
	 * @return All horizontal substrings (inclusive)
	 */
	private Vector<String> getHorizontalSubstringsForward(int startPosition, int endPosition)
	{
		// Result Vector
		Vector<String> substrings = new Vector<String>();
		
		// Forward in H position
		for(int hPos=startPosition; hPos <= endPosition; ++hPos)
		{
			// Forward in V position outer loop in order to catch
			// all V start position substrings
			for(int vStart=0; vStart < getSide(); ++vStart)
			{
				// Buffer for output strings
				StringBuffer buffer = new StringBuffer();

				// Forward V position inner loop given the outer
				// loop start position
				for(int vPos=vStart; vPos < getSide(); ++vPos)
				{
					buffer.append(myBoard[hPos][vPos]);
					addUnique(substrings, buffer.toString());
				}
			}
		}
		
		return substrings;
	}
	
	/**
	 * Generate all the horizontal substrings
	 * @param startPosition Starting horizontal position
	 * @param endPosition Ending horizontal position
	 * @return Unique entries that are horizontal substrings within the given range
	 */
	private Vector<String> getHorizontalSubstrings(int startPosition, int endPosition)
	{
		// Result Vector
		Vector<String> substrings = new Vector<String>();

		// Get forward facing substrings
		Vector<String> directions = getHorizontalSubstringsForward(startPosition, endPosition-1);
		
		// Get backward facing substrings
		directions.addAll(getHorizontalSubstringsBackward(startPosition, endPosition-1));
		
		// Eliminate duplicates adding to result
		for(String e : directions)
		{
			addUnique(substrings, e);
		}
		
		return substrings;
	}
	
	/**
	 * Get all horizontal substrings from the board between horizontal indexes in
	 * backwards direction
	 * @param endPosition Ending horizontal position (inclusive)
	 * @return All horizontal substrings (inclusive)
	 */
	private Vector<String> getHorizontalSubstringsBackward(int startPosition, int endPosition)
	{
		// Result set
		Vector<String> substrings = new Vector<String>();
		
		// Forward on H position
		for(int hPos=startPosition; hPos <= endPosition; ++hPos)
		{
			// Backward on V Position outer loop to get all
			// V position starting points
			for(int vStart=getSide() - 1; vStart >= 0; --vStart)
			{
				// Store in buffer to make string parts
				StringBuffer buffer = new StringBuffer();

				// Backwards on V position (inner loop)
				for(int vPos= vStart; vPos >= 0; --vPos)
				{
					buffer.append(myBoard[hPos][vPos]);
					addUnique(substrings, buffer.toString());
				}
			}
		}
		
		return substrings;
	}
	
	/**
	 * Locate Horizontal substring with L component
	 * facing in the downward direction.
	 * 
	 * @return Downward facing L substrings
	 */
	private Vector<String> getHorizontalLDown()
	{
		// Result vector
		Vector<String> substrings = new Vector<String>();

		// Loop over horizontal position
		for(int HPos=0; HPos < getSide(); ++ HPos)
		{
			int VPos = 0;
			
			// L position starts at the left most part
			// one index down
			int LStart = HPos + 1;
	
			// Pull all the Horizontal strings at this starting
			// position
			Vector<String> hDir = this.getHorizontalSubstringsForward(HPos, HPos);
			
			// Make all the Downward components for each H position
			// Store them to make looping easier below
			Vector<Vector<String>> lParts = new Vector<Vector<String>>();
			for(int i=VPos; i < getSide(); ++i)
			{
				lParts.add(getVerticalLPartDown(i, LStart));
			}
			
			int mod = getSide();
			int count = 0;
			int vPos = 0;
			
			// Loop over the Horizontal elements prepending
			// with the L component stored in vPart
			Vector<String> vPart = lParts.elementAt(vPos++);
			for(String tail : hDir)
			{
				// Substrings Assemble!
				for(String head : vPart)
				{
					addUnique(substrings, head + tail);
				}
				
				// Current location in loop
				++count;
	
				// Ignore first as we prime the vector
				// When the count is reach for the next
				// L start  position, update vPart
				if(mod != 0 && (count % mod == 0))
				{
					// Make sure we don't go out of range
					// This was added due to a bug, but might
					// as well confirm
					if(vPos < getSide())
					{
						// Grab next vertical component
						// increment vPos
						vPart = lParts.elementAt(vPos++);
						
						// Each section is one size progressivly smaller
						mod -= 1;
						count = 0;
					}
				}
			}
		}
		
		return substrings;
	}
	
	/**
	 * Simple downward facing L component for a start vertical
	 * @param vPos Starting vertical location
	 * @param hStart Starting Horizontal location
	 * @return Vector of unique L components
	 */
	private Vector<String> getVerticalLPartDown(int vPos, int hStart)
	{
		// Result vector
		Vector<String> substrings = new Vector<String>();
		
		// Use a buffer to build the substrings
		StringBuffer buffer = new StringBuffer();
		
		// Horizontal component is variable but not V
		for(int hPos=hStart; hPos < getSide(); ++hPos)
		{
			// Prepend into the buffer
			buffer.insert(0,myBoard[hPos][vPos]);
			
			// Add to the output confirming unique
			addUnique(substrings, buffer.toString());
		}
		return substrings;
	}
	
	/**
	 * Generate all L shaped substrings
	 * @return Vector of L shaped substrings with duplicates
	 */
	private Vector<String> getL()
	{
		// Start with downward facing L
		Vector<String> result = getHorizontalLDown();
		
		// Add upward facing L, duplicate allowed
		result.addAll(this.getHorizontalLUp());
		return result;
		
	}	
	
	/**
	 * Generate the upward facing L shapes by
	 * inverse the matrix and looking downward.
	 * 
	 * @return Vector of downward facing L substrings
	 */
	private Vector<String> getHorizontalLUp()
	{
		// Store the original board
		String[][] originalBoard = myBoard;
		
		// Replace myBaord with new memory of inverse
		myBoard = inverseBoard(originalBoard);
		
		// Get the now upward facing L (which are really downward)
		Vector<String> result = getHorizontalLDown();
		
		// Reset board to original
		myBoard = originalBoard;
		
		return result;
		
	}
	
	/**
	 * Private print given board to string.
	 * Kept for debug reasons
	 * @param board Board to print to screen
	 */
	private void printBoardWorker(String[][] board)
	{
		StringBuffer line = new StringBuffer(getSide()*2);
		for(int i=0; i < getSide(); ++i)
		{
			for(int j=0; j < getSide(); ++j)
			{
				line.append(board[i][j]);
				line.append(" ");
			}
			System.out.println(line.toString());
			line = new StringBuffer(getSide()*2);
		}
	}
	
	
	/**
	 * Enforce unique entries into the vector 
	 * @param update Vector to add a unique entry (Note-- Modified)
	 * @param entry Entry to add to update if unique
	 */
	private void addUnique(Vector<String> update, String entry)
	{
		if(!update.contains(entry))
		{
			update.add(entry);
		}
	}
	
	/**
	 * Matrix inverse returning new memory
	 * @param original Starting array, assumed to be myBoard
	 * @return Matrix inverse of original matrix
	 */
	private String[][] inverseBoard(String[][] original)
	{
		String[][] inverse = new String[getSide()][getSide()];
		int reverseStart = getSide() - 1;
		
		for(int i=0 ; i < getSide(); ++i)
		{
			for(int j=0; j < getSide(); ++j)
			{
				inverse[reverseStart-i][reverseStart - j] = original[i][j];
			}
		}
		
		return inverse;
	
	}
	
	
	/**
	 * Side Length
	 */
	private int mySide = 0;
	
	/**
	 * Board of data with defined in [mySide][mySide]
	 */
	private String[][] myBoard = null;
}
