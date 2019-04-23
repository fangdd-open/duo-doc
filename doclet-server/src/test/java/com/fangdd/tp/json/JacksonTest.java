package com.fangdd.tp.json;

import com.fangdd.tp.BaseJunitTest;
import com.fangdd.tp.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by xuwenzhen on 2019/4/23.
 */
public class JacksonTest extends BaseJunitTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void format() {
        String json = "{\"id2\": 23, \"name\": \"张三\"}";
        try {
            User user = objectMapper.readValue(json.getBytes(), User.class);
            System.out.println(user.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
