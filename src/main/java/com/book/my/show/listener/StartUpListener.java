package com.book.my.show.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Slf4j
public class StartUpListener implements ServletContextListener {
    
    private final ApplicationContext context;
    
    public StartUpListener(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context Initialised for {}", context.getApplicationName());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context Destroyed for {}", context.getApplicationName() );
    }
}
