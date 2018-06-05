package sample.controller;

import java.awt.Polygon;
import java.util.Iterator;
import java.util.Optional;

import sample.model.Model;
import sample.view.Application;

public class UndoController {
	final Model model;
	final Application app;
		
	public UndoController(Application app, Model m) {
		this.app = app;
		this.model = m;
	}

	public void removeLastPoint() {
		// either remove last point from polygon; or entire last polygon
		Optional<Polygon> selected = model.getSelected();
		if (selected.isPresent()) {
			// remove last point
			Polygon poly = selected.get();
			if (poly.npoints > 0) {
				poly.npoints--;
			}
		} else {
			// remove last polygon
			Iterator<Polygon> it = model.iterator();
			while (it.hasNext()) {
				it.next();
				if (!it.hasNext()) {
					it.remove();
					break;
				}
			}
		}
		
		UpdateMenu.updateMenu(app, model);
		app.repaint();
	}
}
