package project.model;

import java.awt.Point;

import project.view.PiecesView;
import junit.framework.TestCase;

public class TestPuzzle extends TestCase {
	final static Coordinate c0 = new Coordinate(0,    0);
	final static Coordinate c1 = new Coordinate(1,    0);
	final static Coordinate c4 = new Coordinate(0.5,  0.5);
	
	public void testStart() {
		Puzzle p = new Puzzle(StandardSet.solution(Puzzle.Scale));
		
		// No available piece yet, though there is a solution
		assertFalse(p.pieces().hasNext());
		assertTrue(p.solution().hasNext());
		
		TangramPiece tp = new TangramPiece(1, new Coordinate[] { c0, c1, c4 });
		PlacedPiece piece = new PlacedPiece(tp, PlacedPiece.NO_FLIP, PiecesView.squareSize, PlacedPiece.NO_ROTATION, new Point(0,0));

		assertFalse(p.pieces().hasNext());
		p.add(piece);
		assertTrue(p.pieces().hasNext());
		p.remove(piece);
		assertFalse(p.pieces().hasNext());
	}
	
	public void testActive() {
		Puzzle p = new Puzzle(StandardSet.solution(Puzzle.Scale));
		
		TangramPiece tp = new TangramPiece(1, new Coordinate[] { c0, c1, c4 });
		PlacedPiece piece = new PlacedPiece(tp, PlacedPiece.NO_FLIP, PiecesView.squareSize, PlacedPiece.NO_ROTATION, new Point(0,0));
		
		assertFalse (p.getActive().isPresent());
		p.add(piece);
		assertFalse (p.getActive().isPresent());
		p.setActive(piece);
		assertTrue (p.getActive().isPresent());
	}
	
	public void testContains() {
		Puzzle p = new Puzzle(StandardSet.solution(Puzzle.Scale));
		
		TangramPiece tp = new TangramPiece(1, new Coordinate[] { c0, c1, c4 });
		PlacedPiece piece = new PlacedPiece(tp, PlacedPiece.NO_FLIP, PiecesView.squareSize, PlacedPiece.NO_ROTATION, new Point(0,0));
		
		assertFalse (p.contains(1));
		p.add(piece);
		assertTrue (p.contains(1));
	}
}
