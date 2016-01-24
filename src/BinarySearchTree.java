
public class BinarySearchTree<T extends Comparable > extends BinaryTree<T> {

	public BinarySearchTree(T value)
	{
		super(value);
		myParent = null;
	}
	
	public BinarySearchTree<T> getParent()
	{
		return myParent;
	}
	
	public void setParent(BinarySearchTree<T> parent)
	{
		myParent = parent;
	}

	/**
	 * Create a child node
	 * @param value Value to put in the node
	 * @return Child node
	 */
	protected BinaryTree<T> createElement(T value)
	{ return new BinarySearchTree(value); }
	


	/**
	 * Insert into the left child
	 * @param value Value to insert in the left child
	 * @return Tree result of inserting into left child
	 */
	protected BinarySearchTree<T> insertLeft(T value)
	{
		if(null == this.myLeft)
		{
			BinarySearchTree<T> newChild = (BinarySearchTree)createElement(value);
			newChild.setParent(this);
			this.myLeft = newChild;
		}
		else
		{
			this.myLeft.insert(value);
		}
		return this;

	}

	/**
	 * Insert value into right child
	 * @param value Value to insert in the right child
	 * @return Tree result of inserting value into right child
	 */
	protected BinarySearchTree<T> insertRight(T value)
	{
		if(null == this.myRight)
		{
			BinarySearchTree<T> newChild = (BinarySearchTree)createElement(value);
			newChild.setParent(this);
			this.myRight = newChild;
		}
		else
		{
			this.myRight.insert(value);
		}

		return this;

	}

	/**
	 * Insert value into the tree's children
	 * 
	 * Helper function
	 * 
	 * @param value Value to insert
	 * @return
	 */
	public BinaryTree<T> insert(T value)
	{
	    if(myValue.compareTo(value) < 0)
		{
			return insertRight(value);
		}
		else
		{
			return insertLeft(value);
		}
	}

	/**
	 * Find the node by Value returning the BinaryTree
	 * @param value Value to locate
	 * @return Tree with the value otherwise null
	 */
	public BinaryTree<T> findNodeByValue(T value)
	{
		if(value.compareTo(myValue) == 0)
		{
			return this;
		}
		else
		{
			if(value.compareTo(myValue) < 0 && null != myLeft)
			{
				return this.myLeft.findNodeByValue(value);
			}
			else if(value.compareTo(myValue) > 0 && null != myRight)
			{
				return this.myRight.findNodeByValue(value);
			}

			return null;
		}
	}

	
	/**
	 * Get the uncle node, needed for Red/Black Trees
	 * @return Uncle node
	 */
	protected BinarySearchTree<T> getUncle()
	{
		BinarySearchTree<T> uncle = null;
		BinarySearchTree<T> parent = getParent();
		if(null != parent)
		{
			Boolean leftChild = parent.getLeft() == this;

			uncle = leftChild ? (BinarySearchTree)parent.getRight() : 
				(BinarySearchTree)parent.getLeft();
		}

		return uncle;
	}
	
	/**
	 * Parent node of this tree
	 */
	protected BinarySearchTree<T> myParent;

}
