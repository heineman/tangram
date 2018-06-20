package project.controller.actions;

import project.controller.SolutionController;
import project.model.Action;
import project.model.PieceMemento;
import project.model.PlacedPiece;
import project.model.Puzzle;

/**
 * Remove piece from the solution area (and thus returned to pieces area).
 */
public class ReturnAction implements Action {

	final Puzzle puzzle;
	final PlacedPiece piece;
	final PieceMemento priorState;
	
	public ReturnAction(Puzzle puzzle, PlacedPiece piece, PieceMemento priorState) {
		this.puzzle = puzzle;
		this.piece = piece;
		this.priorState = priorState;
	}
	
	@Override
	public boolean execute() {
		if (!puzzle.contains(piece.getPiece().id)) {
			return false;
		}
		
		puzzle.remove(piece);
		piece.clearListeners();
		return true;
	}

	@Override
	public boolean undo() {
		piece.restore(priorState);
		puzzle.add(piece);
		piece.addListener(SolutionController.getInstance());
		return true;
	}
}
