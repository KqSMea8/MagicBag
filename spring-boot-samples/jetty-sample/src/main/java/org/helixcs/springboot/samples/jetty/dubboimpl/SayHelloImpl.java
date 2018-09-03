package org.helixcs.springboot.samples.jetty.dubboimpl;

import org.helixcs.springboot.samples.dubboclient.SayHelloInterface;
import org.springframework.stereotype.Service;

@Service(value = "sayHelloImpl")
public class SayHelloImpl implements SayHelloInterface {
    @Override
    public String sayHello(String... args) {
        return "Hello World";
    }
}
