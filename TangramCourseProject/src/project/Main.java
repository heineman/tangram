package project;

import project.model.Model;
import project.view.TangramApplication;

public class Main {
	public static void main (String[] args) {
		Model m = Model.defaultModel();
		
		TangramApplication app = new TangramApplication(m);

		app.setVisible(true);

	}
} 
