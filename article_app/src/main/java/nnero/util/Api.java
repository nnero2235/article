package nnero.util;

/**
 * 2017/8/9 上午10:07 created by NNERO
 */
public class Api {

    public static final String DOMAIN = "http://localhost:9000";

    public static final String API_LOGIN = DOMAIN + "/api/user/login";

    public static final String API_AUTO_LOGIN = DOMAIN + "/api/user/auto/login";

    public static final String API_CHECK_NICKNAME = DOMAIN + "/api/user/check";

    public static final String API_REGISTER = DOMAIN + "/api/user/register";

    public static final String API_MODIFY_PASSWORD = DOMAIN + "/api/user/modify/pwd";

    public static final String API_GET_ARTICLE_LIST = DOMAIN + "/api/article/list";

    public static final String API_GET_ARTICLE_DETAIL = DOMAIN + "/api/article/detail";

    public static final String API_ARTICLE_LIKE = DOMAIN + "/api/article/like";

    public static final String API_ARTICLE_UNLIKE = DOMAIN + "/api/article/unlike";

    public static final String API_ARTICLE_COMMENT = DOMAIN + "/api/article/comment";

}
