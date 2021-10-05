package project.model.set;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import project.model.Coordinate;
import project.model.PlacedPiece;
import project.model.TangramPiece;
import project.model.TangramSet;

/**
 * The game is a 14-piece dissection puzzle forming a square, attributed to Archimedes
 * 
 * https://en.wikipedia.org/wiki/Ostomachion
 */
public class Ostomachion extends AbstractFactory {
	/** Name for this set. */
	public static final String name = "Ostomachion";
	
	// define 10 coordinates used for all pieces, defined within unit square
	final static Coordinate c0  = new Coordinate(0,    0);
	final static Coordinate c1  = new Coordinate(0.5,  0);
	final static Coordinate c4  = new Coordinate(1,    0);
	final static Coordinate c2  = new Coordinate(1,    0.333);
	final static Coordinate c3  = new Coordinate(0.167, 0.167);
	
	final static Coordinate c5  = new Coordinate(1,    0.5);
	
	final static Coordinate c7  = new Coordinate(0.25, 0.5);
	final static Coordinate c8  = new Coordinate(0.75, 0.5);
	final static Coordinate c9  = new Coordinate(0.5,  0.5);
	final static Coordinate c10 = new Coordinate(1,    1);
	final static Coordinate c11 = new Coordinate(0,    1);
	final static Coordinate c12 = new Coordinate(0.5,  1);
	
	final static Coordinate c6  = new Coordinate(0.167, 0.667);
	final static Coordinate c13 = new Coordinate(0.667, 0.667);
	final static Coordinate c14 = new Coordinate(0.25, 1.0);
	final static Coordinate c15 = new Coordinate(0.34, 0.34);
	
	/** Ostomachion set. */
	public TangramSet produce() {
		TangramSet set = new TangramSet();
		set.add(new TangramPiece(1, new Coordinate[]  { c0, c1, c15 }));
		set.add(new TangramPiece(2, new Coordinate[]  { c1, c4, c2, c8 }));
		set.add(new TangramPiece(3, new Coordinate[]  { c2, c5, c8 }));
		set.add(new TangramPiece(4, new Coordinate[]  { c8, c5, c10 }));
		set.add(new TangramPiece(5, new Coordinate[]  { c8, c10, c13 }));
		set.add(new TangramPiece(6, new Coordinate[]  { c13, c10, c12 }));
		set.add(new TangramPiece(7, new Coordinate[]  { c9, c13, c12 }));
		set.add(new TangramPiece(8, new Coordinate[]  { c1, c8, c13, c9 }));
		set.add(new TangramPiece(9, new Coordinate[]  { c1, c9, c15 }));
		set.add(new TangramPiece(10, new Coordinate[] { c15, c9, c12, c14, c7 }));
		set.add(new TangramPiece(11, new Coordinate[] { c7, c14, c6 }));
		set.add(new TangramPiece(12, new Coordinate[] { c6, c14, c11 }));
		set.add(new TangramPiece(13, new Coordinate[] { c3, c15, c11 }));
		set.add(new TangramPiece(14, new Coordinate[] { c0, c3, c11 }));
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
