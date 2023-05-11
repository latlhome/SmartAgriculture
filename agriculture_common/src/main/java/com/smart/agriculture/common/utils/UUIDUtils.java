package com.smart.agriculture.common.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}