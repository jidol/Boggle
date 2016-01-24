import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
//		for(String e: playArea.getAllSubstrings())
//			System.out.println(e);
		FileInputStream jsonDataStream = null;
		BinarySearchTree<String> tree = new BinarySearchTree<String>("a");
		try
		{
			jsonDataStream = getDictionaryStream();
			JSONTokener tokenizer = new JSONTokener(jsonDataStream);
			JSONObject jObject = new JSONObject(tokenizer);
			JSONArray dictArray = jObject.getJSONArray("dictionary");
			System.out.println("Array Length = " + dictArray.length());
			for(int i=0; i < dictArray.length(); ++i)
			{
					tree = (BinarySearchTree)tree.insert(dictArray.get(i).toString());
			}
			jObject = null;
			tokenizer = null;
			
		}
		catch(FileNotFoundException ex)
		{
			System.out.println(ex.toString());
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString());
		}
		finally
		{
			try
			{
				if(null != jsonDataStream)
				{
					jsonDataStream.close();
				}
			}
			catch(IOException e)
			{
				System.out.println("Error closing file");
			}
		}
	}
	
	public static FileInputStream getDictionaryStream() throws FileNotFoundException
	{
		return new FileInputStream("./resources/Dictionary.json");
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
