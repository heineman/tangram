package project.model;

import java.awt.Point;

public class PieceMemento {

	final Point position;
	final int rotation;
	final boolean flipped;
	
	public PieceMemento(PlacedPiece piece) {
		this.position = new Point(piece.getTranslation());
		this.rotation = piece.getRotation();
		this.flipped = piece.isFlipped();
	}
}
