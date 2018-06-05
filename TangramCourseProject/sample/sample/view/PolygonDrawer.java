package sample.view;

import javax.swing.JPanel;

import sample.model.Model;

public class PolygonDrawer extends JPanel {
	
	Model model;
	
	/**
	 * Create the panel.
	 */
	public PolygonDrawer(Model m) {
		this.model = m;
	}

}
