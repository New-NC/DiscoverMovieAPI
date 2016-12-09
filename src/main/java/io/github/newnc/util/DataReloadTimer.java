package io.github.newnc.util;

import java.util.List;

import io.github.newnc.model.repository.MoviesRepository;

public class DataReloadTimer implements Runnable{

	// Atributos
	
	private List<MoviesRepository> repositories;
	
	private Thread thread;
	
	
	// Metodos

	public DataReloadTimer(List<MoviesRepository> r){
		System.out.println("DataReloadTimer()");
		repositories = r;
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
		long dia = 24*hora;
		
		long wait = dia;

		try{
			
			while(true){
				System.out.println("DataReloadTimer>run() "+System.currentTimeMillis());
				System.out.println("Waiting "+wait/1000+"s.");
				Thread.sleep(wait);
				
				synchronized(this){
					if(!repositories.isEmpty()){
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
