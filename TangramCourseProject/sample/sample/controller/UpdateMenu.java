package sample.controller;

import sample.model.Model;
import sample.view.Application;

public class UpdateMenu {

	/** Update the menus based on the state of the model. */
	public static void updateMenu (Application app, Model model) {
		if (model.size() == 0) {
			app.getResetMenuItem().setEnabled(false);
			app.getUndoMenuItem().setEnabled(false);
			app.getUndoMenuItem().setText("Remove");  
		} else {
			app.getResetMenuItem().setEnabled(true);
			app.getUndoMenuItem().setEnabled(true);
			
			if (model.getSelected().isPresent()) {
				app.getUndoMenuItem().setText("Remove Last Point");
			} else {
				app.getUndoMenuItem().setText("Remove Last Polygon");
			}
			
		}
	}
}
