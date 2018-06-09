package project.controller;

import project.GUITestCase;
import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;
import project.view.TangramApplication;

public class TestPuzzleController extends GUITestCase {

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
	
	public void testSimpleSelection() {
		Puzzle puzzle = model.getPuzzle().get();
		
		// none placed yet
		assertFalse (puzzle.pieces().hasNext());
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		new PlacePieceController(app, model).place(piece);
		
		// one placed
		PlacedPiece pp = puzzle.pieces().next();
		assertEquals (piece, pp.getPiece());
		
		// move piece around
		PuzzleController pc = new PuzzleController(app, model);
		
		// validate press cause the active piece 
		pc.mousePressed(createPressed(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0]));
		assertTrue (puzzle.getActive().isPresent());
		
		// drag downwards a bit
		pc.mouseDragged(createDragged(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0] + 20));
		
		// release it
		pc.mouseReleased(createReleased(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0] + 20));
		assertFalse (puzzle.getActive().isPresent());
		
	}
	 
	public void testSimpleExit() {
		Puzzle puzzle = model.getPuzzle().get();
		
		// none placed yet
		assertFalse (puzzle.pieces().hasNext());
		
		// grab the first piece from the set.
		TangramPiece piece = model.getTangramSet().iterator().next();
		
		new PlacePieceController(app, model).place(piece);
		
		// one placed
		PlacedPiece pp = puzzle.pieces().next();
		assertEquals (piece, pp.getPiece());
		
		// move piece around
		PuzzleController pc = new PuzzleController(app, model);
		
		// validate press cause the active piece 
		pc.mousePressed(createPressed(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0]));
		assertTrue (puzzle.getActive().isPresent());
		
		// cause exit
		pc.mouseExited(createExited(app.getPuzzleView(), pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0] + 20));
		
		// release it
		assertFalse (puzzle.getActive().isPresent());
		
	}
}
