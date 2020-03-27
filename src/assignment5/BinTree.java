package assignment5;

import java.awt.geom.Area;
import java.util.ArrayList;

public class BinTree {
	Node root;
	int size;
	
	public class Node {
		Node left, right;
		int data;

		public Node(int data) {
			this.data = data;
		}

		public String toString() {
			return data + "";
		}
	}

	public void insert(int value) {
		Node newNode = new Node(value);

		if (root == null) {
			root = newNode;
			size++;
		} else {
			Node focusNode = root;
			Node parent;
			while (true) {
				parent = focusNode;
				if (value < focusNode.data) {
					focusNode = focusNode.left;
					if (focusNode == null) {
						parent.left = newNode;
						size++;
						return;
					}
				} else {
					parent = focusNode;
					if (value > focusNode.data) {
						focusNode = focusNode.right;
						if (focusNode == null) {
							parent.right = newNode;
							size++;
							return;
						}
					}
				}
			}
		}
	}

	public Node search(int value) {
		Node focusNode = root;
		while (focusNode.data != value) {
			if (value < focusNode.data) {
				focusNode = focusNode.left;
			} else {
				focusNode = focusNode.right;
			}
			if (focusNode == null) {
				return null;
			}
		}
		return focusNode;
	}

	public void printInOrder(Node focusNode) {
		if (focusNode != null) {
			printInOrder(focusNode.left);
			System.out.print(focusNode + " ");
			printInOrder(focusNode.right);
		}
	}

	public void printPreOrder(Node focusNode) {
		if (focusNode != null) {
			System.out.print(focusNode + " ");
			printInOrder(focusNode.left);
			printInOrder(focusNode.right);
		}
	}

	public void printPostOrder(Node focusNode) {
		if (focusNode != null) {
			printInOrder(focusNode.left);
			printInOrder(focusNode.right);
			System.out.print(focusNode + " ");
		}
	}

	static Node leftMostNode(Node node) {
		while (node.left != null)
			node = node.left;
		return node;
	}

	static Node rightMostNode(Node node) {
		while (node.right != null)
			node = node.right;
		return node;
	}

	/*
	 * public Node inOrderNextNode(int value) { Node p=search(value); if(p==null) {
	 * return null; } if(p.right!=null) { Node focusNode=p.right; return
	 * leftMostNode(focusNode); } else { Node focusNode=root;
	 * focusNode=rightMostNode(focusNode); if(p==focusNode) { return null; } else {
	 * focusNode=root.left; focusNode=rightMostNode(focusNode); if(p==focusNode) {
	 * return root; } else { focusNode=root; while(focusNode.left!=p) {
	 * focusNode=focusNode.left; } return focusNode; } } } }
	 */

	public ArrayList<Node> addToInOrderList() {
		ArrayList<Node> inOrderList = new ArrayList<Node>();
		
		int heightCount=1;
		Node focusNode = root.left;
		while (focusNode != null) {
			if (focusNode.right != null) {
				inOrderList.add(0, focusNode.right);
			}
			inOrderList.add(0, focusNode);
			focusNode = focusNode.left;
			heightCount++;
		}
		inOrderList.add(root);

		focusNode = root.right;
		heightCount=1;
		while (focusNode != null) {
			if (focusNode.left != null) {
				inOrderList.add(focusNode.left);
			}
			inOrderList.add(focusNode);
			focusNode = focusNode.right;
			heightCount++;
		}
		return inOrderList;
	}
	

	public Integer inOrderNextPos(int value) {
		ArrayList<Node> inOrderList=addToInOrderList();
		Node focusNode = search(value);
		Integer pos = inOrderList.indexOf(focusNode);
		if (pos == size - 1 || pos == -1) {
			return null;
		} else {
			return pos + 1;
		}
	}

	public Node inOrderGetByPos(int pos) {
		ArrayList<Node> inOrderList=addToInOrderList();
		if(pos>size-1||pos<0) {
			return null;
		}
		Node focusNode=inOrderList.get(pos);
		return focusNode;
	}
	
	public int inOrderGetSubtreeHeight(Node focusNode) {
		if(focusNode.left==null&&focusNode.right==null) {
			return 0;
		}
		else {
			int leftSubTreeHeight = inOrderGetSubtreeHeight(focusNode.left);
			int rightSubTreeHeight = inOrderGetSubtreeHeight(focusNode.right);
			return Math.max(leftSubTreeHeight, rightSubTreeHeight) + 1;
		}
	}

	public static void main(String[] args) {
		BinTree binTree = new BinTree();
		binTree.insert(10);
		binTree.insert(5);
		binTree.insert(7);
		binTree.insert(15);
		binTree.insert(3);
		binTree.insert(20);
		binTree.insert(13);
		
		System.out.println("==Print inorder==");
		binTree.printInOrder(binTree.root);

		System.out.println("\n\n==Get next position inorder (position starts at 0)==");

		System.out.println("Position next to 3: " + binTree.inOrderNextPos(3));
		System.out.println("Position next to 10: " + binTree.inOrderNextPos(10));
		System.out.println("Position next to 20: " + binTree.inOrderNextPos(20));
		System.out.println("Position next to 100: " + binTree.inOrderNextPos(100));
		
		System.out.println("\n\n==Get value given position inorder (position starts at 0)==");
		System.out.println("Node at position 0: " + binTree.inOrderGetByPos(0));
		System.out.println("Node at position 6: " + binTree.inOrderGetByPos(6));
		System.out.println("Node at position 100: " + binTree.inOrderGetByPos(100));
		System.out.println("Node at position -100: " + binTree.inOrderGetByPos(-100));
		
		System.out.println("\n==Get height of subtree at given position inorder (position starts at 0)==");
		int heightAt3=binTree.inOrderGetSubtreeHeight(binTree.inOrderGetByPos(3));
		System.out.println("Subtree height at pos 3 (root): "+heightAt3);
		int heightAt6=binTree.inOrderGetSubtreeHeight(binTree.inOrderGetByPos(6));
		System.out.println("Subtree height at pos 6 (leaf): "+heightAt6);
		int heightAt1=binTree.inOrderGetSubtreeHeight(binTree.inOrderGetByPos(1));
		System.out.println("Subtree height at pos 1: "+heightAt1);
	}
}
