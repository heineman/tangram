Version history

sample.0

	Use WindowBuilder to construct a GUI for the sample application
	
sample.1

	Modify the code to conform to EBC, by adding top-level Model class and 
	modifying the Application to expose all actionable elements via 
	navigation methods. Also added PolygonDrawer class for future drawing
	
sample.2

	Added this README file (!) Write first controller that adds point to 
	current polygon (and creates that polygon if it doesn't already exist).
	
sample.3

	Add a controller to close polygons (right mouse click). Add UpdateMenu
	capability to refresh menu items based on application state.
	
sample.4

	Add Undo controller, for removing either last point or last polygon.
	Fixed Ctrl-Z for Undo Controller action.

sample.5

	Add Reset controller and Quit controller to complete application. Fixed
	default behavior of Application to DO_NOTHING_ON_CLOSE so Quit works.

	