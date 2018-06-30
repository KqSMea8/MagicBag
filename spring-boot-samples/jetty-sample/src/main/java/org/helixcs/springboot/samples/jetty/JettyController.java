package org.helixcs.springboot.samples.jetty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class JettyController {
    @Resource
    private HelloServiceImpl helloService;

    @GetMapping("/jetty")
    @ResponseBody
    public String sayHello(){
        return helloService.sayHello();
    }


}
