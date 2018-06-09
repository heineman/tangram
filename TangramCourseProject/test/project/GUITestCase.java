package project;

import java.awt.Component;
import java.awt.event.MouseEvent;

import junit.framework.TestCase;

public abstract class GUITestCase extends TestCase {
	/** (x,y) are offsets into the component. */
	public MouseEvent createPressed (Component component, int x, int y) {
		return new MouseEvent(component, MouseEvent.MOUSE_PRESSED, 
								System.currentTimeMillis(), 0, x, y, 0, false);
	}
	
	/** (x,y) are offsets into the component. */
	public MouseEvent createRightPress (Component component, int x, int y) {
		return new MouseEvent(component, MouseEvent.MOUSE_PRESSED, 
								System.currentTimeMillis(), 0, x, y, 0, true);
	}
	
	/** (x,y) are offsets into the component. */
	public MouseEvent createDragged (Component component, int x, int y) {
		return new MouseEvent(component, MouseEvent.MOUSE_DRAGGED, 
								System.currentTimeMillis(), 0, x, y, 0, false);
	}
	
	/** (x,y) are offsets into the component. */
	public MouseEvent createReleased (Component component, int x, int y) {
		return new MouseEvent(component, MouseEvent.MOUSE_RELEASED, 
				System.currentTimeMillis(), 0, x, y, 0, false);
	}
	
	/** (x,y) are offsets into the component. */
	public MouseEvent createClicked (Component component, int x, int y) {
		return new MouseEvent(component, MouseEvent.MOUSE_CLICKED, 
				System.currentTimeMillis(), 0, x, y, 1, false);
	}
	
	/** (x,y) are offsets into the component. */
	public MouseEvent createDoubleClicked (Component component, int x, int y) {
		return new MouseEvent(component, MouseEvent.MOUSE_CLICKED, 
				System.currentTimeMillis(), 0, x, y, 2, false);
	}
	
	/** (x,y) are offsets into the component. */
	public MouseEvent createExited (Component component, int x, int y) {
		return new MouseEvent(component, MouseEvent.MOUSE_EXITED,
								System.currentTimeMillis(), 0, x, y, 0, false);
	}
}
