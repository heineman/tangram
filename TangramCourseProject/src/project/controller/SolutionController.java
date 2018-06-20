package project.controller;

import java.util.Optional;

import project.model.Model;
import project.model.Puzzle;
import project.model.UpdateListener;
import project.view.TangramApplication;

/** 
 * Singleton object, used to determine whether puzzle has been solved. 
 *
 * Variation of Singleton that shows difficulty when the constructed object
 * needs parameters.
 */
public class SolutionController implements UpdateListener {

	final TangramApplication app;
	final Model model;
	
	static SolutionController instance;

	/** None outside class can access. */
	private SolutionController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}

	/** Return already constructed instance. */
	public static SolutionController getInstance() {
		return instance;
	}

	/** Request the construction of instance with parameters. */
	public static SolutionController instanceOf(TangramApplication app, Model model) {
		instance = new SolutionController(app, model);
		return instance; 
	}

	@Override
	public void update() {
		Optional<Puzzle> op = model.getPuzzle();
		if (op.isPresent()) {
			Puzzle puzzle = op.get();
			puzzle.checkSolved();
		}
	}
}
