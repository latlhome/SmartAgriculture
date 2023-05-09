package com.smart.agriculture.common.result;


public enum ResultCode implements IErrorCode {

    // 操作标示符
    SUCCESS(200, "操作成功"),
    FAILED(-1, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),

    LIMIT_EXCEPTION(201, "访问过于频繁"),
    BAD_REQUEST(201, "非法请求"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    USER_NEED_LOGIN(401, "您还没有登陆"),
    USER_ALREADY_REG(4013, "该用户已经注册"),
    USER_NOT_EXISTED(4014, "用户不存在"),
    ACCOUNT_FREEZED(4015, "账号被冻结,请联系管理员"),
    OLD_PWD_NOT_RIGHT(4016, "原密码不正确"),
    TWO_PWD_NOT_MATCH(4017, "两次输入密码不一致"),
    AUTH_REQUEST_ERROR(4018, "账号密码错误"),

    PARAM_ERROR(400, "请求参数错误"),
    USERNAME_EMPTY(4001, "用户名不能为空"),
    PASSWROD_EMPTY(4002, "密码不能为空"),
    TOKEN_EMPTY(4003, "token不能为空"),
    APPTOKEN_EMPTY(4003, "apptoken不能为空"),
    ROLE_ALREADY_REG(4004, "角色已经存在"),
    BUSSINESS_EXCEPTION(409, "业务逻辑异常"),
    NO_PERMITION(403, "权限认证失败"),

    SIGNATURE_ERROR(405, "签名不合法"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVER_ERROR(500, "抱歉，服务器开了一会小差"),
    TOKEN_ERROR(700, "token验证失败"),
    SIGN_ERROR(700, "签名验证失败"),
    /**
     * 图片
     */
    FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),
    UPLOAD_ERROR(500, "上传图片出错"),
    /**
     * 权限和数据问题
     */
    DB_RESOURCE_NULL(400, "数据库中没有该资源"),
    REQUEST_INVALIDATE(400, "请求数据格式不正确"),
    INVALID_KAPTCHA(400, "验证码不正确"),
    CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
    CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
    CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),

    /**
     * 错误的请求
     */
    MENU_PCODE_COINCIDENCE(400, "菜单编号和副编号不能一致"),
    EXISTED_THE_MENU(400, "菜单编号重复，不能添加"),
    DICT_MUST_BE_NUMBER(400, "字典的值必须为数字"),
    ;


    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
