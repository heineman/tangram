package project.model;

import java.awt.Point;
import java.util.*;

/**
 * A puzzle starts with a predefined set of fixed PlacedPieces from which a silhouette is drawn. These
 * fixed pieces do not move.
 * 
 * A player attempts to solve a puzzle by selecting pieces to add (or remove) dynamically. These pieces
 * can be moved around based on the user's directives.
 * 
 * The Scale determines the size of each piece, relative to a unit square of this size.
 */
public class Puzzle {

	public static final int Scale = 512;

	/** Dynamically added pieces. */
	ArrayList<PlacedPiece> piecesInPlay = new ArrayList<>();

	/** Solution. */
	ArrayList<PlacedPiece> solution = new ArrayList<>();

	/** When a user interacts with a piece, it becomes active. */
	PlacedPiece active;

	/** Form solution from initial TangramSet configuration. */
	public Puzzle(TangramSet set) {
		for (TangramPiece piece : set) {
			PlacedPiece p = new PlacedPiece(piece, Scale, new Point (0, 0));
			solution.add(p);
		}
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
}
