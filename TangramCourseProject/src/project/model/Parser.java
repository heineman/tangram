package project.model;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class Responsible for storing (and loading) puzzles from disk. Only class aware of XML.
 * 
 * Here is a sample XML file that is written/parsed by this class. 

 
  <?xml version="1.0"?>
    <puzzle>
      <solution>
        <piece id="2">
          <x>73</x>
          <y>29</y>
          <rotate>315</rotate>
          <flip>true</flip>
        </piece>
        <piece id="1">
          ...
        </piece>
      </solution>
      <piecesInPlay>
        <piece id="6">
          <x>-119</x>
          <y>-253</y>
          <rotate>315</rotate>
          <flip>false</flip>
        </piece>
      </piecesInPlay>
    </puzzle>

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
	public static final String flipTag   = "flip";
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
		print.println(element(flipTag,   Boolean.toString(piece.isFlipped())));
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
	
	/** Interpret XML element as a PlacedPiece for given set. */
	static PlacedPiece getPlacedPiece(TangramSet set, Node node) {
	    int x = 0;
	    int y = 0;
	    int rotate = PlacedPiece.NO_ROTATION;
	    boolean flip = PlacedPiece.NO_FLIP;
	    int id = Integer.parseInt(node.getAttributes().getNamedItem(idAtt).getNodeValue());
	    
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	    	node = nodeList.item(i);
        	if (node.getNodeType() == Node.ELEMENT_NODE) {
		        String name = node.getNodeName();
		        String val = node.getFirstChild().getNodeValue();
		        if (name.equals(xTag)) {
		        	x = Integer.valueOf(val);
		        } else if (name.equals(yTag)) {
		        	y = Integer.valueOf(val);
		        } else if (name.equals(rotateTag)) {
		        	rotate = Integer.valueOf(val);
		        } else if (name.equals(flipTag)) {
		        	flip = Boolean.valueOf(val);
		        }
        	}
	    }

	    Optional<TangramPiece> foundPiece = set.find(id);
	    if (!foundPiece.isPresent()) {
	    	throw new RuntimeException ("Invalid piece ID (" + id + ") found.");
	    }
	    
	    return new PlacedPiece(foundPiece.get(), flip, Puzzle.Scale, rotate, new Point (x,y));
	}
	
	/** Interpret XML element using given Tangram Set. */
	static ArrayList<PlacedPiece> getPieces(TangramSet set, Node node) {
	    ArrayList<PlacedPiece> list = new ArrayList<>();
	    
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	    	node = nodeList.item(i);
        	if (node.getNodeType() == Node.ELEMENT_NODE) {
        		PlacedPiece piece = getPlacedPiece(set, node);
        		list.add(piece);
        	}
	    }
	    
	    return list;
	}
	
	/** Load from XML file a puzzle based on the given Tangram Set. */
	public static Optional<Puzzle> parse(TangramSet set, File puzzleFile) {
		try (FileInputStream input = new FileInputStream(puzzleFile)) {
			return tryParse(set, input);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return Optional.empty();
		}
	}
	
	/** Load from XML file a puzzle based on the given Tangram Set. */
	static Optional<Puzzle> tryParse(TangramSet set, InputStream input) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder docBuilder;
        
        try {
	    	docBuilder = factory.newDocumentBuilder();
	        Document doc = docBuilder.parse(input);
	         
	        Node solution = doc.getElementsByTagName(solutionTag).item(0);
	        ArrayList<PlacedPiece> list_sol = getPieces(set, solution);
	        
	        Node inPlay = doc.getElementsByTagName(piecesInPlayTag).item(0);
	        ArrayList<PlacedPiece> list_inp = getPieces(set, inPlay);
	        
	        Puzzle puzzle = new Puzzle(list_sol.iterator());
	        for (PlacedPiece p : list_inp) {
	        	puzzle.add(p);
	        }
	            
	        return Optional.of(puzzle);
        } catch (ParserConfigurationException | SAXException | IOException e) {
        	e.printStackTrace();
        	return Optional.empty();
        }
	}
}
