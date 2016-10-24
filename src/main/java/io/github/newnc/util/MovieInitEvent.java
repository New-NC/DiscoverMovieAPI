package io.github.newnc.util;

import org.springframework.stereotype.Component;

@Component
public class MovieInitEvent {
	private Boolean eventFired = false;
	
	public Boolean getEventFired(){
		return eventFired;
	}
	
	public void setEventFired(Boolean ef){
		
		this.eventFired = ef;
	}
}
