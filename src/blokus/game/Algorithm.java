package blokus.game;

/**
 * Algorithm class from which all decision making algorithms inherit.
 * Holds all generic functions that are necessary for 
 * decision making. 
 * @author Hsing
 *
 */
public abstract class Algorithm {
	/**
	 * Make a move in the current state of the
	 * game.
	 * @param currState current game state
	 * @return 
	 */
	abstract public boolean move(Board currState, int player);
	
	/**
	 * Evaluation function for the current state of the game.
	 * @param currState current game state
	 * @param playerId of the player calling evaluate
	 * @return the value of the state
	 */
	public int evaluate(Board currState, int playerId) {
		int height = currState.getHeight();
		int width = currState.getWidth();
		
		int[] colorCount = new int[4];
		
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int color = currState.getColor(x, y);
				
				if (color == Board.EMPTY)
					continue;
				
				colorCount[color]++;
			}
		}

		// Estimate by summing my squares as positive and others'
		// squares as negative.
		int stateEstimate = 0;
		for (int i = 0; i < 4; i++)
		{
			int currCount = colorCount[i];
			if (i == playerId)
				stateEstimate += currCount;
			else
				stateEstimate -= currCount;
		}
		
		return stateEstimate;
	}
}
