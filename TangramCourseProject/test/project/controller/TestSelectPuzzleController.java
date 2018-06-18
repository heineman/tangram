package project.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import project.model.Model;
import project.model.Parser;
import project.view.TangramApplication;
import junit.framework.TestCase;

// Also tests SelectPuzzleController since they are tied together
public class TestSelectPuzzleController extends TestCase {
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
	
	public void testInvalid() {
		File errorFile = new File ("Non-existent-file-somewhere");
		assertFalse(new SelectPuzzleController(app, model).load(errorFile));
	}
	
	public void testNonXMLFile() {
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("sample.", Parser.Suffix);
		} catch (IOException ioe) {
			// can't even create temp file? Skip test entirely...
		}
		
		if (tmpFile != null) {
			tmpFile.deleteOnExit();
			try (PrintStream ps = new PrintStream(tmpFile)) {
				ps.println("GARBAGE FILE");
				ps.close();
				assertFalse(new SelectPuzzleController(app, model).load(tmpFile));
				
			} catch (FileNotFoundException e) {
			}
		}
	}
}
