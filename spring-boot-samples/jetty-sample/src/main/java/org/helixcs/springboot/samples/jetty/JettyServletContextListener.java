package org.helixcs.springboot.samples.jetty;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Configuration
public class JettyServletContextListener extends  ContextLoader implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        initWebApplicationContext(sce.getServletContext());
        System.out.println(">>>>>>>>  Jetty Servlet Context Initialized . ");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
