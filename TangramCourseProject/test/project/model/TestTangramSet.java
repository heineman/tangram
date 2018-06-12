package project.model;

import junit.framework.TestCase;

public class TestTangramSet extends TestCase {
	
	public void testStart() {
		TangramSet set = StandardSet.produce();
		
		assertTrue (set.find(1).isPresent());
		assertFalse (set.find(-99).isPresent());
	}

}
