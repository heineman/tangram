package project.controller;

import java.util.*;

import project.model.*;
import project.view.TangramApplication;

/**
 * Start with empty Solution and allow pieces to be moved onto a new puzzle.
 * 
 * Puzzle solution is created on the board, and only becomes permanent by saving.
 */
public class CreatePuzzleController {
	TangramApplication app;
	Model model;
	
	public static final String CannotCreate = "Unable to create puzzle for pre-loaded set.";

	public CreatePuzzleController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}

	public void create() {
		Puzzle puzzle = new Puzzle(model.getFactory(), new ArrayList<PlacedPiece>().iterator());
		
		model.setPuzzle(puzzle);
		
		app.getPiecesView().refresh();
		app.getPuzzleView().refresh();
	}
}
