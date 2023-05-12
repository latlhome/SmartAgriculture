package com.smart.agriculture.security.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.security.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;





/**
 * redis操作Service的实现类
 *
 */
@Component
public class IRedisServiceImpl implements IRedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private HttpServletRequest httpServletRequest;
    /**
     * 存储数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void setToken(String key, RedisUserInfo value) {
        stringRedisTemplate.opsForValue().set("userinfo:"+key, JSON.toJSONString(value),expiration*1000,TimeUnit.MILLISECONDS);
    }


    @Override
    public void setList(String username, List<String> token){
        stringRedisTemplate.opsForValue().set("username:"+username, JSON.toJSONString(token),expiration*1000,TimeUnit.MILLISECONDS);
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    @Override
    public RedisUserInfo get(String key) {
        String str1 = stringRedisTemplate.opsForValue().get("userinfo:"+key);
        if(str1==null)return null;
        return JSONUtil.toBean(str1, RedisUserInfo.class);
    }

    @Override
    public String get(String key, Integer i) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> getList(String username){
        String str1 = stringRedisTemplate.opsForValue().get("username:"+username);
        return JSONObject.parseArray(str1,String.class);
    }



    /**
     * 设置超期时间
     *
     * @param key    键
     * @param expire 过期时间
     * @return 是否成功
     */
    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 删除数据
     *
     * @param key 键
     */
    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 自增操作
     *
     * @param key   键
     * @param delta 自增步长
     * @return
     */
    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long examRemainingTime(String partyClassCode, String username, Integer ExamMins) {
        //检测是否是第一次进来
        String flag = stringRedisTemplate.opsForValue().get("userExam:flag:" + partyClassCode + ":" + username);
        if(ObjectUtil.isNull(flag)){
            //如果为空则创建flag，标记已经进入过考试
            stringRedisTemplate.opsForValue().set("userExam:flag:" + partyClassCode + ":" + username,"flag");
        }
        String s = stringRedisTemplate.opsForValue().get("userExam:" + partyClassCode + ":" + username);
        if (ObjectUtil.isNotNull(flag) && ObjectUtil.isNull(s)) return (long) -1;
        if(ObjectUtil.isNull(s)){
            //如果为空则创建新的定时
            stringRedisTemplate.opsForValue().set("userExam:" + partyClassCode + ":" + username,ExamMins.toString(),ExamMins, TimeUnit.MINUTES);
        }
        return stringRedisTemplate.getExpire("userExam:" + partyClassCode + ":" + username, TimeUnit.SECONDS);
    }
}
