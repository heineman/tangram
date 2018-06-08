package project.model;

import junit.framework.TestCase;

public class TestModel extends TestCase {
	
	// can produce standard set from the model.
	public void testStart() {
		Model m = Model.defaultModel();
		TangramSet set = StandardSet.produce();
		assertEquals (7, set.size());
	}
	
	
	public void testSet() {
		Model m = new Model();
		TangramSet set = StandardSet.produce();
		m.setTangramSet(set);
		assertEquals (set, m.getTangramSet());
	}
}
