package project.controller;

import project.model.Model;
import project.model.TangramPiece;
import project.model.set.ChieNoIta;
import project.model.set.TraditionalTangram;
import project.view.TangramApplication;
import junit.framework.TestCase;

// Also tests SelectPuzzleController since they are tied together
public class TestSelectSetController extends TestCase {
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
		assertEquals (TraditionalTangram.name, model.getFactory().name());
		
		assertTrue(new PlacePieceController(app, model).place(piece));
		
		//  pieces in play
		assertTrue (model.getPuzzle().get().pieces().hasNext());
		
		assertTrue(new SelectSetController(app, model).tryChoose(ChieNoIta.name));
			
		assertFalse (model.getPuzzle().get().pieces().hasNext());
		
		assertEquals (ChieNoIta.name, model.getFactory().name());
	}
}
