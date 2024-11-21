package com.lvtn.utils.util;

import com.lvtn.utils.dto.page.PagingRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * PageUtil
 * Version 1.0
 * Date: 17/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 17/10/2024        NGUYEN             create
 */
public class PageUtil {
    public static <T> PageRequest getPageRequest(PagingRequest<T> requestPaging) {
        return StringUtils.isEmpty(requestPaging.getSortField())
                ? PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize())
                : PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize(),
                Sort.by(requestPaging.getSortBy(), new String[]{requestPaging.getSortField()}));
    }
}
