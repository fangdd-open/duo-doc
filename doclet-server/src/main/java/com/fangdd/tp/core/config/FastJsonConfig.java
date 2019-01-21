package com.fangdd.tp.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import static com.alibaba.fastjson.util.IOUtils.UTF8;


@Configuration
public class FastJsonConfig {
    static {
        try {
            JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Bean
    public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4() {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        converter.setSupportedMediaTypes(Lists.newArrayList(
                new MediaType("application", "json", UTF8),
                new MediaType("application", "*+json", UTF8)
        ));
        return converter;
    }
}
