package net.devaction.mylocation.vertxutilityextensions.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.json.JsonObject;
import net.devaction.mylocation.vertxutilityextensions.config.ConfigValuesProviderImpl;
import net.devaction.mylocation.vertxutilityextensions.config.VertxProviderImpl;

/**
 * @author Víctor Gil
 *
 * since January 2019
 */
public class MainVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);
    
    private JsonObject vertxConfig;
    private static final String APPLICATION_CONFIGURATION = "application_configuration";
    
    @Override
    public void start(){
        log.info("Starting " + this.getClass().getSimpleName());        

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.getConfig(asyncResult -> {
            if (asyncResult.failed()) {
                log.error("FATAL: Failed to retrieve configuration: " + asyncResult.cause(), asyncResult.cause());
                vertx.close(closeHandler -> {
                    log.info("Vert.x has been closed");
                });
            } else{
                vertxConfig = asyncResult.result();
                log.info("Retrieved configuration: " + vertxConfig);
                
                //This is a workaround, kind of
                ConfigValuesProviderImpl.setAppConfig(vertxConfig.getJsonObject(APPLICATION_CONFIGURATION));                
                VertxProviderImpl.setVertx(vertx);
                
                ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/beans.xml");
                VerticlesBean verticlesBeans = (VerticlesBean) appContext.getBean("verticlesBean");
                ((ConfigurableApplicationContext) appContext).close();                
                
                //this is for the sun.misc.SignalHandler.handle method to be able to shutdown Vert.x
                VertxStarter.setVertx(vertx);
                
                deployVerticles(verticlesBeans.getVerticles());
            }
        });     
    }
    
    private void deployVerticles(List<Verticle> verticles){
        for (Verticle verticle : verticles){
            deployVerticle(verticle);
        }
    }
    
    private void deployVerticle(final Verticle verticle){
        if (verticle == null){
            log.error("The verticle which was supposed to be deployed is null, nothing to do.");
            return;
        }
        
        log.info("Going to deploy " + verticle.getClass().getSimpleName());
        vertx.deployVerticle(verticle, asyncResult -> {
            if (asyncResult.succeeded()){
                log.info("Successfully deployed " +  
                        verticle.getClass().getSimpleName() + ". Result: " + asyncResult.result());
            } else{
                log.error("FATAL: Error when trying to deploy " + verticle.getClass().getSimpleName() +
                        ": " + asyncResult.cause(), asyncResult.cause());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });    
    }
}

