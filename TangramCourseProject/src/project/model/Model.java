package project.model;

import java.util.Optional;

import project.model.set.AbstractFactory;
import project.model.set.TraditionalTangram;

/**
 * There is always a working Tangram set.
 */
public class Model {

	/** Available Factory that generates tangram set. */
	AbstractFactory factory;

	/** Tangram set in use. */
	TangramSet set;
	
	/** (Optional) Active puzzle. */
	Puzzle puzzle;

	/** Returns a reasonable default. */
	public static Model defaultModel() {
		Model m = new Model();
		
		AbstractFactory fact = new TraditionalTangram();
		m.setFactory(fact);
		
		// use original 'square configuration' as a reasonable default puzzle to start with 
		m.setPuzzle(new Puzzle(fact, fact.solution(Puzzle.Scale)));
		return m;
	}
	
	/** Declare which set to use. */
	public void setFactory(AbstractFactory factory) {
		this.factory = factory;
		this.set = factory.produce();
	}
	
	/** Return the factory for the model. */
	public AbstractFactory getFactory() {
		return factory;
	}

	/** Retrieve the Tangram set of puzzles. */
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
