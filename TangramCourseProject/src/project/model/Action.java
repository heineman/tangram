package project.model;

public interface Action {
	
	/** Execute the given action. */
	boolean execute();
	
	/** Reverse effects of the given action. */
	boolean undo();
}
