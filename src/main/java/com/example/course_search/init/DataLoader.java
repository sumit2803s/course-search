package com.example.course_search.init;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import com.example.course_search.model.CourseDocument;
import com.example.course_search.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private CourseRepository courseRepository;



    @Override
    public void run(String... args) throws Exception {
        if (!elasticsearchOperations.indexOps(CourseDocument.class).exists()) {
            elasticsearchOperations.indexOps(CourseDocument.class).create();
            elasticsearchOperations.indexOps(CourseDocument.class).putMapping();
        }
        InputStream is = getClass().getResourceAsStream("/sample-courses.json");
        ObjectMapper mapper = new ObjectMapper();
        List<CourseDocument> courses = Arrays.asList(mapper.readValue(is, CourseDocument[].class));

        courseRepository.saveAll(courses);
        System.out.println("✔ Loaded " + courses.size() + " courses into Elasticsearch.");
    }
}
