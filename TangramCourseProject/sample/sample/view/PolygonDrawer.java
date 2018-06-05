package sample.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Iterator;

import javax.swing.JPanel;

import sample.model.Model;

public class PolygonDrawer extends JPanel {
	
	public static final int Radius = 2;
	
	Model model;
	
	/**
	 * Create the panel.
	 */
	public PolygonDrawer(Model m) {
		this.model = m;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Iterator<Polygon> it = model.iterator();
		while (it.hasNext()) {
			Polygon poly = it.next();
			
			g.setColor(Color.black);
			if (model.isSelected(poly)) {
				g.setColor(Color.red);
			}
			
			// draw lines if closed, otherwise just points.
			if (poly.npoints >= 3) {
				for (int i = 0; i < poly.npoints-1; i++) {
					g.drawLine(poly.xpoints[i], poly.ypoints[i], poly.xpoints[i+1], poly.ypoints[i+1]);
				}
				g.drawLine(poly.xpoints[poly.npoints-1], poly.ypoints[poly.npoints-1], poly.xpoints[0], poly.ypoints[0]);
			} else {
				for (int i = 0; i < poly.npoints; i++) {
					g.drawOval(poly.xpoints[i]-Radius, poly.ypoints[i]-Radius, 2*Radius, 2*Radius);
				}
			}
		}
	}
}
