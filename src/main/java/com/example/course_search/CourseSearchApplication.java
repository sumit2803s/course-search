package com.example.course_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.example.course_search.repository")
public class CourseSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseSearchApplication.class, args);
	}

}
