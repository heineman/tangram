package sample;

import sample.model.Model;
import sample.view.Application;

public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Model m = new Model();
		
		Application frame = new Application(m);
		frame.setVisible(true);
	}

}
