//package com.lvtn.utils.dto.page;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * ex
// * Version 1.0
// * Date: 17/10/2024
// * Copyright
// * Modification Logs
// * DATE          AUTHOR          DESCRIPTION
// * ------------------------------------------------
// * 17/10/2024        NGUYEN             create
// */
//public class ex {
//    @GetMapping("/news")
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
//        Page<PostDto> postsDto = postService.getNews(pageable);
//        return ResponseEntity.ok(
//                ApiResponse.<Page<PostDto>>builder()
//                        .code(HttpStatus.OK.value())
//                        .message(SuccessMessage.GET_NEWS_SUCCESS.getMessage())
//                        .data(postsDto)
//                        .build()
//        );
//    }
//}
