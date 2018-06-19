package project.controller.actions;

import project.model.Action;
import project.model.PieceMemento;
import project.model.PlacedPiece;

/**
 * Uses PieceMemento to restore to prior state.
 */
public class MoveAction implements Action {

	final PlacedPiece piece;
	final PieceMemento oldState;
	final PieceMemento newState;
	
	public MoveAction(PlacedPiece piece, PieceMemento newState, PieceMemento oldState) {
		this.piece = piece;
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public boolean execute() {
		piece.restore(newState);
		return true;
	}

	@Override
	public boolean undo() {
		piece.restore(oldState);
		return true;
	}
}
