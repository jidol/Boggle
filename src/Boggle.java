
public class Boggle {

	/**
	 * Test Runner
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board playArea = new Board(4);
		playArea.roll();
		playArea.printBoard();
		for(String e: playArea.getAllSubstrings())
			System.out.println(e);
	}

}
