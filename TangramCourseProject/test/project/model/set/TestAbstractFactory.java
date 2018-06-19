package project.model.set;

import java.util.Optional;

import junit.framework.TestCase;

public class TestAbstractFactory extends TestCase {
	
	public void testStart() {
		for (String choice : AbstractFactory.knownSets) {
			Optional<AbstractFactory> opt = AbstractFactory.choose(choice);
			assertTrue (opt.isPresent());
			
			assertEquals (choice, opt.get().name());
		}
	}
	
	public void testNonExistent() {
		Optional<AbstractFactory> opt = AbstractFactory.choose(null);
		assertFalse (opt.isPresent());
		
		opt = AbstractFactory.choose(""); // non-existent
	}
	

}
