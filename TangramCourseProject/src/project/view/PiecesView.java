package project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.JPanel;

import project.model.Coordinate;
import project.model.Model;
import project.model.TangramPiece;

/**
 * Shows all Tangram pieces in single panel, meant to be scrolled over.
 * 
 * Each piece is shows in its normal orientation, assuming squareSize pixels for square length
 * 
 */
public class PiecesView extends JPanel {

	/** Model. */
	Model model;

	/** Size of each square. */
	final int squareSize = 128;

	/** Buffer for between and around pieces. */
	public final int offset = 4;

	/** Off-screen image for drawing (and Graphics object). */
	Image offScreenImage = null;
	Graphics offScreenGraphics = null;

	/** Given a set of Tangram pieces, draw them in this panel. */
	public PiecesView(Model model) {
		this.model = model;
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
		offScreenGraphics.clearRect(0, 0, offScreenImage.getWidth(this), offScreenImage.getHeight(this));
		redraw();
		repaint();
	}
	
	/** Compute polygon in view at a given offset. */
	Polygon computePolygon(TangramPiece piece, int offset_y) {
		int[] xpoints = new int[piece.size()];
		int[] ypoints = new int[piece.size()];
		
		// convert coordinate sequence into (x,y) points. 
		int idx = 0;
		for (Coordinate c : piece) {
			xpoints[idx] = (int) (squareSize*c.x);
			ypoints[idx] = offset_y + (int) (squareSize*c.y);
			idx++;
		}

		return new Polygon(xpoints, ypoints, piece.size());
	}

	/** Redraws all pieces into offscreen image, simplified by PlacedPiece. */
	void redraw() {
		int offset = 0;
		for (TangramPiece piece : model.getTangramSet()) {
			Polygon polyshape = computePolygon(piece, offset);
			offScreenGraphics.setColor(Color.black);
			offScreenGraphics.fillPolygon(polyshape);
			
			offset += squareSize;
		}
	}

}
