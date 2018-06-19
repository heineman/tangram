package project.model.set;

import java.util.Iterator;
import java.util.Optional;

import project.model.PlacedPiece;
import project.model.TangramSet;

/**
 * Interface for a factory that returns a set and the "standard solution" for assembling
 * all pieces into the unit square.
 * 
 * Naturally, these must be consistent with each other.
 */
public abstract class AbstractFactory {
	
	/** For convenience, place here. */
	public static final String[] knownSets = { 
		TraditionalTangram.name,
		ChieNoIta.name };
	
	/** For convenience, get a factory for the given assembly, if one exists. */
	public static Optional<AbstractFactory> choose(String name) {
		if (name != null) {
			if (name.equals(TraditionalTangram.name)) {
				return Optional.of(new TraditionalTangram());
			} else if (name.equals(ChieNoIta.name)) {
				return Optional.of(new ChieNoIta());
			} 
		}
		
		return Optional.empty();
	}
	
	/** Produce the desired TangramSet on demand. */
	public abstract TangramSet produce();
	
	/** Return solution of PlacedPiece for the given TangramSet. */
	public abstract Iterator<PlacedPiece> solution(int scale);
	
	/** Return the name of set to be created. */
	public abstract String name();
}
