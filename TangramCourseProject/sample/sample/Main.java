package sample;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import sample.controller.QuitController;
import sample.controller.UpdateMenu;
import sample.model.Model;
import sample.view.Application;

public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Model m = new Model();
		
		final Application frame = new Application(m);
		UpdateMenu.updateMenu(frame, m);
		
		// Disposing the window will complete the application
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (new QuitController().confirm(frame)) {
					frame.dispose();
				}
			}      
		});
		
		frame.setVisible(true);
	}

}
