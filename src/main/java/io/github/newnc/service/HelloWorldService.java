package io.github.newnc.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldService {

  @RequestMapping("/")
  public String home() {
    return "Yep, we're still here...";
  }
  
  
}
