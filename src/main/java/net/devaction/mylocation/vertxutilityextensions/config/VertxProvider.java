package net.devaction.mylocation.vertxutilityextensions.config;

import io.vertx.core.Vertx;

/**
 * @author Víctor Gil
 * since January 2019 
 */
public interface VertxProvider{

    public Vertx provide();
}

