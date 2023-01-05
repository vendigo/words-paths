package com.github.vendigo.wordspaths.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.vendigo.wordspaths.model.WordEntity;

public interface WordJpaRepository extends JpaRepository<WordEntity, String> {

    @Query("SELECT word FROM WordEntity word WHERE length(word.text) = :length")
    Page<WordEntity> findByLength(@Param("length") int length, Pageable pageable);

    @Query("SELECT count(word) FROM WordEntity word WHERE length(word.text) = :length")
    int countByLength(@Param("length") int length);

    @Query("SELECT word.text FROM WordEntity word WHERE length(word.text) = :length AND levenshtein(:word, word.text) = 1")
    List<String> findRelated(@Param("word") String word, @Param("length")Integer length);
}
