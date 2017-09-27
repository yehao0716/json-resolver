package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class SystemProperties {

    private static final Logger LOG = LoggerFactory.getLogger(SystemProperties.class);
    
    private static Properties confProps = new Properties();

    private static SystemProperties instance = null;
    
    private SystemProperties(){
    	String filePath = System.getProperty("user.dir") + File.separator + "conf" + File.separator;

        try
        {
	    	File proFile = new File(filePath + "conf.properties");
	    	if (proFile.exists())
	        {
	    		confProps.load(new FileInputStream(filePath + "conf.properties"));
	        }
        }
        catch (IOException e)
        {
        	LOG.info("The Exception occured.", e);
        }
    }

    
    public synchronized static SystemProperties getInstance()
    {
        if (null == instance)
        {
            instance = new SystemProperties();
        }
        
        return instance;
    }
    
    private String getPropertiesValue(String key)
    {
    	return confProps.getProperty(key);
    }

    public String getValues(String key, String defValue)
     {
    	 String rtValue = null;
         
         if (null == key)
         {
             LOG.error("key is null");
         }
         else
         {
             rtValue = getPropertiesValue(key);
         }
         
         if (null == rtValue)
         {
             LOG.warn("SystemProperties.getValues return null, key is " + key);
             rtValue = defValue;
         }
         
         LOG.info("SystemProperties.getValues: key is " + key + "; Value is " + rtValue);
         
         return rtValue;
     }
}
