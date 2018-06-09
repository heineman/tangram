package project.view;

import java.awt.Point;

import junit.framework.TestCase;
import project.model.Model;
import project.model.PlacedPiece;
import project.view.PiecesView;
import project.view.TangramApplication;

public class TestPiecesView extends TestCase {
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
	
	public void testCanFindPiece() {
		PiecesView pview = app.getPiecesView();
		
		// top-left point can be found
		PlacedPiece pp = pview.pieces.get(0);
		Point point = new Point (pp.getPolygon().xpoints[0], pp.getPolygon().ypoints[0]);
		assertTrue (pview.find(point).isPresent());
		
		// but off-screen can't
		assertFalse (pview.find(new Point (-20, -20)).isPresent());
	}
}
