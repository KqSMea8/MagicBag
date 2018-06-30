package org.helixcs.springboot.samples.jetty;

import org.springframework.stereotype.Service;

@Service(value = "helloService")
public class HelloServiceImpl {

    public String sayHello(){
        return "Hello Jetty";
    }



}
