import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class Boggle {

	/**
	 * Test Runner
	 */
	public static void main(String[] args) {
		try
		{
			boggle();

		}
		catch(Exception ex)
		{
			System.out.println("Something thrown:  " + ex.toString());
		}
	}
	
	public static void boggle()
	{
		int wordSize = 3;
		BinarySearchTree<String> tree = getDictionary(wordSize*2-1);
		Board playArea = new Board(wordSize);
		playArea.roll();
		playArea.printBoard();
		
		Vector<String> all = playArea.getAllSubstrings();
		for(String e: all)
		{
			try
			{
				if(null != tree.findNodeByValue(e.toUpperCase()))
				{
					System.out.println("Word Detected:  " + e);
				}
				
			}
			catch(Exception ex)
			{
				System.out.println("Exception in find: " + ex.toString());
			}
		}
		
			
	}
	
	public static BinarySearchTree<String> getDictionary(int wordSize)
	{
		FileInputStream jsonDataStream = null;
		BinarySearchTree<String> tree = null;
		try
		{
			jsonDataStream = getDictionaryStream();
			JSONTokener tokenizer = new JSONTokener(jsonDataStream);
			JSONObject jObject = new JSONObject(tokenizer);
			JSONArray dictArray = jObject.getJSONArray("dictionary");
			
			// Thread approach only works because we know the input is in alpha order
			tree = buildTree(dictArray, wordSize);
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
		
		return tree;
	}
	
	public static BinarySearchTree<String> buildTree(JSONArray dictArray, int wordSize)
	{
		int end = dictArray.length();
		int half = (int)(end * 0.5);
		AVLTree<String> tree = new AVLTree<String>(dictArray.get(half).toString());
		Thread one = makeThreadFromRange(0, end, dictArray, tree, wordSize);
		
		one.start();

		try
		{
			one.join();

		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
		
		return tree;
	}
	
	public static Thread makeThreadFromRange(int start, int end, JSONArray data, 
			AVLTree<String> tree, int wordSize)
	{
		DictionaryInsertThread runnable = new DictionaryInsertThread(data, start, 
				end, tree, wordSize);
		return new Thread(runnable);

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
