package project.controller;

import java.util.Iterator;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.view.TangramApplication;

public class ResetPuzzleController {

	TangramApplication app;
	Model model;

	public ResetPuzzleController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	public void reset() {
		if (!model.getPuzzle().isPresent()) { return; }
		
		// Grab each placed piece and remove using iterator
		Puzzle puzzle = model.getPuzzle().get();
		for (Iterator<PlacedPiece> it = puzzle.pieces(); it.hasNext(); ) {
			it.next();
			it.remove();
		}
		
		app.getPiecesView().refresh();
		app.getPuzzleView().refresh();
	}
}
