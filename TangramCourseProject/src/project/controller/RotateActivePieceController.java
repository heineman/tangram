package project.controller;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.view.TangramApplication;

/**
 * Rotate active piece by +/- 5 degrees based on user interaction.
 * 
 * Cannot be individually undone, until mouse is released.
 */
public class RotateActivePieceController {

	TangramApplication app;
	Model model;

	public RotateActivePieceController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	public void rotate(int direction) {
		if (model.getPuzzle().isPresent()) {
			Puzzle puzzle = model.getPuzzle().get();
			
			if (puzzle.getActive().isPresent()) {
				PlacedPiece piece = puzzle.getActive().get();
				if (direction < 0) {
					piece.rotate(-5);
				} else {
					piece.rotate(+5);
				}
				
				app.getPuzzleView().refresh();
			}
		}
	}
}
