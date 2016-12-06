package io.github.newnc.model.repository;

/**
 * This class represents a <b>abstraction</b> of data repository that can be 
 * observable by observers.
 *
 */
public abstract class AbstractRepository{
	
	/**
	 * Updates the data of the repository.
	 */
	protected abstract void update();
	
	/**
	 * Forces the update of the data.
	 */
	public abstract void forceUpdate();
	
	/**
	 * Updates the data if necessary.
	 */
	public abstract void updateIfNeeded();
	
	/**
	 * Clear the data.
	 */
	public abstract void clear();
	
	/**
	 * Initialize the object
	 */
	protected abstract void init();
	
	/**
	 * Verifies the repository is empty.
	 * 
	 * @return true if the repository is empty, otherwise false.
	 */
	public abstract boolean isEmpty();
	
	public abstract String getRandomCover();
	
	public abstract String[] getRandomCoversFromEachCategory();
	
	public abstract String[] getRandomCoversForResult(int category);

}
