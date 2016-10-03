package io.github.newnc.util;

import java.util.Observable;
import java.util.Observer;

import io.github.newnc.model.AbstractRepository;

public class DataReloadTimer<T extends AbstractRepository> implements Observer, Runnable {
	
	private static DataReloadTimer<? extends AbstractRepository> instance;
	public static <E extends AbstractRepository> DataReloadTimer<? extends AbstractRepository> getTimer() {
		if (instance == null)
			instance = new DataReloadTimer<E>();
		return instance;
	}
	private DataReloadTimer() {
	}
	
	private AbstractRepository repository;
	private Thread thread;

	@Override
	public void update(Observable o, Object arg) {
		repository = (AbstractRepository) o;
		
		if (thread == null)
			thread = new Thread(this);
		
		if (thread.isAlive()) {
			thread.interrupt();
			
			// if we don't instantiate thread here, JVM give us not friendly exception.
			if (!repository.isEmpty())
				thread = new Thread(this);
		}
		
		thread.start();
		System.out.println(thread.toString());
	}
	@Override
	public void run() {
		try {
			while (true) {
				System.out.println(System.currentTimeMillis());
				// 10 segundos
				Thread.sleep(60 * 1000);
				
				repository.forceUpdate();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
