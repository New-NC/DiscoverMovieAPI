package io.github.newnc.model;

import java.io.IOException;
import java.util.Observable;

/**
 * This class represents a <b>abstraction</b> of data repository that can be 
 * observable by observers.
 *
 */
public abstract class AbstractRepository extends Observable {
	
	/**
	 * Updates the data of the repository.
	 */
	protected abstract void update() throws Exception;
	
	/**
	 * Forces the update of the data.
	 */
	public abstract void forceUpdate() throws Exception;
	
	/**
	 * Updates the data if necessary.
	 */
	public abstract void updateIfNeeded() throws Exception;
	
	/**
	 * Clear the data.
	 */
	public abstract void clear();
	
	/**
	 * Verifies the repository is empty.
	 * 
	 * @return true if the repository is empty, otherwise false.
	 */
	public abstract boolean isEmpty();

}
