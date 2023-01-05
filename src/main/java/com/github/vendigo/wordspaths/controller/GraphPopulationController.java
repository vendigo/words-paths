package com.github.vendigo.wordspaths.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.vendigo.wordspaths.service.GraphPopulationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class GraphPopulationController {

    private final GraphPopulationService populationService;

    @GetMapping("/populate")
    public void populateGraph(@RequestParam("length") int length) {
        populationService.saveWords(length);
        log.info("Words of length {} populated", length);
        populationService.saveRelations(length);
        log.info("Relations for words of length {} populated", length);
    }
}
