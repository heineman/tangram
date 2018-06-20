package project.view;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;

/**
 * Here is where the pieces are to be played (in 512x512 size). 
 */
public class PuzzleView extends JPanel {

	/** Core model. */
	Model model;

	/** around edges. */
	int offset = 32;
	
	/** Off-screen image for drawing (and Graphics object). */
	Image offScreenImage = null;
	Graphics offScreenGraphics = null;
	
	/** Used to create random colors for pieces. */
	Random rand = new Random();
	Hashtable<PlacedPiece,Color> colorMap = new Hashtable<>();

	/** Show congratulations since solved? */
	Font largeFont = new Font("Arial", Font.PLAIN, 72);
	
	/** Given a set of Tangram pieces, draw them in this panel. */
	public PuzzleView(Model model) {
		this.model = model;
	}
	
	/** Assign colors randomly as needed. */
	Color getColor(PlacedPiece piece) {
		// initially allocate random color with each piece
		if (!colorMap.containsKey(piece)) {
			colorMap.put(piece, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		}
		return colorMap.get(piece);
	}
	
	/** 
	 * Must be large enough to draw all Tangram pieces. Make 4x as large as original square.
	 */
	@Override
	public Dimension getPreferredSize() {
		int width = 2*Puzzle.Scale + 2*offset;
		int height = 2*Puzzle.Scale + 2*offset;
		
		return new Dimension (width, height);
	}

	/**
	 * Draw background puzzle and all active pieces.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (offScreenImage == null) {
			Dimension s = getPreferredSize();
			offScreenImage = this.createImage(s.width, s.height);
			if (offScreenImage == null) { return; }
			
			offScreenGraphics = offScreenImage.getGraphics();
			redraw();
		}
		
		// copy image into place.
		g.drawImage(offScreenImage, 0, 0, this); 
		
		// draw active polygon, if puzzle is active
		if (model == null) { return; }
		if (model.getPuzzle().isPresent()) {
			Puzzle puzzle = model.getPuzzle().get();
			if (puzzle.getActive().isPresent()) {
				PlacedPiece active = puzzle.getActive().get();
				g.setColor(Color.red);
				g.fillPolygon(active.getPolygon());
			}
			
			// congratulatory text
			if (puzzle.isSolved()) {
				g.setColor(Color.blue);
				g.setFont(largeFont);
				g.drawString("Congratulations!", 10, 72);
				g.drawString("You are done!", 10, 216);
			}
		}
	}
	
	/** Draw background and then all pieces on top of it. */
	public void redraw() {
		if (offScreenImage == null) { return; }
		
		// double check if no model (for WindowBuilder)
		if (model == null) { return; }

		// Redraw puzzle state, if present.
		if (!model.getPuzzle().isPresent()) { return; }
		
		Dimension dim = getPreferredSize();
		offScreenGraphics.clearRect(0, 0, dim.width, dim.height);
		
		// solution drawn. Could optimize to do just once... 
		Puzzle puzzle = model.getPuzzle().get();
		offScreenGraphics.setColor(Color.black);
		for (Iterator<PlacedPiece> it = puzzle.solution(); it.hasNext(); ) {
			PlacedPiece p = it.next();
			offScreenGraphics.fillPolygon(p.getPolygon());
		}
		
		// placed pieces.
		for (Iterator<PlacedPiece> it = puzzle.pieces(); it.hasNext(); ) {
			PlacedPiece p = it.next();
			offScreenGraphics.setColor(getColor(p));
			offScreenGraphics.fillPolygon(p.getPolygon());
		}
	}

	/**
	 * Purpose of this method is to redraw image in the face of changes to the pieces.
	 * In our case, this only means whether a piece is selected or not. 
	 */
	public void refresh() {
		if (offScreenImage == null) { return; }
		offScreenGraphics.clearRect(0, 0, offScreenImage.getWidth(this), offScreenImage.getHeight(this));
		redraw();
		repaint();
	}
}
