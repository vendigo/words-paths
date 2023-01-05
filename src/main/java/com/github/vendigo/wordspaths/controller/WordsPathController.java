package com.github.vendigo.wordspaths.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.neo4j.driver.internal.value.PathValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.vendigo.wordspaths.repository.WordGraphRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WordsPathController {

    private final WordGraphRepository graphRepository;

    @GetMapping("/path")
    List<List<String>> findPath(@RequestParam("from") String from, @RequestParam("to") String to,
        @RequestParam(value = "excludedWord", required = false) Set<String> excludedWords) {
        var excluded = Optional.ofNullable(excludedWords).orElse(Set.of());
        List<PathValue> shortestPath = graphRepository.findShortestPath(from, to, from.length(), excluded);

        return shortestPath.stream()
            .map(path -> StreamSupport.stream(path.asPath().nodes().spliterator(), false))
            .map(nodesStream -> nodesStream.map(node -> node.get("text").asString()).toList())
            .toList();
    }
}
