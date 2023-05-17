package com.smart.agriculture.security.config;


import cn.hutool.json.JSONUtil;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.security.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{

    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private IRedisService iRedisService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // 设置子线程共享
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(sra, true);
        // 拿到请求头
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        String authToken = "";
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            authToken = authHeader.substring(this.tokenHead.length());
        }
        if (authHeader != null) {
            String username = null;
            RedisUserInfo redisValue = null;
            try {
                // 解密username
                username = jwtTokenUtil.getUserNameFromToken(authToken);
                // 从redis里面拿值
                redisValue = iRedisService.get(username);
            }catch (Exception e) {
                logger.error(JSONUtil.toJsonStr(e));
            }
            if (redisValue != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}