package com.lvtn.utils.util;

import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.constant.Common;
import com.lvtn.utils.dto.request.page.PagingRequest;
import com.lvtn.utils.exception.BaseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        return requestPaging.getOrderList().isEmpty()
                ? PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize())
                : PageRequest.of(requestPaging.getPage() - 1, requestPaging.getSize(),
                Sort.by(requestPaging.getOrderList()));
    }

    public static List<Sort.Order> getOrderList(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(Common.COMMA)) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(Common.COMMA);
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        return orders;
    }

    public static Sort.Direction getSortDirection(String s) {
        Sort.Direction direction = null;
        try {
            direction = Sort.Direction.fromString(s);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.DIRECTION_NOT_CORRECT.getCode(), ErrorCode.DIRECTION_NOT_CORRECT.getMessage());
        }
        return direction;
    }

    public static Pageable getPageable(PagingRequest pagingRequest) {
        return getPageRequest(pagingRequest);
    }
}
//exam
//    public ResponseEntity<ApiResponse<Page<PostDto>>> getNews(
//            @RequestParam(name = "page", defaultValue = "1") int page,
//            @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
//            @RequestParam(name = "sort", defaultValue = "createdAt, desc") String[] sort
//    ) {
//        Pageable pageable = PageUtil.getPageable(PagingRequest.<Object>builder()
//                .size(pageSize)
//                .page(page)
//                .orderList(PageUtil.getOrderList(sort))
//                .build());
