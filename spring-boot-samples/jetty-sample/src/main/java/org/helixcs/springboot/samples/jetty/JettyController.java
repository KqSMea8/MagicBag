package org.helixcs.springboot.samples.jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class JettyController {
    private static final Logger jettyLog = LoggerFactory.getLogger("jettyLog");
    @Resource
    private HelloServiceImpl helloService;

    @GetMapping("/jetty")
    @ResponseBody
    public String sayHello(){
        jettyLog.info("fuck all things.");
        return helloService.sayHello();
    }


}
