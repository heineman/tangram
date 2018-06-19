package project.model;

import java.util.Optional;
import java.util.Stack;

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

	/** Moves that can be undone. */
	Stack<Action> moveStack = new Stack<Action>();
	
	/** Moves that can be (re)-executed. */
	Stack<Action> redoStack = new Stack<Action>();
	
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
	
	/**
	 * Record the action which can be undone in the future.
	 * 
	 * Doing so invalidates any "undone" moves that might be currently on the redoStack.
	 */
	public void recordAction(Action action) {
		moveStack.add(action);
		redoStack.clear();
	}
	
	/** 
	 * Add action as a future move to be redone.
	 * 
	 * @param move
	 */
	public void recordRedoableAction(Action action) {
		redoStack.push(action);
	}

	/**
	 * If any actions have been undone, then they can be redone.
	 */
	public Optional<Action> removeRedoAction() {
		if (redoStack.isEmpty()) { return Optional.empty(); }
		return Optional.of(redoStack.pop());
	}
	
	/**
	 * Redo Controller has executed an action that had previously been undone.
	 * This can go onto the move stack so it can be undone in future
	 * @param m
	 */
	public void recordRedoneAction (Action action) {
		moveStack.push(action);
	}
	
	/**
	 * Prepare for undo by getting last move.
	 */
	public Optional<Action> removeLastAction() {
		if (moveStack.isEmpty()) { return Optional.empty(); }
		return Optional.of(moveStack.pop());
	}
}
