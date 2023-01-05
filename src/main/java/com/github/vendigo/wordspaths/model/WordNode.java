package com.github.vendigo.wordspaths.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node("Word")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "text")
public class WordNode {

    @Id
    private String text;
    @Property
    private Integer length;

    public WordNode(String text) {
        this.text = text;
        this.length = text.length();
    }
}
