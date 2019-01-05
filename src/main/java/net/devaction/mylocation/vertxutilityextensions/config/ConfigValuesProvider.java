package net.devaction.mylocation.vertxutilityextensions.config;

/**
 * @author Víctor Gil
 * since January 2019 
 */
public interface ConfigValuesProvider{

    public String getString(String key);
    
    public Integer getInteger(String key);
    
    public Double getDouble(String key);
    
    public Long getLong(String key);    
}

