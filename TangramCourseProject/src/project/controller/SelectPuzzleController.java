package project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import project.model.Model;
import project.model.Puzzle;
import project.view.TangramApplication;

/**
 * Load up a puzzle for given tangram set.
 */
public class SelectPuzzleController {
	TangramApplication app;
	Model model;

	public static final String NoPuzzleToSave = "No Puzzle to load.";

	public SelectPuzzleController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}

	public boolean select() {
		try {
			return trySelect();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(app, "unable to load puzzle");
			return false;
		}
	}
	
	boolean trySelect() throws Exception {
		File homeDir = new File (System.getProperty("user.dir"));
		JFileChooser chooser = new JFileChooser(homeDir);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				String name = f.getName();
				return name.toLowerCase().endsWith(StorePuzzleController.Suffix);
			}

			@Override
			public String getDescription() {
				return "Tangram ." + StorePuzzleController.Suffix + " Puzzle Files";
			}
		});

		int returnVal = chooser.showOpenDialog(app);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			return load(file);
		}

		return false;
	}

	/** Helper method to load right from file. */
	boolean load(File file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			model.setPuzzle((Puzzle) ois.readObject());
			app.getPiecesView().refresh();
			app.getPuzzleView().refresh();
			return true;
		} catch (IOException | ClassNotFoundException ioe) {
			return false;
		}
	}
}
