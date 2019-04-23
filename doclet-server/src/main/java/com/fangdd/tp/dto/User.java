package com.fangdd.tp.dto;

import java.io.Serializable;

/**
 * Created by xuwenzhen on 2019/4/3.
 */
public class User implements Serializable {
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
