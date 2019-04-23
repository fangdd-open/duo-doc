package com.fangdd.doclet.test.dto;

import java.util.List;

/**
 * @author ycoe
 * @date 19/1/4
 */
public class Admin extends User {
    /**
     * 下属
     */
    private List<User> staff;

    public List<User> getStaff() {
        return staff;
    }

    public void setStaff(List<User> staff) {
        this.staff = staff;
    }
}
