package project.model;

/**
 * Based on unit square, this reflects core coordinate for a border point of a Tangram piece.
 */
public class Coordinate {
	public final double x;
	public final double y;
	
	/** Construct a coordinate from (x,y) values. */
	public Coordinate (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}
