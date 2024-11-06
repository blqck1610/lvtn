package com.lvtn.utils.util;

import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.constant.Common;
import com.lvtn.utils.dto.request.page.PagingRequest;
import com.lvtn.utils.exception.BaseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
