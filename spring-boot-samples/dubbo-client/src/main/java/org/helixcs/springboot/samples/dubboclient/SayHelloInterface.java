package org.helixcs.springboot.samples.dubboclient;

public interface SayHelloInterface {
    default  String sayHello(String... args){ return  null;}
}
