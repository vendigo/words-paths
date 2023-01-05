package com.github.vendigo.wordspaths.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ua_words")
@Data
public class WordEntity {

    @Id
    String text;

    @Override
    public String toString() {
        return text;
    }
}
