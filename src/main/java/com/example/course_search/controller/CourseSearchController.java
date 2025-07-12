package com.example.course_search.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_search.service.AutoCompleteService;
import com.example.course_search.service.CourseSearchService;

@RestController
@RequestMapping("/api")
public class CourseSearchController {

    private final CourseSearchService searchService;
    private final AutoCompleteService autocompleteService;

    public CourseSearchController(CourseSearchService searchService, AutoCompleteService autocompleteService) {
        this.searchService = searchService;
        this.autocompleteService = autocompleteService;
    }

    @GetMapping("/search")
    public Map<String, Object> searchCourses(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam(defaultValue = "upcoming") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return searchService.searchCourses(
                q, minAge, maxAge, category, type,
                minPrice, maxPrice, startDate, sort, page, size
        );
    }
    public CourseSearchController(AutoCompleteService autocompleteService, CourseSearchService searchService) {
        this.searchService = searchService;
        this.autocompleteService = autocompleteService;
    }

    @GetMapping("/suggest")
    public List<String> suggest(@RequestParam String q) throws IOException {
        return autocompleteService.suggest(q);
    }
}
