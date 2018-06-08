package project.model;

/**
 * There is always a working Tangram set.
 */
public class Model {

	/** Available Tangram set. */
	TangramSet set;

	
	/** Returns a reasonable default. */
	public static Model defaultModel() {
		Model m = new Model();
		
		TangramSet set = StandardSet.produce();
		
		m.setTangramSet(set);
		return m;
	}
	
	/** Declare which set to use. */
	public void setTangramSet(TangramSet set) {
		this.set = set;
	}

	/** Retrieve the Tangram set. */
	public TangramSet getTangramSet() {
		return set;
	}
	
}
