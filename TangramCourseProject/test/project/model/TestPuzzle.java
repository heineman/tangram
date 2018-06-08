package project.model;

import java.awt.Point;

import project.view.PiecesView;
import junit.framework.TestCase;

public class TestPuzzle extends TestCase {
	final static Coordinate c0 = new Coordinate(0,    0);
	final static Coordinate c1 = new Coordinate(1,    0);
	final static Coordinate c4 = new Coordinate(0.5,  0.5);
	
	public void testStart() {
		TangramSet set = StandardSet.produce();
		Puzzle p = new Puzzle(set);
		
		// No available piece yet, though there is a solution
		assertFalse(p.pieces().hasNext());
		assertTrue(p.solution().hasNext());
		
		TangramPiece tp = new TangramPiece(new Coordinate[] { c0, c1, c4 });
		PlacedPiece piece = new PlacedPiece(tp, PiecesView.squareSize, new Point(0,0));

		assertFalse(p.pieces().hasNext());
		p.add(piece);
		assertTrue(p.pieces().hasNext());
		p.remove(piece);
		assertFalse(p.pieces().hasNext());
	}
}
