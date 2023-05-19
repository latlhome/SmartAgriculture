package com.smart.agriculture.security.config;

import com.smart.agriculture.common.result.ResultCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;


/**
 * 权限异常
 * @author jie
 */
@Getter
public class CustomAuthorizeException extends AuthenticationException {

    private Long code;

    public CustomAuthorizeException(ResultCode resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CustomAuthorizeException(Long code, String message) {
        super(message);
        this.code = code;
    }

    public CustomAuthorizeException(String message) {
        super(message);
    }
}
