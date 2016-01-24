import java.util.concurrent.Semaphore;
import java.lang.InterruptedException;

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
	{ return new BinarySearchTree<T>(value); }
	

	

	/**
	 * Insert value into the tree's children
	 * 
	 * Helper function
	 * 
	 * @param value Value to insert
	 * @return
	 */
	public BinaryTree<T> insert(T value) throws InterruptedException
	{
		BinarySearchTree<T> node = this;
		do
		{
			Semaphore lock = node.getLock();

			lock.acquire();
			
			T cValue = node.getValue();

		    if(cValue.compareTo(value) < 0)
			{
				if(node.getLeft() != null)
				{
					lock.release();
					node = (BinarySearchTree<T>)node.getLeft();
				}
				else
				{
					node.setLeft(createElement(value));
					((BinarySearchTree)node.getLeft()).setParent(this);
					lock.release();
					return this;
				}
			}
			else
			{
				if(node.getRight() != null)
				{
					lock.release();
					node = (BinarySearchTree<T>)node.getRight();
				}
				else
				{
					node.setRight(createElement(value));
					((BinarySearchTree)node.getRight()).setParent(this);
					lock.release();
					return this;
				}			
			}
		} while(true);
	}

	/**
	 * Find the node by Value returning the BinaryTree
	 * @param value Value to locate
	 * @return Tree with the value otherwise null
	 */
	public BinaryTree<T> findNodeByValue(T value) throws InterruptedException
	{
		BinarySearchTree<T> node = this;
		Semaphore lock = null;

		do
		{
			if(null == node)
				return node;
			
			lock = node.getLock();
			lock.acquire();
			T cValue = node.getValue();
			if(cValue.compareTo(value) == 0)
			{
				lock.release();
				return node;
			}
			if(cValue.compareTo(value) < 0 && null != node.getLeft())
			{
					lock.release();
					node = (BinarySearchTree)node.getLeft();
					continue;
					
			}
			else if(cValue.compareTo(value) > 0 && null != node.getRight())
			{
				lock.release();
				node = (BinarySearchTree)node.getRight();
				continue;
			}
			
			lock.release();
			return null;
				
			
		} while(true);
		
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

			uncle = leftChild ? (BinarySearchTree<T>)parent.getRight() : 
				(BinarySearchTree<T>)parent.getLeft();
		}

		return uncle;
	}
	
	public Semaphore getLock()
	{
		return myLock;
	}
	
	
	/**
	 * Parent node of this tree
	 */
	protected BinarySearchTree<T> myParent;
	
	private Semaphore myLock = new Semaphore(1,true);

}
