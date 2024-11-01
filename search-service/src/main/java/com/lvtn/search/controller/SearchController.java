package com.lvtn.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lvtn.utils.constant.ApiEndpoint.BASE_API;
import static com.lvtn.utils.constant.ApiEndpoint.SEARCH;

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

}
