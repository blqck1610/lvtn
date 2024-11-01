package com.lvtn.search.controller;

import com.lvtn.search.service.SearchService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lvtn.utils.constant.ApiEndpoint.*;

/**
 * SearchController
 * Version 1.0
 * Date: 01/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 01/11/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = BASE_API + SEARCH)
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping(value = AUTO_COMPLETE)
    public ApiResponse<List<String>> autoCompleteProductName(String prefix) {
        return ResponseUtil.getApiResponse(HttpStatus.OK.value(),
                SuccessMessage.OK.getMessage(),
                searchService.autoComplete(prefix));
    }
}
