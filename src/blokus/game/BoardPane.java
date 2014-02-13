package blokus.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Mouse;
import org.apache.pivot.wtk.TablePane;

public class BoardPane extends TablePane{
	
	public final int SQUARE_SIZE = 23;
	public final int SPACING = 2;
	private final int SELECT_PANEL_X = 460;
	private final int PANEL_SIZE = 100;
	private int mouseX;
	private int mouseY;
	
	@Override
	protected boolean mouseMove(int x, int y) {
		mouseX = x;
		mouseY = y;
		return true;
	}
	
	/**
	 * Handle mouse clicks with either making a move or selecting
	 * a piece.
	 */
	@Override
	protected boolean mouseClick(Mouse.Button button, 
			int x, int y, int count) {
		int boardX = screenToCoord(x);
		int boardY = screenToCoord(y);
		Player player = Game.currPlayer;
		Board board = Game.board;
		Piece move = player.getSelectedPiece();

		if (isSelectingPiece(x, y))
			selectPiece(x, y);
		else if (board.isValidMove(boardX, boardY, move,
				player, board))
			Game.board = board.makeMove(boardX, boardY, move, player, board, false);
		else
			Game.wait = false;
		
		return true;
	}
	
	/**
	 * Repaint the canvas to reflect the model of the board.
	 */
	@Override
	public void paint(Graphics2D graphics) {
		File piecesrc = new File("pieces.PNG");
		BufferedImage pieceimg = null;
		
		try {
			pieceimg = ImageIO.read(piecesrc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int turnColor = Game.currPlayer.getColor();
		Color paintColor = matchColor(turnColor);
		graphics.setColor(paintColor);
		graphics.drawString("Turn", 50, SQUARE_SIZE * 21);

		graphics.drawImage(pieceimg, null, SELECT_PANEL_X, 0);
		drawBoard(graphics);
		drawSelectionPanel(graphics);
		repaint();
	}
	
	/**
	 * Check if a mouse click is for selecting a piece
	 * @param x of mouse click
	 * @param y of mouse click
	 * @return true if selecting
	 */
	private boolean isSelectingPiece(int x, int y) {
		if (x > SELECT_PANEL_X)
			return true;
		
		return false;
	}
	
	/**
	 * Select a piece for the player based on mouse click position
	 * @param x of mouse click
	 * @param y of mouse click
	 */
	private void selectPiece(int x, int y) {
		x = (x - SELECT_PANEL_X) / PANEL_SIZE;
		y =  y /PANEL_SIZE;
		int selection = (y * 3) + x;
		
		if(!Game.currPlayer.isUsedPiece(selection))
			Game.currPlayer.setSelection(selection);
	}
	
	/**
	 * Give the screen coordinates on the selection panel based
	 * on a selection
	 * @param selection
	 * @return screen position
	 */
	private int selectToX(int selection) {
		return (selection % 3) * PANEL_SIZE + SELECT_PANEL_X;
	}

	/**
	 * Give the screen coordinates on the selection panel based
	 * on a selection
	 * @param selection
	 * @return screen position
	 */
	private int selectToY(int selection) {
		return (selection / 3) * PANEL_SIZE;
	}
	
	/**
	 * Draw the current state of the board.
	 * @param graphics canvas to drawn on
	 */
	private void drawBoard(Graphics2D graphics) {
		int width = Game.board.getWidth();
		int height = Game.board.getHeight();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int screenX = coordToScreen(x);
				int screenY = coordToScreen(y);
				int size = SQUARE_SIZE - SPACING;

				if (setColor(x, y, graphics))
					graphics.fillRect(screenX, screenY, size, size);
				
				graphics.setColor(Color.BLACK);
				graphics.drawRect(screenX, screenY, size, size);
			}
		}
	}
	
	private void drawSelectionPanel(Graphics2D graphics) {
		for (int selection = 0; selection < 21; selection++) {
			int playerSelect = Game.currPlayer.getSelection();
			int x = selectToX(selection);
			int y = selectToY(selection);
			
			if (Game.currPlayer.isUsedPiece(selection)) {
				graphics.setColor(Color.BLACK);
				graphics.fillRect(x, y, PANEL_SIZE, PANEL_SIZE);
			}
			
			if (selection == playerSelect) {
				graphics.setColor(Color.YELLOW);
				graphics.drawRect(x, y, PANEL_SIZE, PANEL_SIZE);
			}
		}
		
		Piece selectedPiece = Game.currPlayer.getSelectedPiece();
		
		int width = 3;
		int height = 3;
		
		for (int x = -2; x < width; x++) {
			for (int y = -2; y < height; y++) {
				int screenX = mouseX + coordToScreen(x) - 10;
				int screenY = mouseY + coordToScreen(y) - 10;
				int size = SQUARE_SIZE - SPACING;

				graphics.setColor(Color.BLACK);				
				Point newPoint = new Point(x, y);
				
				if (selectedPiece.isInOccupied(newPoint))
					graphics.fillRect(screenX, screenY, size, size);
			}
		}
	}
	
	/**
	 * Translate a coordinate on the board to a screen position.
	 * @param coord on the board
	 * @return the screen position
	 */
	private int coordToScreen(int coord) {
		return (coord * SQUARE_SIZE);
	}
	
	/**
	 * Translate a screen position to a coordinate on the board.
	 * @param screen position
	 * @return coord on the board
	 */
	private int screenToCoord(int screen) {
		return (screen / SQUARE_SIZE);
	}
	
	/**
	 * Set the color being drawn based on where on the board is
	 * it being drawn.
	 * @param x coordinate on the board
	 * @param y coordinate on the board
	 * @param graphics canvas being drawn on
	 * @return true if set correctly, false if empty
	 */
	private boolean setColor(int x, int y, Graphics2D graphics) {
		int color = Game.board.getColor(x, y);
		Color paintColor = matchColor(color);
		
		if (paintColor == null)
			return false;
		
		graphics.setColor(paintColor);
		
		return true;
	}
	
	/**
	 * Match an integer color to an actual painting color.
	 * @param color integer format
	 * @return painting color, or null on error
	 */
	private Color matchColor(int color) {
		Color paintColor = null;
		
		if (color == Board.BLUE)
			paintColor = Color.BLUE;
		else if (color == Board.GREEN)
			paintColor = Color.GREEN;
		else if (color == Board.RED)
			paintColor = Color.RED;
		else if (color == Board.YELLOW)
			paintColor = Color.YELLOW;
		
		return paintColor;
	}
}
