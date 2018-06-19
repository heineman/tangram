package project.controller;

import java.util.Optional;

import javax.swing.JOptionPane;

import project.model.Model;
import project.model.Puzzle;
import project.model.set.*;
import project.view.TangramApplication;

/**
 * Allow user to choose a set to use for creating puzzles. 
 */
public class SelectSetController {
	TangramApplication app;
	Model model;

	public SelectSetController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	public boolean choose() {
		String choice = (String) JOptionPane.showInputDialog(null, "Choose an available set ...",
				"Select a Tangram Set To Use",
				JOptionPane.QUESTION_MESSAGE, 
				null,     // use default icon
				AbstractFactory.knownSets,         // available Tangram sets.
				AbstractFactory.knownSets[0]);     // First entry is the default

		if (choice == null) {
			return false;
		}
		
		return tryChoose(choice);
	}
	
	/** Helper method that avoids GUI interaction. */
	boolean tryChoose (String choice) {
		
		Optional<AbstractFactory> factory = AbstractFactory.choose(choice);
		if (!factory.isPresent()) {
			return false;
		}

		// reset the current puzzle (if one exists) to the initial one, after updating the factory
		model.setFactory(factory.get());
		model.setPuzzle(new Puzzle(factory.get(), factory.get().solution(Puzzle.Scale)));
		
		app.getPuzzleView().refresh();         // has changed state
		app.getPiecesView().computePieces();   // might be new
		app.getPiecesView().refresh();         // has changed state
		return true;
	}
}
