package com.fangdd.tp.service;

/**
 * @auth ycoe
 * @date 19/1/21
 */
public interface ApiUnwindService {
    void unwindAll();

    void unwindDocApi(String docId, Long docVersion);
}
