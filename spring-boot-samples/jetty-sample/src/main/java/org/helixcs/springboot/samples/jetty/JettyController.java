package org.helixcs.springboot.samples.jetty;

import org.helixcs.springboot.samples.dubboclient.SayHelloInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("jetty")
public class JettyController {
    private static final Logger jettyLog = LoggerFactory.getLogger("jettyLog");
    @Resource
    private HelloServiceImpl helloService;
    @Resource
    private SayHelloInterface dubboSayHelloImpl;

    @GetMapping("/")
    @ResponseBody
    public String sayHello(HttpServletRequest request){
        jettyLog.info("fuck all things.");
        return helloService.sayHello();
    }


    @GetMapping("dubbo/sayHello")
    @ResponseBody
    public String dubboSayHello(){
        return dubboSayHelloImpl.sayHello("");
    }

}
