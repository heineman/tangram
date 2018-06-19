package project.model;

import java.util.Iterator;

import project.model.set.AbstractFactory;
import project.model.set.TraditionalTangram;

/** Package private class for constructing Traditional TangramSet for test cases. */
class StandardSet  {
	
	static TraditionalTangram traditional = new TraditionalTangram();

	/** Helpful method to get traditional factory. */
	public static AbstractFactory factory() {
		return traditional;
	}
	
	/** Standard Tangram set. */
	public static TangramSet produce() {
		return traditional.produce();
	}
	
	/** Standard solution of PlacedPieces for initial arrangement of TangramPieces. */
	public static Iterator<PlacedPiece> solution(int scale) {
		return traditional.solution(scale);
	}
}
