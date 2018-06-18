package project.controller;

import junit.framework.TestCase;
import project.model.Model;
import project.view.TangramApplication;

public class TestAboutController extends TestCase {

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

		// Run in own thread, catching Exception if occurs
		boolean[] failure = new boolean[1];
		Thread thread = new Thread() {
			public void run() {
				try {
					new AboutController(app, model).about();
				} catch (Exception e) {
					failure[0] = true;
				}
			}
		};
		thread.start();

		// pause to give time for exception to happen
		try { Thread.sleep(1000); } catch (InterruptedException e) { }
		
		if (failure[0]) { fail("Exception within AboutController"); }

	}
}
