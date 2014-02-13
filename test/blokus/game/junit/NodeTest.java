package blokus.game.junit;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;


import org.junit.Test;

import blokus.game.Board;
import blokus.game.Game;
import blokus.game.Node;
import blokus.game.Parser;
import blokus.game.Piece;

/**
 * Test all methods related to the game board class, Board.
 * @author Hsing
 *
 */
public class NodeTest {

	/**
	 * Test copy constructor on one piece case
	 */
	@Test
	public void testSize() {
		Vector<Point> points = new Vector<Point>();
		Point test = new Point(0, 0);
		points.add(test);
		Node original = new Node(points, 0);
		
		Node copy = new Node(original);
		int size = copy.getPoints().size();
		
		assertEquals(size, 1);
	}
	
	/**
	 * Test copy constructor on blank node
	 */
	@Test
	public void testSizeZero() {
		Vector<Point> points = new Vector<Point>();
		Node original = new Node(points, 0);
		
		Node copy = new Node(original);
		int size = copy.getPoints().size();
		
		assertEquals(size, 0);
	}
}
