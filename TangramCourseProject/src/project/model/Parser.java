package project.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

/**
 * Class Responsible for storing (and loading) puzzles from disk. Only class aware of XML
 */
public class Parser {
	/** Suffix for puzzle. */
	public static String Suffix = "puzzle";

	/** XML Tags and Attribute names. */
	public static final String puzzleTag = "puzzle";
	public static final String pieceTag  = "piece";
	public static final String idAtt     = "id";
	public static final String xTag      = "x";
	public static final String yTag      = "y";
	public static final String rotateTag = "rotate";
	public static final String solutionTag   = "solution";
	public static final String piecesInPlayTag  = "piecesInPlay";
	
	/** Helper function for a single tag and its value. */
	static String element(String name, String value) {
		return String.format("<%s>%s</%s>", name, value, name);
	}
	
	/** Output a Piece as an XML tag with sub-elements for the essential attributes. */
	static void piece(PrintStream print, PlacedPiece piece) {
		print.println(String.format("<%s %s=\"%d\">", pieceTag, idAtt, piece.piece.id));
		print.println(element(xTag,      Integer.toString(piece.getTranslation().x)));
		print.println(element(yTag,      Integer.toString(piece.getTranslation().y)));
		print.println(element(rotateTag, Integer.toString(piece.getRotation())));
		print.println(String.format("</%s>", pieceTag));
	}
	
	/** Write puzzle contents to output file. */
	public static void write(File puzzleFile, Puzzle puzzle) throws IOException {
		try (PrintStream print = new PrintStream(puzzleFile)) {
			write(print, puzzle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	/** Helper method to write to stream. */
	static void write(PrintStream ps, Puzzle puzzle) {
		ps.println("<?xml version=\"1.0\"?>");
		ps.println(String.format("<%s>", puzzleTag));
		ps.println(String.format("<%s>", solutionTag));
			
		  for (Iterator<PlacedPiece> it = puzzle.solution(); it.hasNext(); ) {
			piece(ps, it.next());
		  }
		  ps.println(String.format("</%s>", solutionTag));
		
		  ps.println(String.format("<%s>", piecesInPlayTag));
		    for (Iterator<PlacedPiece> it = puzzle.pieces(); it.hasNext(); ) {
			  piece(ps, it.next());
		    }
		    ps.println(String.format("</%s>", piecesInPlayTag));
		
		    ps.println(String.format("</%s>", puzzleTag));
		ps.close();
	}
	
}
