package sample.model;

import java.awt.Polygon;

import junit.framework.TestCase;

public class TestModel extends TestCase {

	public void testInit() {
		Model m = new Model();
		assertFalse (m.getSelected().isPresent());
		assertEquals (0, m.size());
	}
	
	public void testAdd() {
		Model m = new Model();
		Polygon p1 = new Polygon();
		m.addPolygon(p1);
		m.makeCurrent(p1);
		assertTrue (m.isSelected(p1));
	}
	
	
}
