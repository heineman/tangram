package project.controller;

import junit.framework.TestCase;
import project.Main;
import project.controller.chain.FlipPiece;
import project.controller.chain.ProcessKey;
import project.controller.chain.RotateLeft;
import project.controller.chain.RotateRight;
import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;

public class TestChainOfResponsibility extends TestCase {
	TangramApplication app;
	Model model;
	
	@Override
	protected void setUp() {
		model = Model.defaultModel();
		app = new TangramApplication (model);
		Main.customize(app, model);
		app.setVisible(true);
	}
	
	@Override 
	protected void tearDown() {
		app.dispose();
	}
	
	public void testScratch() {
		Puzzle puzzle = model.getPuzzle().get();
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		new PlacePieceController(app, model).place(piece);
		
		// Place first and make active
		PlacedPiece pp = puzzle.pieces().next();
		puzzle.setActive(pp);
		assertEquals (pp, puzzle.getActive().get());
		
		ProcessKey handler = app.getKeyHandler();
		
		// rotate positive
		handler.processRequest(RotateRight.rotateRight);
		assertEquals (5, pp.getRotation());
		handler.processRequest(RotateLeft.rotateLeft);
		assertEquals (0, pp.getRotation());
		handler.processRequest(FlipPiece.flip);
		assertEquals (true, pp.isFlipped());
	}
	
}
