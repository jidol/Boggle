import java.lang.Comparable;
import java.util.concurrent.Semaphore;
import java.lang.InterruptedException;

public class AVLTree<T extends Comparable<T>> 
extends BinarySearchTree<T> {
	
	public AVLTree(T value)
	{
		super(value);
		myHeight = 0;
	}
	
	/**
	 * Create a child node
	 * @param value Value to put in the node
	 * @return Child node
	 */
	protected BinaryTree<T> createElement(T value)
	{ return ((BinaryTree<T>)new AVLTree<T>(value)); }
	
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
		super.insert(value);
		getLock().acquire();
		BinaryTree<T> result = (BinaryTree<T>)( balance());
		getLock().release();
		return result;
	}

	
	private short nodeHeight(AVLTree node)
	{
		return node != null ? (short)node.getHeight() : 0;
	}
	
	protected AVLTree<T> leftAVL()
	{
		return (AVLTree<T>)(getLeft());
	}
	protected AVLTree<T> rightAVL()
	{
		return (AVLTree<T>)(getRight());
	}
	
	private void fixheight()
	{
	    short leftHeight = nodeHeight(leftAVL());
	    short rightHeight = nodeHeight(rightAVL());
	    int heightReset = (leftHeight > rightHeight ? leftHeight : rightHeight) + 1;
	    setHeight(heightReset);
	}

	protected AVLTree<T> rotateRight() throws InterruptedException
	{
		AVLTree<T> oldLeft = leftAVL();
		oldLeft.getLock().acquire();
		myLeft = oldLeft.getRight();
		leftAVL().setParent((BinarySearchTree)this);
		oldLeft.setRight(this);
		setParent(oldLeft);
		fixheight();
		oldLeft.fixheight();
		oldLeft.getLock().release();
		return oldLeft;
	}

	protected AVLTree<T> rotateLeft() throws InterruptedException
	{
		AVLTree<T> oldRight = rightAVL();
		oldRight.getLock().acquire();
		setRight(oldRight.getLeft());
		rightAVL().setParent((BinarySearchTree)this);
		oldRight.setLeft(this);
		setParent(oldRight);
		fixheight();
		oldRight.fixheight();
		oldRight.getLock().release();
		return oldRight;
	}

	protected AVLTree<T> balance() throws InterruptedException
	{
	    fixheight();
		if( bFactor()==2 )
		{
			if( rightAVL() != null && rightAVL().bFactor() < 0 )
			{
				setRight(rightAVL().rotateRight());
				rightAVL().setParent((BinarySearchTree<T>)this);
			}
			return rotateLeft();
		}
		if( bFactor()==-2 )
		{
			if( leftAVL() != null && leftAVL().bFactor() > 0  )
			{
				setLeft(leftAVL().rotateLeft());
				leftAVL().setParent((BinarySearchTree)this);
			}
			return rotateRight();
		}
		return this;
	}
	
	private int bFactor()
	{
		return nodeHeight(rightAVL()) - nodeHeight(leftAVL());
	}

	public int getHeight()
	{
		return myHeight;
	}
	
	private void setHeight(int height)
	{
		myHeight = height;
	}
	private int myHeight;
}
