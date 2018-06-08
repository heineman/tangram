package project.model;

import java.awt.Point;
import java.awt.Polygon;

/** 
 * Records the placement of a Tangram piece on the board.
 * 
 * The computed polygon is derived from all elements.
 * 
 * This class acts as a bridge between the abstract realm (a tangram piece) and the
 * physical realm (pixels in a GUI).
 */
public class PlacedPiece {
	
	/** Piece placed, its offset, and desired unit scale. */
	TangramPiece piece;
	Point offset;
	int scale;
	
	/** Polygon is computed on demand and cached. */
	Polygon polygon;
	
	public PlacedPiece (TangramPiece tp, int scale, Point offset) {
		this.piece = tp;
		this.offset = offset;
		this.scale = scale;
	}
	
	public boolean contains (Point p) {
		return getPolygon().contains(p);
	}
	
	/** Return polygon (create if necessary) and return it. */
	public Polygon getPolygon() {
		if (polygon == null) {
			polygon = computePolygon();
		}
		return polygon;
	}
	
	public TangramPiece getPiece() {
		return piece;
	}
	
	public Point getTranslation() { return offset; }
	
	/** Translate in plane. */
	public void translate(int x, int y) {
		offset.x = offset.x + x;
		offset.y = offset.y + y;
		polygon = null;
	}
	
	/** Helper method to return polygon for Tangram piece anchored at (offset.x, offset.y). */
	Polygon computePolygon() {
		int[] xpoints = new int[piece.size()];
		int[] ypoints = new int[piece.size()];
		
		// convert coordinate sequence into (x,y) points. 
		int idx = 0;
		for (Coordinate c : piece) {
			xpoints[idx] = (int) (offset.x + scale*c.x);
			ypoints[idx] = (int) (offset.y + scale*c.y);
			idx++;
		}

		return new Polygon(xpoints, ypoints, piece.size());
	}
}
