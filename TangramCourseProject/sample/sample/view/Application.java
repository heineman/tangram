package sample.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import sample.model.Model;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class Application extends JFrame {

	PolygonDrawer contentPane;
	JMenu menu_Polygon;
	JMenuItem menuItem_Reset;
	JMenu menu_Edit;
	JMenuItem menuItem_RemoveLast;
	
	Model model;
	
	public Application (Model m) {
		super();
		this.model = m;
	
		setTitle("Sample Polygon Drawing Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menu_Polygon = new JMenu("Polygon");
		menuBar.add(menu_Polygon);
		
		menuItem_Reset = new JMenuItem("Reset");
		menuItem_Reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menu_Polygon.add(menuItem_Reset);
		
		menu_Edit = new JMenu("Edit");
		menuBar.add(menu_Edit);
		
		menuItem_RemoveLast = new JMenuItem("Remove Last");
		menuItem_RemoveLast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0));
		menu_Edit.add(menuItem_RemoveLast);
		contentPane = new PolygonDrawer(model);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	/** Allow WindowBuilder not to complain. */
	Application () {
		this (new Model());
	}

	/** Provide access to the undo menu item. */
	public JMenuItem getUndoMenuItem() { return menuItem_RemoveLast; }
	
	/** Provide access to the reset menu item. */
	public JMenuItem getResetMenuItem() { return menuItem_Reset; }
	
	/** Provide access to the internal pane, if necessary. */
	public PolygonDrawer getPolygonDrawer() { return contentPane; }
}
