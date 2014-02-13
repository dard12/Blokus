package blokus.game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes information to file.
 * @author Hsing
 *
 */
public class Writer {
	private final static String PLAYER1 = "player1.txt";
	private final static String PLAYER2 = "player2.txt";
	private final static String PLAYER3 = "player3.txt";
	private final static String PLAYER4 = "player4.txt";
	
	/**
	 * Open a printer for the file specified.
	 * @param filename of file to print to
	 * @return printer for the file, null on fail
	 */
	public static PrintWriter openPrinter(String filename) {
		PrintWriter filePrinter = null;

		try {
			FileWriter writer = new FileWriter(filename, true);
			filePrinter = new PrintWriter(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePrinter;
	}
	
	/**
	 * Get the file that corresponds with a player color.
	 * @param color of the player
	 * @return file of the player
	 */
	private static String getPlayerFile(int color) {
		String file;
		
		if (color == 1)
			file = PLAYER1;
		else if (color == 2)
			file = PLAYER2;
		else if (color == 3)
			file = PLAYER3;
		else
			file = PLAYER4;
		
		return file;
			
	}
	
	/**
	 * Writes a recording of a move to the text file
	 * @param filePrinter to file being written on
	 */
	public static void write(Piece piece, int color) {
		String playerFile = getPlayerFile(color);
		PrintWriter writer = Writer.openPrinter(playerFile);
		int pieceId = piece.getId();

		String record = Writer.formatOutput(pieceId);
		writer.println(record);
		writer.close();
	}
	
	/**
	 * Format the record output given coordinate and color information
	 * @param pieceX coordinate
	 * @param pieceY coordinate
	 * @param color of player
	 * @return formatted output
	 */
	public static String formatOutput(int pieceId) {
		return "" + pieceId;
	}
}
