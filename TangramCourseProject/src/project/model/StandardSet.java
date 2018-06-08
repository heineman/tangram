package project.model;

public class StandardSet {
	// define 10 coordinates used for all pieces, defined within unit square
	final static Coordinate c0 = new Coordinate(0,    0);
	final static Coordinate c1 = new Coordinate(1,    0);
	final static Coordinate c2 = new Coordinate(0.25, 0.25);
	final static Coordinate c3 = new Coordinate(0,    0.5);
	final static Coordinate c4 = new Coordinate(0.5,  0.5);
	final static Coordinate c5 = new Coordinate(0.25 ,0.75);
	final static Coordinate c6 = new Coordinate(0.75, 0.75);
	final static Coordinate c7 = new Coordinate(0,    1);
	final static Coordinate c8 = new Coordinate(0.5,  1);
	final static Coordinate c9 = new Coordinate(1,    1);
		
	/** Standard Tangram set. */
	public static TangramSet produce() {
		TangramSet set = new TangramSet();
		set.add(new TangramPiece(new Coordinate[] { c0, c1, c4 }));
		set.add(new TangramPiece(new Coordinate[] { c0, c2, c5, c3}));
		set.add(new TangramPiece(new Coordinate[] { c2, c4, c5 }));
		set.add(new TangramPiece(new Coordinate[] { c1, c9, c4 }));
		set.add(new TangramPiece(new Coordinate[] { c3, c8, c7 }));
		set.add(new TangramPiece(new Coordinate[] { c4, c6, c8, c5 }));
		set.add(new TangramPiece(new Coordinate[] { c6, c9, c8 }));
		return set;
	}
}
