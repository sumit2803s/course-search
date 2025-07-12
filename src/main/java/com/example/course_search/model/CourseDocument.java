package com.example.course_search.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDocument {
    @Id
    private String id;
    private String title;
    private String description;
    private String category;
    private String type;
    private String gradeRange;
    private int minAge;
    private int maxAge;
    private double price;
    private Instant nextSessionDate;
    @CompletionField
    private Completion suggest;
}
