package project.controller;

import java.io.File;
import java.io.IOException;

import project.model.Model;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;
import junit.framework.TestCase;

// Also tests SelectPuzzleController since they are tied together
public class TestStorePuzzleController extends TestCase {
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
	
	public void testStart() {
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		assertTrue(new PlacePieceController(app, model).place(piece));
		
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("sample.", StorePuzzleController.Suffix);
		} catch (IOException ioe) {
			// can't even create temp file? Skip test entirely...
		}
		
		if (tmpFile != null) {
			tmpFile.deleteOnExit();
			assertTrue(new StorePuzzleController(app, model).store(tmpFile));
			
			new CreatePuzzleController(app, model).create();
			
			// no pieces in play
			assertFalse (model.getPuzzle().get().pieces().hasNext());
			
			assertTrue(new SelectPuzzleController(app, model).load(tmpFile));
			
			// one piece in play, with same id as selected earlier
			assertTrue (model.getPuzzle().get().pieces().hasNext());
			assertEquals (piece.id, model.getPuzzle().get().pieces().next().getPiece().id);
		}
	} 
	
	public void testNewSolution() {
		// start a new puzzle
		new CreatePuzzleController(app, model).create();
		
		// grab the first piece from the set and place it
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		assertTrue(new PlacePieceController(app, model).place(piece));
		
		
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("sample.", StorePuzzleController.Suffix);
		} catch (IOException ioe) {
			// can't even create temp file? Skip test entirely...
		}
		
		if (tmpFile != null) {
			tmpFile.deleteOnExit();
			
			// in storing, creates new puzzle with solution of just the single piece.
			assertTrue(new StorePuzzleController(app, model).store(tmpFile));
			
			assertTrue(new SelectPuzzleController(app, model).load(tmpFile));
			
			// no pieces in play, but solution has single piece.
			assertFalse (model.getPuzzle().get().pieces().hasNext());
			assertTrue (model.getPuzzle().get().solution().hasNext());
			
			assertEquals (piece.id, model.getPuzzle().get().solution().next().getPiece().id);
		}
	}
}
