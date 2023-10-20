package br.joao.neto.msEmployees.client.model;

import java.util.UUID;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Proposal {
    private UUID id;
    private String title;
    private UUID authorId;
    private String description;
    private Integer timer;
    
    public Proposal(String title, String description, Integer timer, UUID authorId) {
        this.title = title;
        this.description = description;
        this.timer = timer;
        this.authorId = authorId;
    }

    
}
