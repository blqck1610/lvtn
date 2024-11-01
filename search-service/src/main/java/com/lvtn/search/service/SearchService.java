package com.lvtn.search.service;

import java.util.List;

public interface SearchService {
    public List<String> autoComplete(String prefix);
}
