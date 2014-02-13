package blokus.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

/**
 * 
 * @author Hsing
 *
 */
public class Parser {
	
	final String SPACE_DELIMS = "[ ]+";
	final String CSV_DELIMS = "[,]";

	/**
	 * Parse the text file given into a collection of pieces.
	 * @param filename the file being parsed
	 */
	public Vector<Piece> parse(String filename) {		
		Vector<Piece> pieces = new Vector<Piece>();
		
		Path file = Paths.get(filename);
		try (
			InputStream in = Files.newInputStream(file);
			InputStreamReader inReader = new InputStreamReader(in);
		    BufferedReader reader = new BufferedReader(inReader)) {
			String line = null;
			int pieceId = 0;

			//Loop through each token of each line
		    while ((line = reader.readLine()) != null) {
		    	String[] tokens = line.split(SPACE_DELIMS);
		    	
		    	int size = tokens.length;
		    	if (size == 0)
		    		continue;
		    	
		    	Piece newPiece = new Piece();
		    	for (int i = 0; i < size; i++) {
		    		
		    		String currLine = tokens[i];
		    		if (currLine.length() == 0)
		    			continue;
		    		
		    		String[] coordinates = currLine.split(CSV_DELIMS);
		    		int x = Integer.parseInt(coordinates[0]);
		    		int y = Integer.parseInt(coordinates[1]);
		    		newPiece.addOccupied(x, y);
		    	}
		    	
		    	newPiece.setId(pieceId);
		    	pieces.add(newPiece);		    	
		    	pieceId++;
		    } 
		} catch (IOException error) {
		    System.out.println(error);
		}
		
		return pieces;
	}
}
