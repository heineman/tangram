package project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;

/**
 * Shows all Tangram pieces in single panel, meant to be scrolled over.
 * 
 * Each piece is shows in its normal orientation, assuming squareSize pixels for square length
 */
public class PiecesView extends JPanel {

	/** Model. */
	Model model;
	
	/** Pieces to be drawn. PlacedPiece knows the TangramPiece and a designated (x,y) location. */
	List<PlacedPiece> pieces = new ArrayList<>(); 

	/** Size of each square. */
	public final static int squareSize = 128;

	/** Buffer for between and around pieces. */
	public final int offset = 4;

	/** Off-screen image for drawing (and Graphics object). */
	Image offScreenImage = null;
	Graphics offScreenGraphics = null;

	/** Given a set of Tangram pieces, draw them in this panel. */
	public PiecesView(Model model) {
		this.model = model;
		computePieces();
	}

	/** Refresh pieces because Model has changed its pieces set. */
	public void computePieces() {
		pieces.clear();
		
		offScreenImage = null; // force redraw
		
		// Compute PlacedPiece for each TangramPiece in set.
		int offset_y = 0;
		for (TangramPiece piece : model.getTangramSet()) {
			PlacedPiece pp = new PlacedPiece (piece, PlacedPiece.NO_FLIP, squareSize, PlacedPiece.NO_ROTATION, new Point (0, offset_y));
			pieces.add(pp);

			offset_y += squareSize;
		}
	}
	
	/** 
	 * Swing thing. We must be large enough to draw all Tangram pieces. 
	 */
	@Override
	public Dimension getPreferredSize() {
		int width = squareSize + 2*offset;
		int height = 2*offset + model.getTangramSet().size()*(squareSize+offset);

		return new Dimension (width, height);
	} 

	/**
	 * Draw each piece vertically
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (offScreenImage == null) {
			// create on demand. Leave if fails for any reason.
			Dimension s = getPreferredSize();
			offScreenImage = this.createImage(s.width, s.height);
			if (offScreenImage == null) { return; }
			
			offScreenGraphics = offScreenImage.getGraphics();
			redraw();
		}
		
		// copy image into place.
		g.drawImage(offScreenImage, 0, 0, this);
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

	/** Redraws all pieces into offscreen image, simplified by PlacedPiece. */
	void redraw() {
		for (PlacedPiece piece : pieces) {
			
			// If piece has been placed in puzzle, color gray (otherwise black). 
			if (model.getPuzzle().isPresent()) {
				Puzzle puzzle = model.getPuzzle().get();
				if (puzzle.contains(piece.getPiece().id)) {
					offScreenGraphics.setColor(Color.gray);	
				} else {
					offScreenGraphics.setColor(Color.black);	
				}
			} 
			
			offScreenGraphics.fillPolygon(piece.getPolygon());
		}
	}

	/** Helper function to map a point to a specific PlacedPiece in our view. */
	Optional<PlacedPiece> find (Point p) {
		for (PlacedPiece piece : pieces) {
			Polygon poly = piece.getPolygon();
			
			if (poly.contains(p)) {
				return Optional.of(piece);
			}
		}
		
		return Optional.empty();
	}
}
