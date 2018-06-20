package project;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import project.controller.QuitController;
import project.controller.SolutionController;
import project.controller.chain.FlipPiece;
import project.controller.chain.ProcessKey;
import project.controller.chain.RotateLeft;
import project.controller.chain.RotateRight;
import project.model.Model;
import project.view.TangramApplication;

public class Main {

	/** All app-level customization here. If becomes more complicated, make its own class. */
	public static void customize(TangramApplication app, Model model) {
		// construct instance of the Puzzle Solver.
		SolutionController.instanceOf(app, model);

		ProcessKey left = new RotateLeft(app, model);
		ProcessKey right = new RotateRight(app, model);
		ProcessKey flip = new FlipPiece(app, model);

		left.setNext(right).setNext(flip);
		app.setKeyHandler(left);
	}

	public static void main (String[] args) {
		Model m = Model.defaultModel();

		// if using 'app' within the anonymous class generated below, must be marked final.
		final TangramApplication app = new TangramApplication(m);

		// All customization delegated to this method. 
		customize(app, m);

		// Disposing the window will complete the application
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (new QuitController().confirm(app)) {
					app.dispose();
				}
			}      
		});

		app.setVisible(true);	
	}
} 
