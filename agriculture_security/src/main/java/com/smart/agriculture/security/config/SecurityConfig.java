package com.smart.agriculture.security.config;


import com.smart.agriculture.security.pojo.security.AdminSaveDetails;
import com.smart.agriculture.security.pojo.security.AdminUserDetails;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.security.pojo.security.SysUser;
import com.smart.agriculture.security.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Resource
    private IRedisService iRedisService;



    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 白名单
                .antMatchers(
                        // swagger api json
                        "/v2/api-docs",
                        // 用来获取支持的动作
                        // 用来获取api-docs的URI
                        "/swagger-resources/**",
                        // 安全选项
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/sysUser/register",
                        "/sysUser/login",
                        "/test/**",
                        "/diseaseIdentification/**",
                        "/websocket/**",
                        "/images/**",
                        "/druid/**",
                        "/common/**"
                )
                .permitAll()
                // 跨域请求会先进行一次options请求
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                // 除上面外d所有请求全部需要鉴全认证
                .anyRequest()
                .authenticated()
                .and()
                // 自定义 jwt过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        httpSecurity.headers().cacheControl();
        httpSecurity.headers().frameOptions().disable();
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            try {
            RedisUserInfo redisUserInfo =iRedisService.get(username);
                    // 从redis拿权限
                    if (redisUserInfo == null) {
                        redisUserInfo = new RedisUserInfo();
                    }
                    List<String> permissionList = redisUserInfo.getPermissionValueList();
                    if (permissionList == null) {
                        permissionList = new ArrayList<>();
                    }

                SysUser sysUser = new SysUser();
                sysUser.setUsername(username);
                return new AdminUserDetails(sysUser, permissionList);
                } catch (Exception e) {
                    throw new UsernameNotFoundException(e.getMessage());
                }
            };
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public AdminSaveDetails userSaveDetails() {
        return new AdminSaveDetails();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
