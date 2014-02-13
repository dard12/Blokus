package blokus.game.junit;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;


import org.junit.Test;

import blokus.game.Board;
import blokus.game.Game;
import blokus.game.Parser;
import blokus.game.Piece;

/**
 * Test all methods related to the game board class, Board.
 * @author Hsing
 *
 */
public class ParserTest {

	/**
	 * Test that the size of pieces returned is correct
	 */
	@Test
	public void testPieceSize() {
		String filename = "pieces.txt";
		Parser newParser = new Parser();
		Vector<Piece> allPieces = newParser.parse(filename);
		int size = allPieces.size();
		
		assertEquals(size, 21);
	}
	
	/**
	 * Test that all pieces are populated correctly.
	 */
	@Test
	public void testPiecesNotNull() {
		String filename = "pieces.txt";
		Parser newParser = new Parser();
		Vector<Piece> allPieces = newParser.parse(filename);
		
		for (int i = 0; i < 21; i++)
			assertNotNull(allPieces.elementAt(i));
	}
	
	/**
	 * Test that pieces are contructed with correct occupied space
	 */
	@Test
	public void testPieceOccupied() {
		String filename = "pieces.txt";
		Parser newParser = new Parser();
		Vector<Piece> allPieces = newParser.parse(filename);
		Piece piece = allPieces.elementAt(0);
		Point testPoint = piece.getPoints().nextElement();
		int x = testPoint.x;
		int y = testPoint.y;
		
		assertEquals(x, 0);
		assertEquals(y, 0);
	}

}
