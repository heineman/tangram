package project.model;

import java.util.Iterator;

import project.model.set.TraditionalTangram;
import junit.framework.TestCase;

public class TestModel extends TestCase {
	
	// can produce standard set from the model.
	public void testStart() {
		TangramSet set = StandardSet.produce();
		assertEquals (7, set.size());
	}
	
	public void testSet() {
		Model m = new Model();
		TangramSet set = StandardSet.produce();
		m.setFactory(new TraditionalTangram());
		Iterator<TangramPiece> it1 = set.iterator();
		Iterator<TangramPiece> it2 = m.getTangramSet().iterator();
		while (it1.hasNext() && it2.hasNext()) {
			assertEquals (it1.next().id, it2.next().id);
		}
	}
	
	public void testPuzzle() {
		Model m = new Model();
		assertFalse (m.getPuzzle().isPresent());
		
		m.setPuzzle(new Puzzle(StandardSet.factory(), StandardSet.solution(Puzzle.Scale)));
		assertTrue (m.getPuzzle().isPresent());
	}
}
