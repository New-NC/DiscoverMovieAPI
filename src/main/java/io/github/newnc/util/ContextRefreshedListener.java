/*
package io.github.newnc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.github.newnc.service.MoviesService;


@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
	private MovieInitEvent movieInitEvent;
	
	@Autowired
	public void setMovieInitEvent(MovieInitEvent mie){
		this.movieInitEvent = mie;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		System.out.println("Context Refreshed");
		
		//MoviesService ms = new MoviesService();
		
		//ms.start();
		
		movieInitEvent.setEventFired(true);
	}
	
}
*/