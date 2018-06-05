package sample.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import sample.controller.AddPointController;
import sample.controller.CompletePolygonController;
import sample.controller.ResetController;
import sample.controller.UndoController;
import sample.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menu_Polygon = new JMenu("Polygon");
		menuBar.add(menu_Polygon);
		
		menuItem_Reset = new JMenuItem("Reset");
		menuItem_Reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menu_Polygon.add(menuItem_Reset);
		menuItem_Reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ResetController(Application.this, model).reset();
			}
		});
		
		menu_Edit = new JMenu("Edit");
		menuBar.add(menu_Edit);
		
		menuItem_RemoveLast = new JMenuItem("Remove Last");
		menuItem_RemoveLast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		menu_Edit.add(menuItem_RemoveLast);
		menuItem_RemoveLast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new UndoController(Application.this, model).removeLastPoint();
			}
		});
		
		contentPane = new PolygonDrawer(model);
		
		/** Register controller to react to mouse events on PolygonDrawer. */
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// on right-mouse click
				if (SwingUtilities.isRightMouseButton(e)) {
					new CompletePolygonController(Application.this, model).complete();
				} else {
					new AddPointController(Application.this, model).addPoint(e.getPoint());
				}
			}
		});
		
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
