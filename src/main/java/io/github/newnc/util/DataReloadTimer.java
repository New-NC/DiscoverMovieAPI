package io.github.newnc.util;

import java.util.ArrayList;
import java.util.List;

import io.github.newnc.model.repository.MoviesRepository;

public class DataReloadTimer implements Runnable{

	// Atributos
	
	private static DataReloadTimer instance;

	private List<MoviesRepository> repositories;
	
	private Thread thread;
	
	
	// Metodos

	public static DataReloadTimer getTimer(){
		if(instance == null) instance = new DataReloadTimer();
		return instance;
	}

	private DataReloadTimer(){
		System.out.println("DataReloadTimer()");
		repositories = new ArrayList<>();
		thread = new Thread(this);
		
		thread.start();
	}

	/* Metodo de Thread */
	@Override
	public void run(){
		
		long ms = 1;
		long seg = 1000 * ms;
		long min = 60 * seg;
		long hora = 60 * min;
		//long dia = 24*hora;

		try{
			
			while(true){
				System.out.println("DataReloadTimer>run() "+System.currentTimeMillis());
				Thread.sleep(hora);
				
				synchronized(this){
					if(repositories.isEmpty()){
						for(MoviesRepository r : repositories)
							r.forceUpdate();
					}	
				}
			}
			
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
	}

}
