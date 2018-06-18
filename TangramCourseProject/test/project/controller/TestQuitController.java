package project.controller;

import junit.framework.TestCase;
import project.model.Model;
import project.view.TangramApplication;

public class TestQuitController extends TestCase {

	TangramApplication app;
	Model model;
	
	@Override
	protected void setUp() {
		model = Model.defaultModel();
		app = new TangramApplication (model);
		app.setVisible(true);
	}
	
	@Override 
	protected void tearDown() {
		app.dispose();
	}
	
	public void testExecutes() {
		// Just execute without really testing
		Thread thread = new Thread() {
			public void run() {
				new QuitController().confirm(app);
			}
		};
		thread.start();
	}
}
