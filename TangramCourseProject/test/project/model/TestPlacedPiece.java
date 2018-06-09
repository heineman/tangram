package project.model;

import java.awt.Point;
import java.awt.Polygon;

import project.view.PiecesView;
import project.view.PuzzleView;
import junit.framework.TestCase;

public class TestPlacedPiece extends TestCase {
	// big top triangle in tangram set.
	final static Coordinate c0 = new Coordinate(0,    0);
	final static Coordinate c1 = new Coordinate(1,    0);
	final static Coordinate c4 = new Coordinate(0.5,  0.5);

	public void testStart() {
		TangramPiece tp = new TangramPiece(1, new Coordinate[] { c0, c1, c4 });
		PlacedPiece piece = new PlacedPiece(tp, PiecesView.squareSize, new Point(0,0));
		assertEquals (tp, piece.getPiece());
		
		assertEquals(new Point(0,0), piece.getTranslation());
		
		Polygon p = piece.getPolygon();
		assertEquals (4, p.npoints);
	
		// make sure finds point inside. This is on the border of point, which works.
		assertTrue(piece.contains(new Point (0,0)));
		
		// can translate
		piece.translate(100, 100);
		assertFalse(piece.contains(new Point (0,0)));
	}
	
}
