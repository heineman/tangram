package project.controller.chain;

/** Chain of responsibility base class for processing key press events. */
public abstract class ProcessKey {

	/** Next one in the chain. */
	ProcessKey next;

	/** Declare the next one in the chain. For programming convenience, return next one. */
	public ProcessKey setNext(ProcessKey next) {
		this.next = next;
		return next;
	}

	/** Attempt to process keyCode request, returning true if was handled; false otherwise. */
	public final boolean processRequest(int keyCode) {
		if (process(keyCode)) {
			return true;
		} else if (next != null) {
			return next.processRequest(keyCode);
		}
		
		return false;
	}

	/** React to given key press and return true if consumed. Responsiblity of subclass to extend. */
	abstract boolean process(int keyCode);
}
