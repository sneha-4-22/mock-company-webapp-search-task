package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class ReportController {

    private final SearchService searchService;
    private final EntityManager entityManager;

    @Autowired
    public ReportController(SearchService searchService, EntityManager entityManager) {
        this.searchService = searchService;
        this.entityManager = entityManager;
    }

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        // Total product count
        int count = entityManager.createQuery("SELECT item FROM ProductItem item").getResultList().size();
        response.setProductCount(count);

        // Counting specific terms
        hits.put("Cool", searchService.countProductsByQuery("%cool%"));
        hits.put("Kids", searchService.countProductsByQuery("(?i).*kids.*"));
        hits.put("Amazing", searchService.countProductsByQuery("%amazing%"));
        hits.put("Perfect", searchService.countProductsByQuery("%perfect%"));

        return response;
    }
}
