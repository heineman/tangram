package sample.controller;

import sample.model.Model;
import sample.view.Application;

public class ResetController {
	final Model model;
	final Application app;
	
	public ResetController(Application app, Model m) {
		this.app = app;
		this.model = m;
	}

	public void reset() {
		model.removeAll();

		UpdateMenu.updateMenu(app, model);
		app.repaint();
	}
}
