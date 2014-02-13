package blokus.game;

/**
 * Class for each simulated move on the game board
 * @author dard12
 *
 */
public class Move {

	private int pieceUsed;
	private Board board;
	
	public Move(int piece, Board nextBoard) {
		pieceUsed = piece;
		board = nextBoard;
	}
	
	public int getPieceUsed() {
		return pieceUsed;
	}
	
	public Board getBoard() {
		return board;
	}
}
