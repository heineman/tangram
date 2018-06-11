package project.model;

import java.util.Optional;

/**
 * There is always a working Tangram set.
 */
public class Model {

	/** Available Tangram set. */
	TangramSet set;

	/** Active puzzle. */
	Puzzle puzzle;

	/** Returns a reasonable default. */
	public static Model defaultModel() {
		Model m = new Model();
		
		TangramSet set = StandardSet.produce();
		m.setTangramSet(set);
		
		// use original 'square configuration' as a reasonable default puzzle to start with 
		m.setPuzzle(new Puzzle(StandardSet.solution(Puzzle.Scale)));
		return m;
	}
	
	/** Declare which set to use. */
	public void setTangramSet(TangramSet set) {
		this.set = set;
	}

	/** Retrieve the Tangram set. */
	public TangramSet getTangramSet() {
		return set;
	}
	
	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	/** Return Puzzle as optional, since user must select it. */
	public Optional<Puzzle> getPuzzle() {
		if (puzzle == null) {
			return Optional.empty();
		}
			
		return Optional.of(puzzle);
	}
}
