package project.controller;

import java.util.Optional;

import project.model.Action;
import project.model.Model;
import project.view.TangramApplication;

public class RedoController {
	TangramApplication app;
	Model model;

	public RedoController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model; 
	}
	
	public boolean process() {
		Optional<Action> op_action = model.removeRedoAction();
		if (!op_action.isPresent()) { return false; }
		
		Action action = op_action.get();
		if (action.execute()) {
			model.recordRedoneAction(action);
		}
		
		app.getPuzzleView().refresh();   // may have changed state
		app.getPiecesView().refresh();   // have changed state
		return true;
	}
}
