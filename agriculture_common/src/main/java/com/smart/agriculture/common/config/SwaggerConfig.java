package com.smart.agriculture.common.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zfm
 * @Date: 2020/4/18 12:07
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket webApiConfig1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.smart.agriculture"))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }
    // @Bean
    // public Docket webApiConfig2(){
    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .groupName("mobile")
    //             .apiInfo(webApiInfo())
    //             .select()
    //             .apis(RequestHandlerSelectors.basePackage("com.smart.agriculture"))
    //             .paths(Predicates.not(PathSelectors.regex("/error.*")))
    //             .build()
    //             .securitySchemes(securitySchemes()).securityContexts(securityContexts());
    // }
    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("智慧农业")
                .description("软件杯智慧农业平台-JHC")
                .version("1.0")
                .contact(new Contact("java", "JHC", "2232881941@qq.com"))
                .build();
    }

    private List<ApiKey> securitySchemes() {
        // 设置请求头
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }

}
