package project.controller.chain;

import java.awt.event.KeyEvent;

import project.controller.RotateActivePieceController;
import project.model.Model;
import project.view.TangramApplication;

/** Chain of responsibility element for rotating a piece left. */
public class RotateLeft extends ProcessKey {
	
	TangramApplication app;
	Model model;

	public static final int rotateLeft = KeyEvent.VK_LEFT;
	
	public RotateLeft(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	boolean process(int keyCode) {
		if (keyCode == rotateLeft) {
			new RotateActivePieceController(app, model).rotate(-1);
			return true;
		} else {
			return false;
		}
	}
}
