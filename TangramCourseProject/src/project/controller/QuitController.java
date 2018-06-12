package project.controller;

import javax.swing.JOptionPane;

import project.view.TangramApplication;

public class QuitController {
	public boolean confirm(TangramApplication app) {
		return JOptionPane.showConfirmDialog (app, "Do you wish to exit Application?") == JOptionPane.OK_OPTION;	
	}
}