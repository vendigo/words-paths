package com.github.vendigo.wordspaths.repository;

import java.util.List;
import java.util.Set;

import org.neo4j.driver.internal.value.PathValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.github.vendigo.wordspaths.model.WordNode;

public interface WordGraphRepository extends Neo4jRepository<WordNode, String> {

    @Query("""
        MATCH p = allShortestPaths((:Word{text: $from, length: $length})-[*1..20]->(:Word{text: $to, length: $length}))
        WHERE none(word IN nodes(p) WHERE word.text IN $excludedWords)
        RETURN p
        """)
    List<PathValue> findShortestPath(@Param("from") String from, @Param("to") String to, @Param("length") Integer length,
        @Param("excludedWords") Set<String> excludedWords);

    @Query("MATCH "
        + "  (a:Word{text: $from}), "
        + "  (b:Word{text: $to}) "
        + "CREATE (a)-[r:related]->(b)")
    void createRelation(@Param("from") String from, @Param("to") String to);
}
