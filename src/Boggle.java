import org.json.JSONObject;

public class Boggle {

	/**
	 * Test Runner
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board playArea = new Board(4);
		playArea.roll();
		playArea.printBoard();
		
		// Print substrings for now 
		for(String e: playArea.getAllSubstrings())
			System.out.println(e);
	}
	
	/**
	 * Use spell checker here from outside library
	 * @param word Word to check
	 * @return True if spelled correctly otherwise false
	 */
	public static boolean checkSpelling(String word)
	{
		
		 
		 return false;
	}

}
