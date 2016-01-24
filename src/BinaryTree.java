
/**
 * Simple binary tree port from my C++ impl
 * @author Jeff Idol
 *
 */
public class BinaryTree<T> {

	public BinaryTree(T value)
	{
		myValue = value;
		myLeft = null;
		myRight = null;
	}

	/**
	 * Insert value into the binary tree
	 * 
	 * NOTE: Not strictly binary as I'm enforcing height balance.
	 * 
	 * @return New tree with the value within
	 */
	public BinaryTree<T> insert(T value)
	{
		// Only insert new values
		if(null == findNodeByValue(value))
		{
			if(null == myLeft)
			{
				myLeft = new BinaryTree<T>(value);
			}
			else if(null == myRight)
			{
				myRight = new BinaryTree<T>(value);
			}
			else
			{
				int leftHeight = myLeft.getHeight();
				int rightHeight = myRight.getHeight();

				if(leftHeight < rightHeight)
				{
					myLeft = myLeft.insert(value);
				}
				else
				{
					myRight = myRight.insert(value);
				}
			}
		}
		return this;

	}

	/**
	 * Get the height of the tree
	 * @return Integer height of the tree
	 */
	public int getHeight()
	{
		int height = 1;

		if (null != myLeft)
		{
			height += myLeft.getHeight();
		}
		if (null != myRight)
		{
			height += myRight.getHeight();
		}

		return height;
	}

	/**
	 * Find a value in the tree returning the whole node containing it
	 * @param value Value to locate
	 * @return Node of the tree with Value within it otherwise null is returned
	 */
	public BinaryTree<T> findNodeByValue(T value)
	{
		if(value == myValue)
		{
			return this;
		}
		else
		{
			BinaryTree<T> result = null != myRight ? myRight.findNodeByValue(value) : null;
			if (null == result && null != myLeft)
			{
				result = myLeft.findNodeByValue(value);
			}

			return result;
		}
	}
	
	/**
	 * Value of the Node
	 */
	protected T myValue;
	
	/**
	 * Left child which should always be logically less than the myValue
	 */
	protected BinaryTree<T> myLeft = null;
	
	/**
	 * Right child which should always be logically greater than the myValue
	 */
	protected BinaryTree<T> myRight = null;
}
