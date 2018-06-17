package project.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;

import project.controller.AboutController;
import project.controller.CreatePuzzleController;
import project.controller.PlacePieceController;
import project.controller.PuzzleController;
import project.controller.QuitController;
import project.controller.ResetPuzzleController;
import project.controller.SelectPuzzleController;
import project.controller.StorePuzzleController;
import project.controller.chain.ProcessKey;
import project.model.Model;
import project.model.PlacedPiece;

/**
 * GUI frame converted to be amenable to EBC requirements.
 */
public class TangramApplication extends JFrame {

	JPanel contentPane;
	
	/** Represents model for application domain. */
	Model model;
	
	/** View for Tangram pieces. */
	PiecesView piecesView;
	
	/** View for the solution. */
	PuzzleView puzzleView;

	JMenuItem mntmStorePuzzle;
	JMenuItem mntmCreatePuzzle;
	JMenuItem mntmSelectPuzzle;
	JMenuItem mntmResetPuzzle;
	
	public PiecesView getPiecesView() { return piecesView; }
	public PuzzleView getPuzzleView() { return puzzleView; }
	public JMenuItem getStorePuzzle() { return mntmStorePuzzle; }
	public JMenuItem getCreatePuzzle() { return mntmCreatePuzzle; }
	public JMenuItem getSelectPuzzle() { return mntmSelectPuzzle; }
	public JMenuItem getResetPuzzle() { return mntmResetPuzzle; }

	ProcessKey keyHandler = null;
	
	/** Determine the handler for processing key events. */
	public void setKeyHandler(ProcessKey handler) { keyHandler = handler; }
	public ProcessKey getKeyHandler() { return keyHandler; }
	
	/**
	 * Create the frame.
	 */
	public TangramApplication(Model m) {
		this.model = m;
		
		setTitle("Tangram Application");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 770, 479);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnTangram = new JMenu("Tangram");
		menuBar.add(mnTangram);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnTangram.add(mntmQuit);
		
		mntmQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (new QuitController().confirm(TangramApplication.this)) {
					TangramApplication.this.dispose();
				}
			}
		});
		
		JMenu mnPuzzle = new JMenu("Puzzle");
		menuBar.add(mnPuzzle);
		
		mntmResetPuzzle = new JMenuItem("Reset Puzzle");
		mntmResetPuzzle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmResetPuzzle);
		
		mntmResetPuzzle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ResetPuzzleController(TangramApplication.this, model).reset();
			}
		});
		
		mntmSelectPuzzle = new JMenuItem("Select Puzzle...");
		mntmSelectPuzzle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmSelectPuzzle);
		
		mntmSelectPuzzle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SelectPuzzleController(TangramApplication.this, model).select();
			}
		});
		
		mntmStorePuzzle = new JMenuItem("Store Puzzle...");
		mntmStorePuzzle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmStorePuzzle);
		
		mntmStorePuzzle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StorePuzzleController(TangramApplication.this, model).store();
			}
		});
		
		mntmCreatePuzzle = new JMenuItem("Create Puzzle...");
		mntmCreatePuzzle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		mnPuzzle.add(mntmCreatePuzzle);
		
		mntmCreatePuzzle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CreatePuzzleController(TangramApplication.this, model).create();
			}
		});
		
		JMenu mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAboutTangram = new JMenuItem("About Tangram");
		mntmAboutTangram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnAbout.add(mntmAboutTangram);
		
		mntmAboutTangram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutController(TangramApplication.this, model).about();
			}
		});
		
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JScrollPane tangramSetPane = new JScrollPane();
		
		// Install PiecesView in the left scrolling panel.
		piecesView = new PiecesView(model);
		tangramSetPane.setViewportView(piecesView);

		// For each mouse event, locate actual piece in PiecesView and have it added to puzzle.
		piecesView.addMouseListener(new MouseAdapter() { 
			@Override
			public void mousePressed(MouseEvent me) {
				Optional<PlacedPiece> found = piecesView.find(me.getPoint());
				if (found.isPresent()) {
					new PlacePieceController(TangramApplication.this, model).place(found.get().getPiece());
				}
			}
		});
		
		JScrollPane solutionSetPane = new JScrollPane(); 
		
		// install PuzzleView in the right scrolling panel.
		puzzleView = new PuzzleView(model);
		solutionSetPane.setViewportView(puzzleView);
		
		PuzzleController pc = new PuzzleController(this, model);
		puzzleView.addMouseListener(pc);
		puzzleView.addMouseMotionListener(pc);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tangramSetPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(solutionSetPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(solutionSetPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
						.addComponent(tangramSetPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (keyHandler != null) {
					keyHandler.processRequest(ke.getKeyCode());
				}
			}
		});
	}
	
	// Only here for WindowBuilder
	TangramApplication() {
		this (Model.defaultModel());
	}
}
