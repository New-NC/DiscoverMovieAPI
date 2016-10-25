package io.github.newnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.newnc.util.MovieInitEvent;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		// If you don't want to see log messages, comment the line above.
		//io.github.newnc.debug.Print.activate();

		ConfigurableApplicationContext ctx =
				SpringApplication.run(ServerApplication.class, args);

		MovieInitEvent bean = ctx.getBean(MovieInitEvent.class);

		System.out.println("MovieInitEvent fired? " + bean.getEventFired());
	}
}
