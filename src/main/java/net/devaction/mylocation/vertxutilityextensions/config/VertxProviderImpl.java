package net.devaction.mylocation.vertxutilityextensions.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;

/**
 * @author Víctor Gil
 * since January 2019 
 */
public class VertxProviderImpl implements VertxProvider{
    private static final Logger log = LoggerFactory.getLogger(VertxProviderImpl.class);

    private static Vertx vertx;
    
    @Override
    public Vertx provide(){
        if (vertx == null){
            String errorMessage = "FATAL: Vertx is null";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }            
        return vertx;
    }

    public static void setVertx(Vertx vertx) {
        VertxProviderImpl.vertx = vertx;
    }   
}

