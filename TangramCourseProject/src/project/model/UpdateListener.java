package project.model;

/** Interface for Observer design pattern. */
public interface UpdateListener {
	
	/** When the state of a PlacedPiece changes, listener is notified. */
	void update();
}
