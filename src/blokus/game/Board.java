package blokus.game;

import java.awt.Point;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Board class that holds all information about what squares
 * are taken on the board.
 * @author Hsing
 *
 */
public class Board {
	public static final int EMPTY = -1;
	public static final int BLUE = 0;
	public static final int YELLOW = 1;
	public static final int RED = 2;
	public static final int GREEN = 3;
	
	private int width = 20;
	private int height = 20;
	private int[][] squares;
	
	/**
	 * Initialize all squares to EMPTY at the start of the game.
	 */
	public Board() {
		squares = new int[width][height];
		
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				squares[x][y] = EMPTY;
			}
		}
	}
	
	/**
	 * Copy the original board into a new one.
	 * @param original board
	 */
	public Board(Board original) {
		squares = new int[width][height];
		
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				squares[x][y] = original.squares[x][y];
			}
		}
	}
	
	/**
	 * Set the square the designated color
	 * @param
	 * @param y
	 * @param color of the player making the move
	 */
	public void setSquare(int x, int y, int color) {
		if (isValidCoord(x, y) && isColor(x, y, EMPTY))
			squares[x][y] = color;
	}
	
	/**
	 * Make a move on the board with position and piece
	 * @param x position of move
	 * @param y position of move
	 * @param piece to make the move with
	 */
	public Board makeMove(int x, int y, Piece piece, 
			Player player, Board board, boolean sim) {
		Enumeration<Point> points = piece.getPoints();
		Board newBoard = new Board(board);
		int color = player.getColor();
		
		while(points.hasMoreElements()) {
			Point currPoint = points.nextElement();
			int pieceX = currPoint.x + x;
			int pieceY = currPoint.y + y;

			newBoard.setSquare(pieceX, pieceY, color);
			player.setUsedPiece();
		}
		
		if (!sim) {
			Writer.write(piece, color);
			Game.wait = false;
		}
		
		return newBoard;
	}
	
	/**
	 * Make a move on the board with position and piece
	 * @param x position of move
	 * @param y position of move
	 * @param piece to make the move with
	 */
	public Board makeSimMove(int x, int y, Piece piece, Player player) {
		Enumeration<Point> points = piece.getPoints();
		int color = player.getColor();
		Board nextBoard = new Board(this);
		
		while(points.hasMoreElements()) {
			Point currPoint = points.nextElement();
			int pieceX = currPoint.x + x;
			int pieceY = currPoint.y + y;
			nextBoard.setSquare(pieceX, pieceY, color);
		}
		
		return nextBoard;
	}
	
	/**
	 * Show if a given move is valid
	 * @param x of the move
	 * @param y of the move
	 * @param piece of the move
	 * @return true if valid
	 */
	public boolean isValidMove(int x, int y, Piece piece, 
			Player player, Board board) {
		int selection = piece.getId();
		if (player.isUsedPiece(selection))
			return false;
		
		Enumeration<Point> points = piece.getPoints();
		int color = player.getColor();
		boolean isDiagonal = false;
		
		while(points.hasMoreElements()) {
			Point currPoint = points.nextElement();
			int pieceX = currPoint.x + x;
			int pieceY = currPoint.y + y;
						
			if (!isValidCoord(pieceX, pieceY))
				return false;
			
			if (!Game.board.isEmpty(pieceX, pieceY))
				return false;
			
			if (isTouching(pieceX, pieceY , color))
				return false;
			
			if (isDiagonal(pieceX, pieceY, color) || isCorner(pieceX, pieceY))
				isDiagonal = true;
		}
		
		return isDiagonal;
	}
	
	/**
	 * Check if a point is touching any other square of the same
	 * color
	 * @param x of point
	 * @param y of point
	 * @param color to check against
	 * @return true if touching
	 */
	private boolean isTouching(int x, int y, int color) {
		if (isColor(x, y - 1, color) ||
			isColor(x, y + 1, color) ||
			isColor(x + 1, y, color) ||
			isColor(x - 1, y, color))
			return true;
			
		return false;
	}
	
	/**
	 * Check if a point is diagonal to any other square of the same
	 * color
	 * @param x of point
	 * @param y of point
	 * @param color to check against
	 * @return true if diagonal
	 */
	private boolean isDiagonal(int x, int y, int color) {
		if (isColor(x - 1, y - 1, color) ||
				isColor(x + 1, y - 1, color) ||
				isColor(x - 1, y + 1, color) ||
				isColor(x + 1, y + 1, color))
			return true;
				
		return false;
	}
	
	/**
	 * Check if a point is a corner of the board.
	 * @param x of point
	 * @param y of point
	 * @return true if is corner
	 */
	private boolean isCorner(int x, int y) {
		boolean isCorner = false;
		
		if (x == 0 && y == 0)
			isCorner = true;
		else if (x == width - 1 && y == 0)
			isCorner = true;
		else if (y == height - 1 && x == 0)
			isCorner = true;
		else if (y == height - 1 && x == width - 1)
			isCorner = true;
		
		return isCorner;
	}
	
	/**
	 * Check if a given square is of the specified color
	 * @param x
	 * @param y
	 * @param color being specified
	 * @return true if colors are the same, false otherwise
	 */
	public boolean isColor(int x, int y, int color) {
		if (isValidCoord(x, y))
			return (squares[x][y] == color);
		
		return false;
	}
	
	/**
	 * Return the color of the given square.
	 * @param x
	 * @param y
	 * @return the color of the square, or -1 for invalid
	 */
	public int getColor(int x, int y) {
		if (isValidCoord(x, y))
			return (squares[x][y]);
		
		return -1;
	}
	
	/**
	 * Check if the x and y are within board dimensions.
	 * @param x
	 * @param y
	 * @return true if valid, false otherwise.
	 */
	private boolean isValidCoord(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return false;
		
		return true;
	}
	
	/**
	 * Check if a given position is empty
	 * @param x position
	 * @param y position
	 * @return true if empty
	 */
	private boolean isEmpty(int x, int y) {
		if (getColor(x, y) == EMPTY)
			return true;

		return false;
	}
	
	// Getters and Setters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
