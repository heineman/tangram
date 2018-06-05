package sample.controller;

import sample.model.Model;
import sample.view.Application;

public class CompletePolygonController {
	final Model model;
	final Application app;
	
	public CompletePolygonController(Application app, Model m) {
		this.app = app;
		this.model = m;
	}
	
	public void complete() {
		model.unselectPolygon();
		
		UpdateMenu.updateMenu(app, model);
		app.repaint();
	}
}
