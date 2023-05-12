package com.smart.agriculture.security.service;


import com.smart.agriculture.security.pojo.security.RedisUserInfo;

import java.util.List;

public interface IRedisService {

    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    void setToken(String key, RedisUserInfo value);



    /**
     * 存储用户token
     *
     * @param username   键
     * @param token 值
     */
    void setList(String username, List<String> token);

    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, String value);
    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    RedisUserInfo get(String key);


    String get(String key,Integer i);


    List<String> getList(String username);


    /**
     * 设置超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return 是否成功
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 自增操作
     * @param key 键
     * @param delta 自增步长
     * @return
     */
    Long increment(String key, long delta);


    Long examRemainingTime(String partyClassCode,String username,Integer examMins);

}
