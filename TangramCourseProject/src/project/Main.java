package project;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import project.controller.QuitController;
import project.model.Model;
import project.view.TangramApplication;

public class Main {
	public static void main (String[] args) {
		Model m = Model.defaultModel();

		// if using 'app' within the anonymous class generated below, must be marked final.
		final TangramApplication app = new TangramApplication(m);

		// Disposing the window will complete the application
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (new QuitController().confirm(app)) {
					app.dispose();
				}
			}      
		});

		app.setVisible(true);	}
} 
