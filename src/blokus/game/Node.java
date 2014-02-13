package blokus.game;

import java.awt.Point;
import java.util.Vector;

/**
 * Quadtree class used to rotate the pieces.
 * @author Hsing
 *
 */
public class Node {
	private int level;
	private Vector<Point> points;
	
	private Node leftUp;
	private Node rightUp;
	private Node leftDown;
	private Node rightDown;
	
	/**
	 * Constructor setting the points and level of the node
	 * @param collection of points
	 * @param depth level of the node
	 */
	public Node(Vector<Point> collection, int depth) {
		points = collection;
		level = depth;
	}
	
	public Node(Node copy) {
		if (copy == null)
			return;
		
		Vector<Point> newPoints = new Vector<Point>();
		int size = copy.getPoints().size();
		Vector<Point> oldPoints = copy.getPoints();
		
		for (int i = 0; i < size; i++)
			newPoints.add(oldPoints.elementAt(i));
	}

	public Vector<Point> getPoints() {
		return points;
	}

	public void setPoints(Vector<Point> points) {
		this.points = points;
	}

	public Node getLeftUp() {
		return leftUp;
	}

	public void setLeftUp(Node leftUp) {
		this.leftUp = leftUp;
	}

	public Node getRightUp() {
		return rightUp;
	}

	public void setRightUp(Node rightUp) {
		this.rightUp = rightUp;
	}

	public Node getLeftDown() {
		return leftDown;
	}

	public void setLeftDown(Node leftDown) {
		this.leftDown = leftDown;
	}

	public Node getRightDown() {
		return rightDown;
	}

	public void setRightDown(Node rightDown) {
		this.rightDown = rightDown;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
