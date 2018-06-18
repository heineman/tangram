package project.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import project.controller.PlacePieceController;
import project.view.TangramApplication;
import junit.framework.TestCase;

public class TestParser extends TestCase {
	
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
		TangramSet set = StandardSet.produce();
		Puzzle puzzle = new Puzzle(StandardSet.solution(Puzzle.Scale));
		model.setPuzzle(puzzle);
		
		// grab the first piece from the set.
		TangramPiece piece = set.iterator().next();
		new PlacePieceController(app, model).place(piece);
		
		// now model has a single piece placed.
		
		// create encoding.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		Parser.write(ps, puzzle);
		ps.close();
		byte[] bytes = baos.toByteArray();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Optional<Puzzle> op = Parser.tryParse(set, bais);
		assertTrue (op.isPresent());
		
		Puzzle newPuzzle = op.get();
		assertTrue(newPuzzle.pieces().hasNext());  // has a piece in play.
		
	}
}
