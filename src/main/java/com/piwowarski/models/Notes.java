package com.piwowarski.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;
    @Lob  //more than 255 characters
    private String recipeNotes;

    public Notes() {
    }

}
