import org.json.JSONArray;

public class DictionaryInsertThread implements Runnable {
	
	public DictionaryInsertThread(JSONArray data, int start, 
			int end, AVLTree<String> tree, int wordSize)
	{
		if(end < start)
		{
			throw new IndexOutOfBoundsException("End before start");
		}
		
		myStartIndex = start;
		myEndIndex = end;
		myData = data;
		myTree = tree;
		myMaxWordSize = wordSize;
		
	}
	

	@Override
	public void run() {
		
		for(int index = myStartIndex; index < myEndIndex; ++index)
		{
			String insertData = myData.get(index).toString();
			
			// Only add words we would end up using 
			if(this.myMaxWordSize >= insertData.length())
			{
				insert(insertData);
			}			
		}
	}
	
	private void insert(String insertData)
	{
		try
		{
			myTree = (AVLTree<String>)(myTree.insert(insertData));
		}
		catch(Exception ex)
		{
			System.out.println("Error inserting:  " + insertData);
			insert(insertData);
			
		}
	}
	
	public AVLTree<String> getTree()
	{
		return myTree;
	}
	
	private JSONArray myData = null;
	
	private int myStartIndex = 0;
	
	private int myEndIndex = 0;
	
	private AVLTree<String> myTree = null;
	
	private int myMaxWordSize = -1;

}
