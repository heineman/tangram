package project.controller;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;
import junit.framework.TestCase;

public class TestCreatePuzzleController extends TestCase {

	TangramApplication app;
	Model model;
	
	@Override
	protected void setUp() {
		model = Model.defaultModel();
		app = new TangramApplication (model);
		app.setVisible(true);
	}
	
	@Override 
	protected void tearDown() {
		app.dispose();
	}
	
	public void testFromScratch() {
		Puzzle puzzle = model.getPuzzle().get();
		
		// default has a solution
		assertTrue(puzzle.solution().hasNext());
		
		new CreatePuzzleController(app, model).create();
		
		// Now gone.
		Puzzle newPuzzle = model.getPuzzle().get();
		assertFalse(newPuzzle.solution().hasNext());
	}	
	
	public void testAddedPieces() {
		Puzzle puzzle = model.getPuzzle().get();
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		new PlacePieceController(app, model).place(piece);
		
		// should eliminate even added pieces.
		new CreatePuzzleController(app, model).create();
		
		// Now gone.
		Puzzle newPuzzle = model.getPuzzle().get();
		assertFalse(newPuzzle.solution().hasNext());
	}	
}

