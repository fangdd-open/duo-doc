package com.fangdd.tp.doclet;

import com.github.diamond.client.PropertiesConfiguration;
import com.github.diamond.client.event.ConfigurationEvent;
import com.github.diamond.client.event.ConfigurationListener;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * @auth ycoe
 * @date 18/3/19
 */
public class SuperDiamondConfigTest {
    @Test
    public void configTest() {
        PropertiesConfiguration config = new PropertiesConfiguration("config.fangdd.net", 8283, "m.web.cp.fdd", "development");
        config.addConfigurationListener(configurationEvent -> {
            System.out.println(configurationEvent.getType() + ":" + configurationEvent.getPropertyName() + "=" + configurationEvent.getPropertyValue());
        });

        Properties ps = config.getProperties();
        for (Map.Entry entry : ps.entrySet()) {
            System.out.println(entry.getKey() + "=>" + entry.getValue());
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
