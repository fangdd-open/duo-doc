package com.fangdd.tp;

import com.fangdd.seed.common.SeedConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by ycoe on 17/10/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class BaseJunitTest {
    private static final String APPLICATION_NAME = "tp-doc.op.fdd";

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    static {
        SeedConstant.setApplicationName(APPLICATION_NAME);
    }

    @Before
    public void evnLoad() {
        //初始化 mvc 测试类
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void tested() {

    }
}
