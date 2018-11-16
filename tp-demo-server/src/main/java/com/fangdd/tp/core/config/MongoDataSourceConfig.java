package com.fangdd.tp.core.config;

import com.fangdd.traffic.common.mongo.core.YMongoClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ycoe on 17/10/20.
 */
@Configuration
public class MongoDataSourceConfig {
    /**
     * tp_doc库
     *
     * @return
     */
    @Bean("cmsMongoClient")
    @ConfigurationProperties("mongodb.cms")
    public YMongoClient matMongoClient() {
        return new YMongoClient();
    }
}
