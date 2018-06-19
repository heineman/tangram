package project.controller;

import project.GUITestCase;
import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;

public class TestUndoController extends GUITestCase {
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
		Puzzle puzzle = model.getPuzzle().get();
		
		// none placed yet
		assertFalse (puzzle.pieces().hasNext());
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		new PlacePieceController(app, model).place(piece);
		
		// one placed
		PlacedPiece pp = puzzle.pieces().next();
		assertEquals (piece, pp.getPiece());
		
		assertTrue (new UndoController(app, model).process());
		assertFalse (puzzle.pieces().hasNext());
		
		assertTrue (new RedoController(app, model).process());
		assertTrue (puzzle.pieces().hasNext());
		
		// move piece around 
		PuzzleController pc = new PuzzleController(app, model);
		
		// validate press cause the active piece 
		pc.mousePressed(createPressed(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0]));
		assertTrue (puzzle.getActive().isPresent());
		
		// drag downwards a bit
		pc.mouseDragged(createDragged(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0] + 20));
		assertTrue (puzzle.getActive().isPresent());
		
		// release it
		pc.mouseReleased(createReleased(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0] + 20));
		
		// release it
		assertFalse (puzzle.getActive().isPresent());
		
		// can do twice
		assertTrue (new UndoController(app, model).process());
		assertTrue (new UndoController(app, model).process());
		
		// but not three times
		assertFalse (new UndoController(app, model).process());
	}
	
	public void testReturnUndo() {
		Puzzle puzzle = model.getPuzzle().get();
		
		// none placed yet
		assertFalse (puzzle.pieces().hasNext());
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		new PlacePieceController(app, model).place(piece);
		
		// one placed
		PlacedPiece pp = puzzle.pieces().next();
		assertEquals (piece, pp.getPiece());
		
		// remove piece  
		PuzzleController pc = new PuzzleController(app, model);
		
		// validate press cause the active piece 
		pc.mousePressed(createPressed(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0]));
		assertTrue (puzzle.getActive().isPresent());
		
		// Exit
		pc.mouseExited(createDragged(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0] + 20));
		
		assertFalse (puzzle.getActive().isPresent());
		
		// can do twice
		assertTrue (new UndoController(app, model).process());
		assertTrue (new UndoController(app, model).process());
		
		// but not three times
		assertFalse (new UndoController(app, model).process());
	}
	
}
