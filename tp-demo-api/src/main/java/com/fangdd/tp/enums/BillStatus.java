package com.fangdd.tp.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @auth ycoe
 * @date 18/8/3
 */
public enum BillStatus {
    SUCCESS(1, "支付成功"),

    FAIL(-1, "支付失败"),

    PROCESSING(2, "正在处理中"),

    CREATE(0, "待支付");


    private int status;

    private String desc;


    BillStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 枚举数据库存储值
     */
    public Serializable getValue() {
        return status;
    }

    public boolean isNeedPay() {
        return status <= 0;
    }

    @JsonValue
    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
