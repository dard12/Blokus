package blokus.game;

import java.util.Vector;

/**
 * Abstract class to make sure all players of the game
 * implement turn taking.
 * @author Hsing
 *
 */
public abstract class Player {
	private Vector<Piece> pieces;
	private boolean[] usedPieces;
	private int selection;
	private int color;
	
	/**
	 * Initialize players with their designated color.
	 * @param setColor for this player
	 */
	public Player(int setColor) {
		color = setColor;
	}
	
	/**
	 * Create a simulation player object from an original
	 * @param original player
	 */
	public Player(Player original) {
		Vector<Piece> origPieces = original.getPieces();
		int size = origPieces.size();
		pieces = new Vector<Piece>();
		usedPieces = new boolean[size];
		
		for (int i = 0; i < size; i++) {
			Piece piece = origPieces.elementAt(i);
			pieces.add(piece);
			usedPieces[i] = original.getUsedPieces()[i];
		}
		
		color = original.getColor();
	}

	/**
	 * Make a move either by prompting user player or calculating
	 * for computer player.
	 */
	public abstract boolean takeTurn();

	/**
	 * Get the piece that corresponds with a selection.
	 * @return piece selected
	 */
	private Piece selectionToPiece(int select) {
		return pieces.elementAt(select);
	}
	
	/**
	 * Appoint a set to be the player's pieces on hand.
	 * @param set to be given to the player
	 */
	public void setPieces(Vector<Piece> set) {
		pieces = set;
		int size = set.size();
		usedPieces = new boolean[size];
	}

	//Getters + Setters
	public Piece getSelectedPiece() {
		return selectionToPiece(selection);
	}
	
	public Piece getSelectedPiece(int select) {
		return selectionToPiece(select);
	}
	
	public boolean isUsedPiece(int selection) {
		return usedPieces[selection];
	}
	
	public void setUsedPiece() {
		usedPieces[selection] = true;
	}
	
	public void setUsedPiece(int select) {
		usedPieces[select] = true;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}
	
	public int getSelection() {
		return selection;
	}

	public int getColor() {
		return color;
	}
	
	public Vector<Piece> getPieces() {
		return pieces;
	}

	public boolean[] getUsedPieces() {
		return usedPieces;
	}
}
