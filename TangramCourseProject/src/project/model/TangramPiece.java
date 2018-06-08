package project.model;

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
	
	/** 
	 * Construct Tangram piece based on array of border coordinates assumed to be in order.
	 * Polygon is closed from last point to first point. 
	 * 
	 * Note that the points[] array contains all unique points. We close the polygon here by adding final point to polygon.
	 */
	public TangramPiece(Coordinate[] points) {
		if (points.length < 3) { throw new IllegalArgumentException ("TangramPiece must contain at least 3 points"); }
		
		for (Coordinate c : points) {
			polygon.add(c);
		}
		
		// close the loop
		polygon.add(points[0]);
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
