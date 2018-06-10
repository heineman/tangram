package project.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Individual Tangram piece, based on (0,0) - (1,1) square.
 * 
 * Anchor point is first one.
 */
public class TangramPiece implements Iterable<Coordinate> {
	/** store points. Since coordinates are double, can't use native java.awt.Polygon */ 
	ArrayList<Coordinate> polygon = new ArrayList<>();

	/** Center point. */
	public final Coordinate center;

	/** Each piece is given a unique identifier. */
	public final int id;
	
	/** 
	 * Construct Tangram piece based on array of border coordinates assumed to be in order.
	 * Polygon is closed from last point to first point. 
	 * 
	 * Note that the points[] array contains all unique points. We close the polygon here by adding final point to polygon.
	 */
	public TangramPiece(int id, Coordinate[] points) {
		if (points.length < 3) { throw new IllegalArgumentException ("TangramPiece must contain at least 3 points"); }
		this.id = id;
		
		Point2D.Double p = new Point2D.Double(0, 0);   // need point with double precision
		for (Coordinate c : points) {
			polygon.add(c);
			p.x += c.x;
			p.y += c.y;
		}
		
		// close the loop
		polygon.add(points[0]);
		this.center = new Coordinate(p.x/points.length, p.y/points.length);
	}

	@Override
	/** Return points in the Tangram Piece. Note that the first point will be repeated at the end. */
	public Iterator<Coordinate> iterator() {
		return polygon.iterator();
	}	
	
	/** Return number of points in polygon. */
	public int size() {
		return polygon.size();
	}
}
