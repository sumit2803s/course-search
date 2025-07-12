package com.example.course_search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.course_search.model.CourseDocument;

@Repository
public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {
}
