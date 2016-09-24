package io.github.newnc.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldService {

  @RequestMapping("/")
  public String home() {
    return "We are up and running! "
      + "You ever watched Star Wars today? "
      + "May be the force be with you!\n";
  }
}
