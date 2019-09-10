package com.fangdd.tp.service;

/**
 * @author xuwenzhen
 * @date 19/1/21
 */
public interface ApiUnwindService {
    void unwindAll();

    void unwindDocApi(String docId, Long docVersion);
}
