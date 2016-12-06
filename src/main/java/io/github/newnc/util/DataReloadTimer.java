package io.github.newnc.util;

import java.util.Observable;
import java.util.Observer;

import io.github.newnc.model.repository.AbstractRepository;

public class DataReloadTimer<T extends AbstractRepository> implements Observer, Runnable{

	// Atributos
	
	private static DataReloadTimer<? extends AbstractRepository> instance;

	private AbstractRepository repository;
	
	private Thread thread;
	
	
	// Metodos

	public static <T extends AbstractRepository> DataReloadTimer<? extends AbstractRepository> getTimer(){
		if(instance == null) instance = new DataReloadTimer<T>();
		return instance;
	}

	private DataReloadTimer(){
		System.out.println("DataReloadTimer()");
		repository = null;
		thread = new Thread(this);
		
		thread.start();
		
	}

	/* Metodo de Observer */
	@Override
	public void update(Observable o, Object obj){
		repository = (AbstractRepository) o;

		try{
			thread.interrupt();
			
			System.out.println("Creating new Thread");
			
			thread = new Thread(this);
			thread.start();
			
			System.out.println("Tread theoretically started(): "+thread.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* Metodo de Thread */
	@Override
	public void run(){
		
		long ms = 1;
		long seg = 1000 * ms;
		long min = 60 * seg;
		long hora = 60 * min;
		long dia = 24*hora;

		try{
			
			while(true){
				System.out.println("DataReloadTimer>run() "+System.currentTimeMillis());
				Thread.sleep(hora);
				
				if(repository != null){
					repository.forceUpdate();
				}
			}
			
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
	}

}
