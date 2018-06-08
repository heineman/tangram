package project.controller;

import java.awt.Point;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;

/** Place piece into a Puzzle, which will eventually be redrawn. */
public class PlacePieceController {

	TangramApplication app;
	Model model;

	public PlacePieceController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	public boolean place(TangramPiece piece) {
		if (!model.getPuzzle().isPresent()) { return false; }
		
		PlacedPiece p = new PlacedPiece(piece, Puzzle.Scale, new Point(0,0));
		model.getPuzzle().get().add(p);
		
		app.getPiecesView().refresh();
		app.getPuzzleView().refresh();

		return true;
	}
}
