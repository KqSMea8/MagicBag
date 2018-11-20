package org.helixcs.springboot.samples.jetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.helixcs.springboot.samples.jetty")
//@ImportResource(value = {"all_in_one.xml"})
public class JettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JettyApplication.class,args);
    }
}
