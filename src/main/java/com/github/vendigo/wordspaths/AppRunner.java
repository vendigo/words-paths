package com.github.vendigo.wordspaths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.vendigo.wordspaths.service.GraphPopulationService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final GraphPopulationService graphPopulationService;

    @Override
    public void run(final ApplicationArguments args) {
        //graphPopulationService.saveWords(3);
        //graphPopulationService.saveRelations(3);
    }

}
