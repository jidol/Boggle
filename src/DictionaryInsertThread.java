import org.json.JSONArray;

public class DictionaryInsertThread implements Runnable {
	
	public DictionaryInsertThread(JSONArray data, int start, 
			int end, BinarySearchTree<String> tree, int wordSize)
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
		
		int count = 0;
		for(int index = myStartIndex; index < myEndIndex; ++index)
		{
			String insertData = myData.get(index).toString();
			
			// Only add words we would end up using 
			if(this.myMaxWordSize >= insertData.length())
			{
				++count;
				insert(insertData);
			}			
		}
		System.out.println("Added:  " + count);


	}
	
	private void insert(String insertData)
	{
		try
		{
			myTree.insert(insertData);
		}
		catch(Exception ex)
		{
			System.out.println("Error inserting:  " + insertData);
			insert(insertData);
			
		}
	}
	
	private JSONArray myData = null;
	
	private int myStartIndex = 0;
	
	private int myEndIndex = 0;
	
	private BinarySearchTree<String> myTree = null;
	
	private int myMaxWordSize = -1;

}
