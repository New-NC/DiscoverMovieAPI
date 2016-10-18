package io.github.newnc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * This class represents a <b>abstraction</b> of data repository that can be 
 * observable by observers.
 *
 */
public abstract class AbstractRepository extends Observable {
	protected Map<Integer, List<Integer>> listAnimal    = new HashMap<Integer, List<Integer>>();
	protected Map<Integer, List<Integer>> listTech		= new HashMap<Integer, List<Integer>>();
	protected Map<Integer, List<Integer>> listPrincess  = new HashMap<Integer, List<Integer>>();
	protected Map<Integer, List<Integer>> listAdventure = new HashMap<Integer, List<Integer>>();
	
	
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
