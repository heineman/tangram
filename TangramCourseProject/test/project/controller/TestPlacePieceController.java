package project.controller;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;
import junit.framework.TestCase;

public class TestPlacePieceController extends TestCase {

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
		
		// none placed yet
		assertFalse (puzzle.pieces().hasNext());
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		new PlacePieceController(app, model).place(piece);
		
		// one placed
		PlacedPiece pp = puzzle.pieces().next();
		assertEquals (piece, pp.getPiece());
	}
	
	// ensure no duplicates
	public void testDuplicate() {
		Puzzle puzzle = model.getPuzzle().get();
		
		// none placed yet
		assertFalse (puzzle.pieces().hasNext());
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		assertTrue (new PlacePieceController(app, model).place(piece));
		
		// one placed
		PlacedPiece pp = puzzle.pieces().next();
		assertEquals (piece, pp.getPiece());
		
		// not going to do anything...
		assertFalse (new PlacePieceController(app, model).place(piece));
	}
}
