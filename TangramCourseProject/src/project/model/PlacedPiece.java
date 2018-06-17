package project.model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

/** 
 * Records the placement of a Tangram piece on the board.
 * 
 * The computed polygon is derived from all elements.
 * 
 * This class acts as a bridge between the abstract realm (a tangram piece) and the
 * physical realm (pixels in a GUI).
 */
public class PlacedPiece implements java.io.Serializable {
	
	/** Piece placed, its offset, and desired unit scale. */
	TangramPiece piece;
	Point offset;
	int rotation;
	int scale;
	boolean flipped;
	
	public static final int NO_ROTATION = 0;
	public static final boolean NO_FLIP = false;
	
	/** Polygon is computed on demand and cached. */
	Polygon polygon;
	
	public PlacedPiece (TangramPiece tp, boolean flipped, int scale, int rotation, Point offset) {
		this.piece = tp;
		this.flipped = flipped;
		this.offset = offset;
		this.scale = scale;
		this.rotation = rotation;
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
	public int getRotation() { return rotation; }
	public boolean isFlipped() { return flipped; }
	
	/** Rotate in increments no greater than +/- 360 to avoid excessive degrees.  */
	public void rotate(int r) {
		if (r < -360) {
			r = (r % -360);
		} else if (r > 360) {
			r = (r % 360);
		}
		
		rotation = this.rotation + r;
		if (rotation < 0) {
			rotation += 360; 
		} else if (rotation >= 360) {
			rotation -= 360;
		}
		
		polygon = null;
	}
	
	/** Translate in plane. */
	public void translate(int x, int y) {
		offset.x = offset.x + x;
		offset.y = offset.y + y;
		polygon = null;
	}
	
	
	/** Flip. */
	public void flip () {
		flipped = !flipped;
		polygon = null;
	}
	
	/** Helper method to return polygon for Tangram piece anchored at (x,y). */
	Polygon initialPolygon() {
		int[] xpoints = new int[piece.size()];
		int[] ypoints = new int[piece.size()];
		double center_y = getPiece().center.y;
		
		// convert coordinate sequence into (x,y) points. If flipped, 
		// vertically swap around center.
		int idx = 0;
		for (Coordinate c : piece) {
			xpoints[idx] = (int) (offset.x + scale*c.x);
			if (flipped) {
				ypoints[idx] = (int) (offset.y + scale*(2*center_y-c.y));
			} else {
				ypoints[idx] = (int) (offset.y + scale*c.y);
			}
			idx++;
		}

		return new Polygon(xpoints, ypoints, piece.size());
	}
	
	/** 
	 * Helper method to return polygon for Tangram piece anchored at (x,y) with given rotation.
	 * This rotation logic is complicated stuff, but it can be encapsulated cleanly in this method.
	 */
	Polygon computePolygon() {
		Polygon poly = initialPolygon();
		
		// https://docs.oracle.com/javase/tutorial/2d/advanced/transforming.html
		PathIterator pi = poly.getPathIterator(AffineTransform.getRotateInstance(Math.toRadians(rotation), 
				(offset.x+scale*piece.center.x), (offset.y+scale*piece.center.y)));

		float coords[] = new float[6];
		int xpoints[] = new int[piece.size()];
		int ypoints[] = new int[piece.size()];

		int idx = 0;
		while (!pi.isDone()) {
			int type = pi.currentSegment(coords);
			if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO) {
				xpoints[idx] = (int) coords[0];
				ypoints[idx] = (int) coords[1];
				idx++;
			}
			pi.next();
		}

		poly = new Polygon(xpoints, ypoints, piece.size());
		return poly;
	}
}
