package com.lvtn.utils.constant;

/**
 * ApiEndpoint
 * Version 1.0
 * Date: 23/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 23/10/2024        NGUYEN             create
 */
public class ApiEndpoint {
    public static final String BASE_API = "/api/v1";
    public static final String INTERNAL = "/internal";

    //    User
    public static final String USER = "/user";
    public static final String GET_BY_USERNAME = "/get-by-username";

    //    auth
    public static final String AUTH = "/auth";
    public static final String REGISTER = "/register";
    public static final String AUTHENTICATE = "/authenticate";
    public static final String REFRESH_TOKEN = "/refresh-token";
    public static final String EXTRACT_ALL_CLAIMS = "/extract-all-claims";
    public static final String IS_TOKEN_VALID = "/is-token-valid";
    public static final String IS_TOKEN_EXPIRED = "/is-token-expired";

    public static final String SETTING = "/setting";

    public static final String ID = "/{id}";
    public static final String VIEW_LIST = "/view-list";


}
