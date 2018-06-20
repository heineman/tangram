package project.model;

import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.*;

import project.model.set.AbstractFactory;

/**
 * A puzzle starts with a predefined set of fixed PlacedPieces from which a silhouette is drawn. These
 * fixed pieces do not move.
 * 
 * A player attempts to solve a puzzle by selecting pieces to add (or remove) dynamically. These pieces
 * can be moved around based on the user's directives.
 * 
 * The Scale determines the size of each piece, relative to a unit square of this size.
 * 
 * Note that a valid solution might be different from the pieces of the initial solution.
 */
public class Puzzle {

	public static final int Scale = 512;

	/** Dynamically added pieces. */
	ArrayList<PlacedPiece> piecesInPlay = new ArrayList<>();

	/** Solution. */
	ArrayList<PlacedPiece> solution = new ArrayList<>();

	/** When a user interacts with a piece, it becomes active. */
	PlacedPiece active;

	/** Factory used to construct puzzle. */
	AbstractFactory factory;
	
	/** Remembers if solved. */
	boolean solved = false;
	
	/** Form solution from desired placed pieces. */
	public Puzzle(AbstractFactory factory, Iterator<PlacedPiece> pieces) {
		this.factory = factory;
		while (pieces.hasNext()) {
			solution.add(pieces.next());
		}
	}
	
	/** Return factory used to construct puzzle. */
	public AbstractFactory getFactory() {
		return factory;
	}
	
	/** Get the active piece, if it exists or not. */
	public Optional<PlacedPiece> getActive() {
		if (active == null) {
			return Optional.empty();
		}

		return Optional.of(active);
	}

	/** Set the active piece. */
	public void setActive(PlacedPiece active) {
		this.active = active;
	}

	/** Add a new placed piece to the puzzle. */
	public void add(PlacedPiece piece) {
		piecesInPlay.add(piece);
	}

	/** Remove piece from the collection of dynamic pieces. */
	public void remove(PlacedPiece piece) {
		piecesInPlay.remove(piece);
	}

	/** Return the desired solution. */
	public Iterator<PlacedPiece> solution() {
		return solution.iterator();
	}

	/** Return the pieces placed dynamically. */
	public Iterator<PlacedPiece> pieces() {
		return piecesInPlay.iterator();
	}
	
	/** Check if piece with given id is already placed on the board. */
	public boolean contains(int id) {
		for (PlacedPiece p : piecesInPlay) {
			if (p.getPiece().id == id) {
				return true;
			}
		}

		return false;
	}
	
	/** Determine if puzzle is solved. */
	public boolean isSolved() {
		return solved;
	}
	
	/** Validate when done by ensuring all dynamic pieces remove the area from initial pieces. */
	public void checkSolved() {
		// Union all shapes from the solution
		Area solution = null;
		for (Iterator<PlacedPiece> it = solution(); it.hasNext(); ) {
			Area area = new Area(it.next().getPolygon());
			if (solution == null) {
				solution = area;
			} else {
				solution.add(area);
			}
		}

		// necessary safety check when creating a puzzle from scratch
		if (solution == null) { 
			solved = false;
			return; 
		}
		
		int adjust = 5; // fudge factor. Too high and may have false positives.
		for (PlacedPiece p : piecesInPlay) {
			Polygon poly =  p.getPolygon();
			poly = new Polygon(poly.xpoints, poly.ypoints, poly.npoints);
			
			// move adjust +/- in each direction
			poly.translate(-adjust,  0);
			solution.subtract(new Area(poly));
			poly.translate(adjust, -adjust);
			solution.subtract(new Area(poly));
			poly.translate(adjust, adjust);
			solution.subtract(new Area(poly));
			poly.translate(-adjust, adjust);
			solution.subtract(new Area(poly));
		}

		solved = solution.isEmpty();
	}
}
