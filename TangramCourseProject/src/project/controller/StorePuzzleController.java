package project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.view.TangramApplication;

/**
 * Save newly-created puzzle
 */
public class StorePuzzleController {
	TangramApplication app;
	Model model;

	public static final String Suffix = "puzzle";
	
	public static final String NoPuzzleToSave = "No Puzzle to save.";
	
	public StorePuzzleController(TangramApplication app, Model model) {
		this.app = app;
		this.model = model;
	}

	public boolean store() {
		try { 
			return tryStore();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(app, "Unable to store puzzle");
			return false;
		}
	}
		
	boolean tryStore() throws Exception {
		Optional<Puzzle> puzzleOption = model.getPuzzle();
		if (!puzzleOption.isPresent()) {
			throw new Exception (NoPuzzleToSave);
		}
		
		// if puzzle has no solution, then take all the pieces that are in play, and make them the solution
		Puzzle puzzle = puzzleOption.get();
		if (!puzzle.solution().hasNext()) {
			Iterator<PlacedPiece> newSolution = puzzle.pieces();
			puzzle = new Puzzle(newSolution);
		}
		
		// Store files (by default) in current directory
		File homeDir = new File (System.getProperty("user.dir"));
		 
		JFileChooser chooser = new JFileChooser(homeDir) {
			@Override
            public void approveSelection()  {
                File f = getSelectedFile();
                String name = f.getName();
                int idx = name.lastIndexOf(".");
                if (idx == -1) {
                	f = new File (f.getAbsolutePath() + "." + Suffix);
                	setSelectedFile(f);
                } else {
                	String suff = name.substring(idx+1);
                	if (!suff.toLowerCase().equals(Suffix)) {
                		String rewrite = f.getAbsolutePath();
                		idx = rewrite.lastIndexOf('.');
                		f = new File (rewrite.substring(0, idx+1) + "." + Suffix);
                		setSelectedFile(f);
                	}
                }
                
                if (f.exists()) {
                    int result = JOptionPane.showConfirmDialog(this,
	                   String.format("%s already exists.%n Overwrite?", f.getName()),
                       "File already exists", JOptionPane.YES_NO_OPTION);

                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                            
                        case JOptionPane.NO_OPTION:
                        case JOptionPane.CLOSED_OPTION:
                            return;
                            
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
		};
		
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		chooser.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				String name = f.getName();
				return name.toLowerCase().endsWith(Suffix);
			}

			@Override
			public String getDescription() {
				return "Tangram ." + Suffix + " Puzzle Files";
			}
		});
		
		int returnVal = chooser.showSaveDialog(app);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
			oos.writeObject(puzzle);
			oos.close();
			return true;
		}
		
		return false;
	}
}
