package project.model.set;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import project.model.Coordinate;
import project.model.PlacedPiece;
import project.model.TangramPiece;
import project.model.TangramSet;

/**
 * Lesser known, though still widespread, variation from Japan.
 * 
 * https://arxiv.org/pdf/1407.1923.pdf
 */
public class ChieNoIta extends AbstractFactory {
	/** Name for this set. */
	public static final String name = "Sei Shonagon Chie No Ita";
	
	// define 10 coordinates used for all pieces, defined within unit square
	final static Coordinate c0  = new Coordinate(0,    0);
	final static Coordinate c1  = new Coordinate(0.5,  0);
	final static Coordinate c2  = new Coordinate(0.75, 0.25);
	final static Coordinate c3  = new Coordinate(0.75, 0.75);
	final static Coordinate c4  = new Coordinate(1,    0);
	final static Coordinate c5  = new Coordinate(1,    0.5);
	final static Coordinate c6  = new Coordinate(0.25, 0.25);
	final static Coordinate c7  = new Coordinate(0,    0.5);
	final static Coordinate c8  = new Coordinate(0.25, 0.75);
	final static Coordinate c9  = new Coordinate(0.5,  0.5);
	final static Coordinate c10 = new Coordinate(1,    1);
	final static Coordinate c11 = new Coordinate(0,    1);
	final static Coordinate c12 = new Coordinate(0.5,  1);

	/** Standard Tangram set. */
	public TangramSet produce() {
		TangramSet set = new TangramSet();
		set.add(new TangramPiece(1, new Coordinate[] { c0, c1, c2, c3}));
		set.add(new TangramPiece(2, new Coordinate[] { c1, c4, c5}));
		set.add(new TangramPiece(3, new Coordinate[] { c2, c5, c10, c3 }));
		set.add(new TangramPiece(4, new Coordinate[] { c9, c10, c12, c8 }));
		set.add(new TangramPiece(5, new Coordinate[] { c11, c7, c12 }));
		set.add(new TangramPiece(6, new Coordinate[] { c7, c6, c9, c8 }));
		set.add(new TangramPiece(7, new Coordinate[] { c7, c0, c6}));
		return set;
	}
	
	/** Standard solution of PlacedPieces for initial arrangement of TangramPieces. */
	public Iterator<PlacedPiece> solution(int scale) {
		ArrayList<PlacedPiece> solution = new ArrayList<PlacedPiece>();
		for (TangramPiece piece : produce()) {
			PlacedPiece p = new PlacedPiece(piece, PlacedPiece.NO_FLIP, scale, PlacedPiece.NO_ROTATION, new Point (0, 0));
			solution.add(p);
		}
		
		return solution.iterator();
	}

	/** Return the name of this set. */
	public String name() { return name; }
	
}
