package blokus.game;

/**
 * Extension of the Player class to take turns by calculation.
 * @author Hsing
 *
 */
public class ComputerPlayer extends Player{

	final AlphaBeta AI = new AlphaBeta();
	
	public ComputerPlayer(int setColor) {
		super(setColor);
	}

	public ComputerPlayer(Player origPlayer) {
		super(origPlayer);
	}

	/**
	 * Take turn by calculation of optimal move and making it.
	 */
	@Override
	public boolean takeTurn() {
		Game.wait = true;
		boolean success = AI.move(Game.board, getColor());
		Game.wait = false;
		
		return success;
	}

}
