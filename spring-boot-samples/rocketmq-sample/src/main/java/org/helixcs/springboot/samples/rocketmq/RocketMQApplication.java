package org.helixcs.springboot.samples.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: helix
 * @Time:7/14/18
 */
@SpringBootApplication(scanBasePackages = "org.helixcs.springboot.samples.rocketmq")
public class RocketMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMQApplication.class,args);
    }
}
