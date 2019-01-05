package net.devaction.mylocation.vertxutilityextensions.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Verticle;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class VerticlesBean{
    private static final Logger log = LoggerFactory.getLogger(VerticlesBean.class);
    
    private List<Verticle> verticles;

    public List<Verticle> getVerticles(){
        return verticles;
    }

    public void setVerticles(List<Verticle> verticles){
        this.verticles = verticles;
    }
}

