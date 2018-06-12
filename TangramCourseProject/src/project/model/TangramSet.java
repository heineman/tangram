package project.model;

import java.util.*;

/**
 * A Tangram set is a collection of TangramPieces.
 * 
 * The representation is in Normal Form, which means all pieces are initially identified
 * by their location within the [0, 1] unit square.
 */
public class TangramSet implements Iterable<TangramPiece> {

	/** Pieces in arbitrary order. */
	List<TangramPiece> pieces = new ArrayList<>();
	
	/** Add given piece to the set. */
	public void add(TangramPiece piece) {
		pieces.add(piece);
	}
	
	/** Convenient iteration over pieces. */
	public Iterator<TangramPiece> iterator() {
		return pieces.iterator();
	}

	/** Return number of pieces in set. */
	public int size() {
		return pieces.size();
	}
	
	/** Find piece by id, if it exists. */
	public Optional<TangramPiece> find(int id) {
		for (TangramPiece piece : pieces) {
			if (piece.id == id) {
				return Optional.of(piece);
			}
		}
		
		return Optional.empty();
	}
}

