package sample.model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class Model {

	/** Keep track of a number of polygons. */
	ArrayList<Polygon> list = new ArrayList<>();
	
	/** Which is the currently selected polygon. */
	Polygon current = null;
	
	/** 
	 * Return polygons in the model.
	 * @return iterator of polygons  
	 */
	public Iterator<Polygon> iterator() { return list.iterator(); }
	
	/** Return total number of polygons. */
	public int size() { return list.size(); }
	
	/** Clear all polygons in model. */
	public void removeAll() { 
		list.clear();
		current = null;
	}
	
	/** Add polygon. */
	public void addPolygon(Polygon poly) { 
		list.add(poly); 
	}
	
	/** Select polygon to be current, if already exists. */
	public void makeCurrent(Polygon poly) {
		if (list.contains(poly)) {
			current = poly;
		}
	}
	
	/** Unselect current polygon, if one had been selected. */
	public void unselectPolygon() { current = null; }
	
	/** Return the selected polygon (if any). */
	public Optional<Polygon> getSelected() {
		Optional<Polygon> option = Optional.ofNullable(current); 
		return option;
	}
	
	/** Simply determine if polygon is the current one. */
	public boolean isSelected(Polygon poly) {
		return (poly != null && poly == current);
	}
}