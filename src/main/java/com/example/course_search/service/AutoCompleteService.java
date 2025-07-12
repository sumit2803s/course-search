package com.example.course_search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import co.elastic.clients.elasticsearch.core.search.Suggestion;


@Service
public class AutoCompleteService {

    private ElasticsearchClient client;
    public AutoCompleteService(ElasticsearchClient client) {
        this.client = client;
    }
    public void AutocompleteService(ElasticsearchClient client) {
        this.client = client;
    }



    public List<String> suggest(String prefix) throws IOException {
        SearchResponse<CompletionSuggestOption> response = client.search(s -> s
                .index("courses")
                .suggest(su -> su
                        .suggesters("course-suggest", sb -> sb
                                .prefix(prefix)
                                .completion(c -> c
                                        .field("suggest")
                                        .skipDuplicates(true)
                                        .size(10)
                                )
                        )
                ), CompletionSuggestOption.class);

        Map<String, List<Suggestion<CompletionSuggestOption>>> suggestions = response.suggest();
        List<Suggestion<CompletionSuggestOption>> suggestList = suggestions.get("course-suggest");

        if (suggestList == null) return List.of();

        return suggestList.stream()
                .flatMap(s -> s.completion().options().stream())
                .map(CompletionSuggestOption::text)
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}