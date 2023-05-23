package com.smart.agriculture.common.config;

public class RedisConstants {
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 36000L;

    public static final Long CACHE_NULL_TTL = 2L;

    public static final Long CACHE_ARTICLE_TTL = 30L;
    public static final String CACHE_ARTICLE_KEY = "cache:article:";

    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final Long LOCK_SHOP_TTL = 10L;

    public static final String SECKILL_STOCK_KEY = "seckill:stock:";
    public static final String ARTICLE_LIKED_KEY = "article:liked:";
    public static final String FEED_KEY = "feed:";
    public static final String ARTICLE_ALL_KEY = "article:all";
    public static final String COLLECTION_FEED_KEY = "collection:feed:";
    public static final String SHOP_GEO_KEY = "shop:geo:";
    public static final String USER_SIGN_KEY = "sign:";

    public static final String FOLLOWS = "follows:";
    public static final String COLLECTION = "collection:";
}
