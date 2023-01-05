package com.github.vendigo.wordspaths.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.vendigo.wordspaths.model.WordEntity;
import com.github.vendigo.wordspaths.model.WordNode;
import com.github.vendigo.wordspaths.repository.WordGraphRepository;
import com.github.vendigo.wordspaths.repository.WordJpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GraphPopulationService {

    private static final int PAGE_SIZE = 20;

    private final WordJpaRepository wordRepository;
    private final WordGraphRepository graphRepository;
    private final TransactionTemplate transactionTemplate;

    public void saveWords(int length) {
        int totalCount = wordRepository.countByLength(length);
        int totalPages = totalCount / PAGE_SIZE + 1;

        for (int page = 0; page < totalPages; page++) {
            List<WordNode> words = wordRepository.findByLength(length, PageRequest.of(page, PAGE_SIZE)).stream()
                .map(WordEntity::getText)
                .map(WordNode::new)
                .toList();
            graphRepository.saveAll(words);
            log.info("Saved words page {}/{}", page, totalPages);
        }
    }

    public void saveRelations(int length) {
        int totalCount = wordRepository.countByLength(length);
        int totalPages = totalCount / PAGE_SIZE + 1;

        for (int page = 0; page < totalPages; page++) {
            final int pageNum = page;
            transactionTemplate.execute(o -> {
                saveRelations(pageNum, length);
                return null;
            });
            log.info("Saved relations for page {}/{}", page, totalPages);
        }
    }

    private void saveRelations(int page, int length) {
        wordRepository.findByLength(length, PageRequest.of(page, PAGE_SIZE)).stream()
            .map(WordEntity::getText)
            .forEach(this::saveRelations);
    }

    private void saveRelations(String word) {
        List<String> relatedWords = wordRepository.findRelated(word, word.length());
        for (String related : relatedWords) {
            graphRepository.createRelation(word, related);
        }
    }
}
