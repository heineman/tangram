package project.controller;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.view.TangramApplication;

public class FlipActivePieceController {

	TangramApplication app;
	Model model;

	public FlipActivePieceController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	public void flip() {
		if (model.getPuzzle().isPresent()) {
			Puzzle puzzle = model.getPuzzle().get();
			
			if (puzzle.getActive().isPresent()) {
				PlacedPiece piece = puzzle.getActive().get();
				piece.flip();
				
				app.getPuzzleView().refresh();
			}
		}
	}
}
