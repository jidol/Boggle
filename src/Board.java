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
	
	public Board(int squareSideCount)
	{
		myHeight = squareSideCount;
		myWidth = squareSideCount;
		myBoard = new String[myHeight][myWidth];
	}
	
	/**
	 * Generate a random board of characters
	 */
	public void roll()
	{
		//Not allow duplicates for now
		HashSet<String> all = new HashSet<String>();
		int count = getHeight() * getWidth();
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
		for(int i=0; i < getHeight(); ++i)
		{
			for(int j=0; j < getWidth(); ++j)
			{
				myBoard[i][j] = data.pop();
			}
		}
		
		data = null;
	}
	
	/**
	 * Get all vertical substrings from the board between vertical indexes in
	 * forward direction
	 * @param endPosition Ending vertical position (inclusive)
	 * @return All vertical substrings (inclusive)
	 */
	public Vector<String> getVerticalSubstringsForward(int startPosition, int endPosition)
	{
		Vector<String> substrings = new Vector<String>();
		for(int vPos=startPosition; vPos <= endPosition; ++vPos)
		{
			for(int hStart=0; hStart < getHeight(); ++hStart)
			{
				StringBuffer buffer = new StringBuffer();

				for(int hPos=hStart; hPos < getHeight(); ++hPos)
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
	public Vector<String> getVerticalSubstringsBackward(int startPosition, int endPosition)
	{
		Vector<String> substrings = new Vector<String>();
		for(int vPos=startPosition; vPos < endPosition; ++vPos)
		{
			for(int hStart=getHeight() - 1; hStart >= 0; --hStart)
			{
				StringBuffer buffer = new StringBuffer();

				for(int hPos= hStart; hPos >= 0; --hPos)
				{
					buffer.append(myBoard[hPos][vPos]);
					addUnique(substrings, buffer.toString());
				}
			}
		}
		
		return substrings;
	}
	
	public Vector<String> getVerticalSubstrings(int startPosition, int endPosition)
	{
		Vector<String> substrings = new Vector<String>();

		Vector<String> directions = getVerticalSubstringsForward(startPosition, endPosition-1);
		directions.addAll(getVerticalSubstringsBackward(startPosition, endPosition));
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
	public Vector<String> getHorizontalSubstringsForward(int startPosition, int endPosition)
	{
		Vector<String> substrings = new Vector<String>();
		for(int hPos=startPosition; hPos <= endPosition; ++hPos)
		{
			for(int vStart=0; vStart < getWidth(); ++vStart)
			{
				StringBuffer buffer = new StringBuffer();

				for(int vPos=vStart; vPos < getWidth(); ++vPos)
				{
					buffer.append(myBoard[hPos][vPos]);
					addUnique(substrings, buffer.toString());
				}
			}
		}
		
		return substrings;
	}
	
	/**
	 * Get all horizontal substrings from the board between horizontal indexes in
	 * backwards direction
	 * @param endPosition Ending horizontal position (inclusive)
	 * @return All horizontal substrings (inclusive)
	 */
	public Vector<String> getHorizontalSubstringsBackward(int startPosition, int endPosition)
	{
		Vector<String> substrings = new Vector<String>();
		for(int hPos=startPosition; hPos <= endPosition; ++hPos)
		{
			for(int vStart=getWidth() - 1; vStart >= 0; --vStart)
			{
				StringBuffer buffer = new StringBuffer();

				for(int vPos= vStart; vPos >= 0; --vPos)
				{
					buffer.append(myBoard[hPos][vPos]);
					addUnique(substrings, buffer.toString());
				}
			}
		}
		
		return substrings;
	}
	
	public Vector<String> getHorizontalSubstrings(int startPosition, int endPosition)
	{
		Vector<String> substrings = new Vector<String>();

		Vector<String> directions = getHorizontalSubstringsForward(startPosition, endPosition-1);
		directions.addAll(getHorizontalSubstringsBackward(startPosition, endPosition-1));
		for(String e : directions)
		{
			addUnique(substrings, e);
		}

		
		return substrings;
	}
	
	public Vector<String> getVerticalLPartDown(int vPos, int hStart)
	{
		Vector<String> substrings = new Vector<String>();
		StringBuffer buffer = new StringBuffer();
		for(int hPos=hStart; hPos < getHeight(); ++hPos)
		{
			buffer.insert(0,myBoard[hPos][vPos]);
			addUnique(substrings, buffer.toString());
		}
		return substrings;
	}
	
	public Vector<String> getVerticalLPartUp(int vPos, int hStart)
	{
		Vector<String> substrings = new Vector<String>();
		StringBuffer buffer = new StringBuffer();
		for(int hPos=hStart; hPos >= 0; --hPos)
		{
			buffer.append(myBoard[hPos][vPos]);
			addUnique(substrings, buffer.toString());
		}
		return substrings;
	}
	
	public Vector<String> getHorizontalLDown()
	{
		Vector<String> substrings = new Vector<String>();

		for(int HPos=0; HPos < getWidth(); ++ HPos)
		{
			int VPos = 0;
			int LStart = HPos + 1;
	
			Vector<String> hDir = this.getHorizontalSubstringsForward(HPos, HPos);
			
			// Make all the Downward components for each H position
			Vector<Vector<String>> lParts = new Vector<Vector<String>>();
			for(int i=VPos; i < getWidth(); ++i)
			{
				lParts.add(getVerticalLPartDown(i, LStart));
			}
			int mod = getWidth();
			int count = 0;
			int vPos = 0;
			Vector<String> vPart = lParts.elementAt(vPos++);
			for(String tail : hDir)
			{
				for(String head : vPart)
				{
					addUnique(substrings, head + tail);
				}
				++count;
	
				if(mod != 0 && (count % mod == 0))
				{
					if(vPos < getWidth())
					{
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
	
	public Vector<String> getHorizontalLUp()
	{
		 String[][] originalBoard = myBoard;
		myBoard = inverseBoard(originalBoard);
		Vector<String> result = getHorizontalLDown();
		myBoard = originalBoard;
		return result;
		
	}
	
	public Vector<String> getL()
	{
		Vector<String> result = getHorizontalLDown();
		result.addAll(this.getHorizontalLUp());
		return result;
		
	}	
	public Vector<String> getAllSubstrings()
	{
		int side = getWidth();
		Vector<String> substrings = new Vector<String>();
		Vector<String> working = this.getVerticalSubstrings(side, side);
		working.addAll(this.getHorizontalSubstrings(side, side));
		working.addAll(getL());
		
		for(String E : working)
		{
			addUnique(substrings, E);
		}


		return substrings;
	}

	public int getHeight()
	{
		return myHeight;
	}
	
	public int getWidth()
	{
		return myWidth;
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
	 * Private print given board to string.
	 * Kept for debug reasons
	 * @param board Board to print to screen
	 */
	private void printBoardWorker(String[][] board)
	{
		StringBuffer line = new StringBuffer(getWidth()*2);
		for(int i=0; i < getHeight(); ++i)
		{
			for(int j=0; j < getWidth(); ++j)
			{
				line.append(board[i][j]);
				line.append(" ");
			}
			System.out.println(line.toString());
			line = new StringBuffer(getWidth()*2);
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
		String[][] inverse = new String[myHeight][myWidth];
		int reverseStart = getHeight() - 1;
		
		for(int i=0 ; i < getHeight(); ++i)
		{
			for(int j=0; j < getWidth(); ++j)
			{
				inverse[reverseStart-i][reverseStart - j] = original[i][j];
			}
		}
		
		return inverse;
	
	}
	
	/**
	 * First index of myBoard Array
	 */
	private int myHeight = 0;
	
	/**
	 * Second index of myBoard Array
	 */
	private int myWidth = 0;
	
	/**
	 * Board of data with defined in [myHeight][myWidth]
	 */
	private String[][] myBoard = null;
}
