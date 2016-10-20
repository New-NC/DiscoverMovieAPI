package io.github.newnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		// If you don't want to see log messages, comment the line above.
		io.github.newnc.debug.Print.activate();
		
		SpringApplication.run(ServerApplication.class, args);
	}
}
