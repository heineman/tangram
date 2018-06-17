package project.controller.chain;

import java.awt.event.KeyEvent;

import project.controller.FlipActivePieceController;
import project.model.Model;
import project.view.TangramApplication;

/** Chain of responsibility element for flipping a piece. */
public class FlipPiece extends ProcessKey {
	
	TangramApplication app;
	Model model;

	public static final int flip = KeyEvent.VK_SPACE;
	
	public FlipPiece(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	boolean process(int keyCode) {
		if (keyCode == flip) {
			new FlipActivePieceController(app, model).flip();
			return true;
		} else {
			return false;
		}
	}
}
