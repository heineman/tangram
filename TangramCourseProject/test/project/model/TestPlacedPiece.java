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
		PlacedPiece piece = new PlacedPiece(tp, PiecesView.squareSize, PlacedPiece.NO_ROTATION, new Point(0,0));
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
	
	
	public void testRotate() {
		TangramPiece tp = new TangramPiece(1, new Coordinate[] { c0, c1, c4 });
		PlacedPiece piece = new PlacedPiece(tp, PiecesView.squareSize, PlacedPiece.NO_ROTATION, new Point(0,0));
		
		piece.rotate(90);
		assertEquals (90, piece.getRotation());
		
		piece.rotate(270);
		assertEquals (0, piece.getRotation());   // back to start
		
		piece.rotate(900);
		assertEquals (180, piece.getRotation()); // should be 180, since 900 = 180 (mod 360)
		
		piece.rotate(-540);						 // should be -180, for similar reasons
		assertEquals (0, piece.getRotation());   // back to start
		
		piece.rotate(-45);
		assertEquals (315, piece.getRotation()); // make sure neg rotation works. All angles between 0 and 360
	}
	
}
