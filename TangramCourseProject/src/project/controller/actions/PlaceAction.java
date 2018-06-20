package project.controller.actions;

import java.awt.Point;
import java.util.Iterator;

import project.controller.SolutionController;
import project.model.Action;
import project.model.PlacedPiece;
import project.model.Puzzle;
import project.model.TangramPiece;

/**
 * Places a piece on the solution area.
 */
public class PlaceAction implements Action {

	final Puzzle puzzle;
	final TangramPiece piece;
	
	public PlaceAction(Puzzle puzzle, TangramPiece piece) {
		this.puzzle = puzzle;
		this.piece = piece;
	}
	
	@Override
	public boolean execute() {
		PlacedPiece p = new PlacedPiece(piece, PlacedPiece.NO_FLIP, Puzzle.Scale, PlacedPiece.NO_ROTATION, new Point(0,0));
		int x = Puzzle.Scale/2 - (int)(Puzzle.Scale*p.getPiece().center.x);
		int y = Puzzle.Scale/2 - (int)(Puzzle.Scale*p.getPiece().center.y);
		p.translate(x, y);
		
		puzzle.add(p);
		p.addListener(SolutionController.getInstance());
		return true;
	}

	@Override
	public boolean undo() {
		for (Iterator<PlacedPiece> it = puzzle.pieces(); it.hasNext(); ) {
			PlacedPiece p = it.next();
			if (p.getPiece().id == piece.id) {
				it.remove();
				p.clearListeners();
				return true;
			}
		}
		
		return false;
	}
}
