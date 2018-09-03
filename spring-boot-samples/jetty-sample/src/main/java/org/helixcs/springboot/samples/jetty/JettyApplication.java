package org.helixcs.springboot.samples.jetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = "org.helixcs.springboot.samples.jetty")
@ImportResource("dubbo-provider.xml")
public class JettyApplication {
    public static void main(String[] args) {
        SpringApplication.run(JettyApplication.class,args);
    }
}
