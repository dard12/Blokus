package blokus.game;

import java.util.Vector;

/**
 * Where all turn taking and tracking of current game state
 * takes place.
 * @author Hsing
 *
 */
public class Game {
	
	public static Board board;
	public static boolean endGame = false;
	public static boolean wait = false;
	public static Player currPlayer;
	public static Vector<Player> players;

	/**
	 * Main loop for all game logic.
	 * @param args
	 */
	public static void runGame() {
		board = new Board();
		players = createPlayers();
		parsePieces("pieces.txt", players);
		int turn = 0;
		boolean[] end = new boolean[4];
		
		while (!endGame) {
			currPlayer = players.elementAt(turn);
			end[turn] = !currPlayer.takeTurn();
			turn = incrementTurn(turn);
			
			boolean testEndGame = true;
			for (int i = 0; i < 4; i++) {
				if (end[i] == false)
					testEndGame = false;
			}
			
			endGame = testEndGame;
		}
		
		System.out.println("The game has ended.");
		
		while(true);
	}
	
	/**
	 * Create four players in a vector
	 * @return vector of players created
	 */
	private static Vector<Player> createPlayers() {
		// TODO: interface for choosing who is user and who is computer
		Player playerOne = new UserPlayer(Board.BLUE);
		Player playerTwo = new ComputerPlayer(Board.YELLOW);
		Player playerThree = new ComputerPlayer(Board.RED);
		Player playerFour = new ComputerPlayer(Board.GREEN);
		
		Vector<Player> players = new Vector<Player>();
		players.add(playerOne);
		players.add(playerTwo);
		players.add(playerThree);
		players.add(playerFour);
		
		return players;
	}
	
	/**
	 * Parse a given file to create a set of pieces for each player.
	 * Initialize each player's selected piece to the first.
	 * @param filename to create pieces from
	 * @param players that each receive a set of pieces
	 */
	private static void parsePieces(String filename, Vector<Player> players) {
		for (int i = 0; i < 4; i++)
		{
			//TODO: Don't repeatedly parse
			Parser newParser = new Parser();
			Vector<Piece> allPieces = newParser.parse(filename);
			players.elementAt(i).setPieces(allPieces);
		}
	}
	
	/**
	 * Increment turn counter
	 * @param turn to increment
	 * @return new turn
	 */
	public static int incrementTurn(int turn) {
		turn += 1;
		if (turn == 4)
			turn = 0;
		
		return turn;
	}
}
