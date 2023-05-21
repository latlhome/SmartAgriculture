package com.smart.agriculture.service;

public interface IIsVoidService {
    /**
     * 是否登录
     * @return 是否
     */
    boolean  isLogin();
    /**
     * 植物是否存在
     * @param id 植物ID
     * @return 是否存在
     */
    boolean plantIsExist(Long id);
    /**
     * 类别是否存在
     * @param id 类别ID
     * @return 是否存在
     */
    boolean  categoryIsExist(Long id);
    /**
     * 病害是否存在
     * @param id 病害ID
     * @return 是否存在
     */
    boolean  diseaseIsExist(Long id);
    /**
     * 用户是否存在
     * @param username 用户username
     * @return 是否存在
     */
    boolean  userIsExist(String username);
    /**
     * 自由帖子是否存在
     * @param id 病害ID
     * @return 是否存在
     */
    boolean  freedomArticleIsExist(Long id);
    /**
     * 问答是否存在
     * @param id 问答帖子ID
     * @return 是否存在
     */
    boolean  askQuestionsArticleIsExist(Long id);
    /**
     * 评论是否存在
     * @param id 评论ID
     * @return 是否存在
     */
    boolean  commentIsExist(Long id);
    /**
     * 日志是否存在
     * @param id 日志ID
     * @return 是否存在
     */
    boolean  logIsExist(Long id);

}
