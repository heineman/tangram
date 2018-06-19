package project.model.set;

import project.model.TangramPiece;
import project.model.TangramSet;
import junit.framework.TestCase;

public class TestChieNoTa extends TestCase {
	public void testSet() {
		AbstractFactory factory = AbstractFactory.choose(ChieNoIta.name).get();
		TangramSet set = factory.produce();

		// 7 pieces in the set.
		assertEquals (7, set.size());
		
		// Validate iterator works
		int count = 0;
		for (TangramPiece tp : set) {
			count++;
		}
		assertEquals (7, count);
	}
	
}
