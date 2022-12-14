import java.util.Iterator;
import java.util.NoSuchElementException;

import StackAndQueuePackage.*;

public class BinaryTree<T> implements BinaryTreeInterface<T> {
	private BinaryNode<T> root;

	public BinaryTree() {
		root = null;
	} // end default constructor

	public BinaryTree(T rootData) {
		root = new BinaryNode<>(rootData);
	} // end constructor

	public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		privateSetTree(rootData, leftTree, rightTree);
	} // end constructor

	public void setTree(T rootData) {
		root = new BinaryNode<>(rootData);
	} // end setTree

	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
		privateSetTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
	} // end setTree

	private void privateSetTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		root = new BinaryNode<>(rootData);

		if ((leftTree != null) && !leftTree.isEmpty())
			root.setLeftChild(leftTree.root);

		if ((rightTree != null) && !rightTree.isEmpty()) {
			if (rightTree != leftTree)
				root.setRightChild(rightTree.root);
			else
				root.setRightChild(rightTree.root.copy());
		}

		if ((leftTree != null) && (leftTree != this))
			leftTree.clear();

		if ((rightTree != null) && (rightTree != this))
			rightTree.clear();
	}

	private class PreorderIterator implements Iterator<T> {
		private StackInterface<BinaryNode<T>> nodeStack;

		public PreorderIterator() {
			nodeStack = new LinkedStack<>();
			if (root != null)
				nodeStack.push(root);
		} // end default constructor
		public boolean hasNext() {
			return !nodeStack.isEmpty();
		}
		public T next() {
			BinaryNode<T> nextNode;
			if (hasNext()) {
				nextNode = nodeStack.pop();
				BinaryNode<T> leftChild = nextNode.getLeftChild();
				BinaryNode<T> rightChild = nextNode.getRightChild();
				if (rightChild != null)
					nodeStack.push(rightChild);
				if (leftChild != null)
					nodeStack.push(leftChild);
			} else {
				throw new NoSuchElementException();
			}

			return nextNode.getData();
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end PreorderIterator

	public void iterativePreorderTraverse() {
		StackInterface<BinaryNode<T>> nodeStack = new LinkedStack<>();
		if (root != null)
			nodeStack.push(root);
		BinaryNode<T> nextNode;
		while (!nodeStack.isEmpty()) {
			nextNode = nodeStack.pop();
			BinaryNode<T> leftChild = nextNode.getLeftChild();
			BinaryNode<T> rightChild = nextNode.getRightChild();
			if (rightChild != null)
				nodeStack.push(rightChild);
			if (leftChild != null)
				nodeStack.push(leftChild);
			System.out.print(nextNode.getData() + " ");
		} // end while
	} // end iterativePreorderTraverse

	private class InorderIterator implements Iterator<T> {
		private StackInterface<BinaryNode<T>> nodeStack;
		private BinaryNode<T> currentNode;
		public InorderIterator() {
			nodeStack = new LinkedStack<>();
			currentNode = root;
		} // end default constructor
		public boolean hasNext() {
			return !nodeStack.isEmpty() || (currentNode != null);
		} // end hasNext
		public T next() {
			BinaryNode<T> nextNode = null;
			while (currentNode != null) {
				nodeStack.push(currentNode);
				currentNode = currentNode.getLeftChild();
			} // end while

			if (!nodeStack.isEmpty()) {
				nextNode = nodeStack.pop();
				assert nextNode != null;
				currentNode = nextNode.getRightChild();
			} else
				throw new NoSuchElementException();

			return nextNode.getData();
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end InorderIterator

	public void iterativeInorderTraverse() {
		StackInterface<BinaryNode<T>> nodeStack = new LinkedStack<>();
		BinaryNode<T> currentNode = root;
		while (!nodeStack.isEmpty() || (currentNode != null)) {
			while (currentNode != null) {
				nodeStack.push(currentNode);
				currentNode = currentNode.getLeftChild();
			} // end while
			if (!nodeStack.isEmpty()) {
				BinaryNode<T> nextNode = nodeStack.pop();
				assert nextNode != null;
				System.out.print(nextNode.getData() + " ");
				currentNode = nextNode.getRightChild();
			} // end if
		} // end while
	} // end iterativeInorderTraverse

	private class PostorderIterator implements Iterator<T> {
		private StackInterface<BinaryNode<T>> nodeStack;
		private BinaryNode<T> currentNode;
		public PostorderIterator() {
			nodeStack = new LinkedStack<>();
			currentNode = root;
		} // end default constructor

		public boolean hasNext() {
			return !nodeStack.isEmpty() || (currentNode != null);
		} // end hasNext

		public T next() {
			BinaryNode<T> leftChild, rightChild, nextNode = null;

			while (currentNode != null) {
				nodeStack.push(currentNode);
				leftChild = currentNode.getLeftChild();
				if (leftChild == null)
					currentNode = currentNode.getRightChild();
				else
					currentNode = leftChild;
			} // end while

			if (!nodeStack.isEmpty()) {
				nextNode = nodeStack.pop();
				BinaryNode<T> parent = null;
				if (!nodeStack.isEmpty()) {
					parent = nodeStack.peek();
					if (nextNode == parent.getLeftChild()) currentNode = parent.getRightChild();
					else currentNode = null;
				} else
					currentNode = null;
			} else
				throw new NoSuchElementException();

			return nextNode.getData();
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end PostorderIterator

	private class LevelOrderIterator implements Iterator<T> {
		private QueueInterface<BinaryNode<T>> nodeQueue;
		public LevelOrderIterator() {
			nodeQueue = new LinkedQueue<>();
			if (root != null) nodeQueue.enqueue(root);
		} // end default constructor

		public boolean hasNext() {
			return !nodeQueue.isEmpty();
		} // end hasNext

		public T next() {
			BinaryNode<T> nextNode;
			if (hasNext()) {
				nextNode = nodeQueue.dequeue();
				BinaryNode<T> leftChild = nextNode.getLeftChild();
				BinaryNode<T> rightChild = nextNode.getRightChild();
				if (leftChild != null) nodeQueue.enqueue(leftChild);

				if (rightChild != null) nodeQueue.enqueue(rightChild);
			} else
				throw new NoSuchElementException();

			return nextNode.getData();
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end LevelOrderIterator

	@Override
	public T getRootData() {
		 T rootData = null;
		 if (root != null) rootData = root.getData();
		 return rootData;
	}

	@Override
	public int getHeight() {
		return root.getHeight();
	}

	@Override
	public int getNumberOfNodes() {
		return root.getNumberOfNodes();
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public void clear() {
		root = null;
	}
	protected void setRootNode(BinaryNode<T> rootNode) {
        root = rootNode;
    }

    protected BinaryNode<T> getRootNode() {
        return root;
    }

	@Override
	public Iterator<T> getPreorderIterator() {
		return new PreorderIterator();
	}

	@Override
	public Iterator<T> getPostorderIterator() {
		return new PostorderIterator();
	}

	@Override
	public Iterator<T> getInorderIterator() {
		return new InorderIterator();
	}

	@Override
	public Iterator<T> getLevelOrderIterator() {
		return new LevelOrderIterator();
	}
	
} // end BinaryTree
