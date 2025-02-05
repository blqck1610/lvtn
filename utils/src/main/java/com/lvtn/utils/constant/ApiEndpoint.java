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
    public static final String PUBLIC = "/public";
    public static final String ADMIN = "/admin";
    public static final String CREATE = "/create";
    public static final String GET = "/get";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    //    User
    public static final String USER = "/user";
    public static final String GET_BY_USERNAME = "/get-by-username/{username}";
    public static final String PASSWORD = "/password";
    public static final String AVATAR = "/avatar";

    //    auth
    public static final String AUTH = "/auth";
    public static final String REGISTER = "/register";
    public static final String AUTHENTICATE = "/authenticate";
    public static final String REFRESH_TOKEN = "/refresh-token";
    public static final String REVOKE_ALL_TOKEN = "/revoke-all-token";
    public static final String EXTRACT_ALL_CLAIMS = "/extract-all-claims";

    public static final String ID = "/{id}";
    public static final String VIEW_LIST = "/view-list";

    public static final String NOTIFICATION = "/notification";

    //    PRODUCT
    public static final String PRODUCT = "/product";
    public static final String AUTO_COMPLETE = "/auto-complete";
    public static final String CART = "/cart";
    public static final String ITEM = "/item";

//    REVIEW
    public static final String REVIEW = "/review";

//    Order
    public static final String ORDER = "/order";
    public static final String INVENTORY = "/inventory";
    public static final String CHECK = "/check";
    public static final String CANCEL = "/cancel";

//    payment
    public static final String PAYMENT = "/payment";
    public static final String VNPAY = "/vnpay";
    public static final String PAYPAL = "/paypal";
    public static final String TRANSACTION = "/transaction";
}

