package blokus.game.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import blokus.game.Board;

/**
 * Test all methods related to the game board class, Board.
 * @author Hsing
 *
 */
public class BoardTest {

	/**
	 * Test that board initializes to empty squares.
	 */
	@Test
	public void testBoardConstruct() {
		Board testBoard = new Board();
		int width = testBoard.getWidth();
		int height = testBoard.getHeight();
		
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				testBoard.isColor(x, y, Board.EMPTY);
			}
		}
	}
	
	/**
	 * Test that setting and comparing square colors work.
	 */
	@Test
	public void testSetSquare() {
		Board testBoard = new Board();
		
		testBoard.setSquare(10, 5, Board.RED);
		testBoard.setSquare(0, 2, Board.YELLOW);
		
		boolean checkRed = testBoard.isColor(10, 5, Board.RED);
		boolean checkYellow = testBoard.isColor(0, 2, Board.YELLOW);
		boolean checkNotBlue = testBoard.isColor(0, 2, Board.BLUE);
		
		assertTrue("Result: ", checkRed);
		assertTrue("Result: ", checkYellow);
		assertFalse("Result: ", checkNotBlue);
	}
}
