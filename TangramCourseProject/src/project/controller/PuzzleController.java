package project.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Optional;

import project.model.Model;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.view.PiecesView;
import project.view.PuzzleView;
import project.view.TangramApplication;

/**
 * Ensures:
 * 
 * If selected piece in model then MouseMove follows that piece here. Piece is drawn
 * always relative to its center.
 * 
 * When mouse pressed inside *AND* no selected piece, then we can move that piece around.
 */
public class PuzzleController extends MouseAdapter {

	PuzzleView puzzleView;
	PiecesView tangramView;
	Model model;

	/** If draggingPiece is present, then draggingAnchor recalls initial press point. */
	Optional<PlacedPiece> draggingPiece = Optional.empty();
	Point draggingAnchor;

	public PuzzleController(TangramApplication app, Model m) {
		this.puzzleView = app.getPuzzleView();
		this.tangramView = app.getPiecesView();
		this.model = m;
	}

	/** Determine which piece was selected in the PiecesView. */
	public void mousePressed (MouseEvent me) {
		if (!model.getPuzzle().isPresent()) { return; }
		Puzzle puzzle = model.getPuzzle().get();
		
		// select the active piece.
		if (!puzzle.getActive().isPresent()) {
			draggingAnchor = me.getPoint();

			// perhaps we are pressing inside one of the existing pieces? Take LAST piece that
			// intersects, since that will ensure we grab topmost one.
			for (Iterator<PlacedPiece> it = puzzle.pieces(); it.hasNext(); ) {
				PlacedPiece piece = it.next();
				
				if (piece.contains(draggingAnchor)) {
					// perhaps we will be dragging this one. Keep going until last one chosen.
					draggingPiece = Optional.of(piece);
					puzzle.setActive(piece);
					
					puzzleView.refresh();   // has changed state
					tangramView.refresh();  // has changed state
				}
			}
		}
	}
	
	/** With each mouse drag, translate location of draggingPiece and refresh. */
	public void mouseDragged (MouseEvent me) {
		// if nothing being dragged, leave
		if (!draggingPiece.isPresent()) { return; }

		int diffX = me.getPoint().x - draggingAnchor.x;
		int diffY = me.getPoint().y - draggingAnchor.y;
		draggingAnchor = me.getPoint();

		draggingPiece.get().translate(diffX, diffY);
		puzzleView.redraw();
		puzzleView.repaint();
	}
	
	/** Once released, no more dragging. */
	public void mouseReleased(MouseEvent me) {
		draggingPiece = Optional.empty();
		draggingAnchor = null;
		
		if (model.getPuzzle().isPresent()) {
			model.getPuzzle().get().setActive(null);
		}
		
		puzzleView.refresh();   // has changed state
		tangramView.refresh();  // has changed state
	}

	@Override
	public void mouseExited(MouseEvent me) {
		if (!model.getPuzzle().isPresent()) { return; }
		
		if (draggingPiece.isPresent()) {
			
			// piece is no longer on the board, so remove it!
			model.getPuzzle().get().remove(draggingPiece.get());
			draggingPiece = Optional.empty();
			draggingAnchor = null;
		}
		
		// clear the view of partial drawings once mouse exits region
		model.getPuzzle().get().setActive(null);

		puzzleView.refresh();   // has changed state
		tangramView.refresh();  // has changed state
	}

	

	
}
