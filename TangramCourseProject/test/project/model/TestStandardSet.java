package project.model;

import junit.framework.TestCase;

public class TestStandardSet extends TestCase {
	public void testSet() {
		TangramSet set = StandardSet.produce();
		Model m = new Model();
		m.setTangramSet(set);

		assertEquals (set, m.getTangramSet());
		
		// 7 pieces in the set.
		assertEquals (7, set.size());
		
		// Validate iterator works
		int count = 0;
		for (TangramPiece tp : set) {
			count++;
		}
		assertEquals (7, count);
	}
	
	public void testConstructor() {
		// Annoying test case one would have to write to get 100% coverage of StandardSet
		StandardSet s = new StandardSet();
		assertTrue (s != null);	
	}
}
