package blokus.game;

/**
 * Extension of the Player class to take turns by prompting the user.
 * @author Hsing
 *
 */
public class UserPlayer extends Player{

	public UserPlayer(int setColor) {
		super(setColor);
	}

	/**
	 * Take a turn by listening for an action from the GUI.
	 */
	@Override
	public boolean takeTurn() {
		Game.wait = true;
		
		while (Game.wait) {
			//TODO: get rid of busy wait for user input
		}
		
		return true;
	}

}
