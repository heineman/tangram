package project.controller;


import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import project.model.Model;
import project.view.TangramApplication;

public class AboutController {

	TangramApplication app;
	Model model;

	public AboutController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}
	
	public void about() {
		ImageIcon icon = new ImageIcon(AboutController.class.getResource("/img/TangramHelp.png"));
        
		JOptionPane.showMessageDialog(app, 
				/* Message */
				"This Tangram sample application demonstrates how to use the\n" + 
				"Entity/Boundary/Controller design paradigm to analyze, design\n" +
				"and implement a non-trivial Java application using Swing. Feel\n" + 
				"free to use these ideas in any of your code.\n" +
				"\n" +
				"Author: George Heineman (heineman@cs.wpi.edu)\n" + 
				"Date: 2018",
				
				/* Title */
				"About Tangram Application",
				
				/* Type. */
				JOptionPane.INFORMATION_MESSAGE,
				icon);
	}
}
