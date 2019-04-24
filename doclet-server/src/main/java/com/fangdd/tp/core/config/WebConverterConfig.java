package com.fangdd.tp.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuwenzhen
 * @date 2019/4/14
 */
@Configuration
public class WebConverterConfig implements WebMvcConfigurer {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //注册jackson序列化方法
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = getObjectMapper();
        converter.setObjectMapper(mapper);
        converters.add(converter);

        //注册二进制响应
        converters.add(byteArrayHttpMessageConverter());
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                //反序列化时，忽略目标对象没有的属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

                //默认Date的格式化返回
                .setDateFormat(new SimpleDateFormat(DATE_FORMAT_PATTERN))

                //下面配置是值为null时，不显示
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)

                //下面一个配置是集合返回为空时，不显示
                .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
                ;
    }

    /**
     * 参考文档：https://www.baeldung.com/spring-mvc-image-media-data
     * @return
     */
    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_OCTET_STREAM));
        return arrayHttpMessageConverter;
    }
}