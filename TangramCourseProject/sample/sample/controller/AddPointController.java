package sample.controller;

import java.awt.Point;
import java.awt.Polygon;
import java.util.Optional;

import sample.model.Model;
import sample.view.Application;

/**
 * <b>Use case: Add Point</b><p>
 * 
 * Either creates a new polygon or adds a point to the selected polygon.
 */
public class AddPointController {
	final Model model;
	final Application app;
	
	public AddPointController(Application app, Model m) {
		this.app = app;
		this.model = m;
	}
	
	/** User requests to add this point to the current polygon (or create new one if needed). */
	public void addPoint(Point p) {
		Optional<Polygon> selected = model.getSelected();
		if (!selected.isPresent()) {
			// create the polygon
			Polygon poly = new Polygon();
			poly.addPoint(p.x, p.y);
			
			model.addPolygon(poly);
			model.makeCurrent(poly);
		} else {
			// add point
			selected.get().addPoint(p.x, p.y);
		}
		
		// refresh display
		app.repaint();
	}
}
