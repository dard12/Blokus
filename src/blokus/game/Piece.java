package blokus.game;

import java.awt.Point;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

/**
 * A class that uses a vector of Points to represent a single piece's
 * configuration.
 * 
 * parse text file and use helper
 * 
 * @author Hsing
 *
 */

public class Piece {
	private Hashtable<Point, Point> occupied;
	private int id;
	
	public Piece() {
		occupied = new Hashtable<Point, Point>();
	}
	
	/**
	 * Renew the occupied table
	 */
	public void killOccupied() {
		occupied = new Hashtable<Point, Point>();
	}
	
	/**
	 * Reconstruct the occupied with a tree
	 * @param currNode root of subtree
	 * @param x of the function
	 * @param y of the function
	 */
	public void reconstruct(Node currNode, int x, int y, int size) {
		if (currNode == null)
			return;
		
		if (size == 1) {
			Point newPoint = new Point(x, y);
			occupied.put(newPoint, newPoint);
			return;
		}
		
		reconstruct(currNode.getLeftUp(), x, y, size / 2);
		reconstruct(currNode.getRightUp(), x + size, y, size / 2);
		reconstruct(currNode.getLeftDown(), x, y + size, size / 2);
		reconstruct(currNode.getRightDown(), x + size, y + size, size / 2);
	}
	
	/**
	 * Create a root node with holding all occupied points
	 * @return root node
	 */
	public Node createRoot() {
		Enumeration<Point> keys = getPoints();
		Vector<Point> points = new Vector<Point>();
		
		while (keys.hasMoreElements()) {
			Point currPoint = keys.nextElement();
			points.add(currPoint);
		}
		
		Node root = new Node(points, 2);
		return root;
	}
	
	/**
	 * Build a subtree based on the current node.
	 * @param currNode node at root of subtree
	 */
	public void buildTree(Node currNode) {
		Vector<Point> points = currNode.getPoints();
		int size = points.size();
		
		if (size == 1)
			return;
		
		Vector<Point> upLeft = new Vector<Point>();
		Vector<Point> upRight = new Vector<Point>();
		Vector<Point> downLeft = new Vector<Point>();
		Vector<Point> downRight = new Vector<Point>();
		
		for (int i = 0; i < size; i++) {
			Point currPoint = points.elementAt(i);
			int x = currPoint.x + 4;
			int y = currPoint.y + 4;
			int level = currNode.getLevel();
			
			if (x < (4 / level)) {
				if (y < (4 / level))
					upLeft.add(currPoint);
				else
					upRight.add(currPoint);
			}
			else {
				if (y < (4 / level))
					downLeft.add(currPoint);
				else
					downRight.add(currPoint);
			}
			
			Node leftUp = new Node(upLeft, level + 1);
			Node rightUp = new Node(upRight, level + 1);
			Node leftDown = new Node(downLeft, level + 1);
			Node rightDown = new Node(downRight, level + 1);
			
			currNode.setLeftDown(leftDown);
			currNode.setRightDown(rightDown);
			currNode.setLeftUp(leftUp);
			currNode.setRightUp(rightUp);
		}
	}
	
	/**
	 * Create a rotated piece using the root node of a tree
	 * @param currNode root of subtree tree
	 */
	public void rotate(Node currNode) {
		Vector<Point> points = currNode.getPoints();
		int size = points.size();
		
		if (size == 1)
			return;
		
		Node leftDownCopy = new Node(currNode.getLeftDown());
		Node leftUpCopy = new Node(currNode.getLeftUp());
		Node rightDownCopy = new Node(currNode.getRightDown());
		Node rightUpCopy = new Node(currNode.getRightUp());
		
		currNode.setLeftUp(leftDownCopy);
		currNode.setRightUp(leftUpCopy);
		currNode.setRightDown(rightUpCopy);
		currNode.setLeftDown(rightDownCopy);
	}
	
	/**
	 * Test is target is unoccupied and not touching anything.
	 * @param target
	 * @return true if valid
	 */
	boolean isValidDiagonal(Point target) {
		return (!isInOccupied(target) && !isTouchingPoint(target));
	}
		
	/**
	 * Check if target is in occupied
	 * @param target
	 * @return true if in occupied
	 */
	public boolean isInOccupied(Point target) {
		return (occupied.containsKey(target));
	}
	
	/**
	 * Check if a target point touches any other point.
	 * @param target point
	 * @return true if another point touches target
	 */
	public boolean isTouchingPoint(Point target) {
			int targX = target.x;
			int targY = target.y;

			Point topSquare = new Point(targX, targY - 1);
			Point botSquare = new Point(targX, targY + 1);
			Point rightSquare = new Point(targX + 1, targY);
			Point leftSquare = new Point(targX - 1, targY);
			
			if (occupied.containsKey(topSquare) ||
				occupied.containsKey(botSquare) ||
				occupied.containsKey(rightSquare) ||
				occupied.containsKey(leftSquare))
				return true;
				
			return false;
	}
	
	/**
	 * Get all points that the piece occupies
	 * @return occupied points
	 */
	public Enumeration<Point> getPoints() {
		return occupied.keys();
	}
	
	/**
	 * Add a new point specified by x and y to the occupied Vector.
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void addOccupied(int x, int y) {
		Point newPoint = new Point(x, y);
		occupied.put(newPoint, newPoint);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
