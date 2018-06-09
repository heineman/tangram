package project.model;

import junit.framework.TestCase;

public class TestTangramPiece extends TestCase {
	final static Coordinate c0 = new Coordinate(0,    0);
	final static Coordinate c1 = new Coordinate(1,    0);
	final static Coordinate c4 = new Coordinate(0.5,  0.5);
	
	// make sure works, and first point is duplicated.
	public void testOne() {
		// ensure one more point, since first point is duplicated as last one.
		TangramPiece tp = new TangramPiece(1, new Coordinate[] { c0, c1, c4 });
		assertEquals (4, tp.size());
		
		// first point is last
		assertEquals (tp.polygon.get(0), tp.polygon.get(tp.size()-1));
	}
	
	// make sure iterator works, and first point is last.
	public void testIterator() {
		TangramPiece tp = new TangramPiece(1, new Coordinate[] { c0, c1, c4 });
		Coordinate first = null;
		Coordinate last = null;
		for (Coordinate c : tp) {
			if (first == null) { first = c; }
			last = c;
		}
		
		assertEquals (first, last);		
	}
	
	// validate errors handled
	public void testFailure() {
		try {
			TangramPiece tp = new TangramPiece(1, new Coordinate[]{ c0 });
			fail ("Must prevent < 3 points");
		} catch (Exception e) {
			
		}
		
		try {
			TangramPiece tp = new TangramPiece(1, new Coordinate[]{ c0, c1 });
			fail ("Must prevent < 3 points");
		} catch (Exception e) {
			
		}
	}
}
