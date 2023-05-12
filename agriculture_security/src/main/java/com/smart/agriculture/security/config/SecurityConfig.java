package com.smart.agriculture.security.config;

import com.smart.agriculture.Do.SysPermission;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.security.pojo.security.AdminUserDetails;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.security.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ApplicationContext applicationContext;


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
                        "/test/**",
                        "/websocket/**",
                        "/images/**",
                        "/manager/user/login",
                        "/manager/user/ticketLogin",
                        "/druid/**"
                        ,"/jmreport/**"
                        ,"/Jmreport/**",
                        "/common/**",
                        "/mobile/user/**",
                        "/moblie/common/**"
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
        return userName -> {
            try {

                //Map<String,RedisUserInfo> redisUserInfos = iRedisService.get(userName);
                String authHeader = httpServletRequest.getHeader(this.tokenHeader);
                RedisUserInfo redisUserInfo =iRedisService.get(authHeader);
                // 从redis拿权限
                if (redisUserInfo == null) {
                    redisUserInfo = new RedisUserInfo();
                }
                List<SysPermission> permissionList = redisUserInfo.getPermissionList();
                if (permissionList == null) {
                    permissionList = new ArrayList<>();
                }
                SysUser user = new SysUser();
                user.setUsername(userName);
                return new AdminUserDetails(user, permissionList);
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
