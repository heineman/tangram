package project.controller;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;
import junit.framework.TestCase;

public class TestRotateController extends TestCase {

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
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		new PlacePieceController(app, model).place(piece);
		
		// Place first and make active
		PlacedPiece pp = puzzle.pieces().next();
		puzzle.setActive(pp);
		assertEquals (pp, puzzle.getActive().get());
		
		// rotate positive
		new RotateActivePieceController(app, model).rotate(1);
		assertEquals (5, pp.getRotation());
		
		// repeat number of times until back to zero.
		new RotateActivePieceController(app, model).rotate(-1);
		assertEquals (0, pp.getRotation());
		
	}
	
}
