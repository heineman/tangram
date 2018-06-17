package project.controller;

import project.model.Model;
import project.model.TangramPiece;
import project.view.TangramApplication;
import junit.framework.TestCase;

// Also tests SelectPuzzleController since they are tied together
public class TestResetPuzzleController extends TestCase {
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
		
		//  pieces in play
		assertTrue (model.getPuzzle().get().pieces().hasNext());
		
		new ResetPuzzleController(app, model).reset();
			
		assertFalse (model.getPuzzle().get().pieces().hasNext());
	}
}
