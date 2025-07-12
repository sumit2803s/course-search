package com.example.course_search.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import com.example.course_search.model.CourseDocument;

import lombok.RequiredArgsConstructor;

@Service

public class CourseSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    public CourseSearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Map<String, Object> searchCourses(
            String keyword,
            Integer minAge,
            Integer maxAge,
            String category,
            String type,
            Double minPrice,
            Double maxPrice,
            Instant startDate,
            String sort,
            int page,
            int size
    ) {
        Criteria criteria = new Criteria();

        // Full-text search on title or description (simulated using "contains")
        if (keyword != null && !keyword.isBlank()) {
            Criteria keywordCriteria = new Criteria("title").matches(keyword)
                    .or(new Criteria("description").matches(keyword));
            criteria = criteria.and(keywordCriteria);
        }

        // Age filter
        if (minAge != null) {
            criteria = criteria.and(new Criteria("minAge").greaterThanEqual(minAge));
        }
        if (maxAge != null) {
            criteria = criteria.and(new Criteria("minAge").lessThanEqual(maxAge)); // Based on minAge field
        }

        // Price filter
        if (minPrice != null) {
            criteria = criteria.and(new Criteria("price").greaterThanEqual(minPrice));
        }
        if (maxPrice != null) {
            criteria = criteria.and(new Criteria("price").lessThanEqual(maxPrice));
        }

        // Category filter (exact match)
        if (category != null && !category.isBlank()) {
            criteria = criteria.and(new Criteria("category").is(category));
        }

        // Type filter (exact match)
        if (type != null && !type.isBlank()) {
            criteria = criteria.and(new Criteria("type").is(type));
        }

        // Date filter
        if (startDate != null) {
            criteria = criteria.and(new Criteria("nextSessionDate").greaterThanEqual(startDate));
        }


        // Sorting
        Sort sortOrder;
        switch (sort != null ? sort : "") {
            case "priceAsc":
                sortOrder = Sort.by(Sort.Direction.ASC, "price");
                break;
            case "priceDesc":
                sortOrder = Sort.by(Sort.Direction.DESC, "price");
                break;
            default:
                sortOrder = Sort.by(Sort.Direction.ASC, "nextSessionDate");
                break;
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Build query
        CriteriaQuery query = new CriteriaQuery(criteria, pageable);

        // Execute search
        SearchHits<CourseDocument> hits = elasticsearchOperations.search(query, CourseDocument.class);

        List<CourseDocument> results = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return Map.of(
                "total", hits.getTotalHits(),
                "courses", results
        );
    }
}