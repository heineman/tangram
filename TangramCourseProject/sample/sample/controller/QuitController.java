package sample.controller;

import javax.swing.JOptionPane;

import sample.view.Application;

public class QuitController {
	public boolean confirm(Application app) {
		return JOptionPane.showConfirmDialog (app, "Do you wish to exit Application?") == JOptionPane.OK_OPTION;	
	}
}
