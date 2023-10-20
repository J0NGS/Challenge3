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
    private UUID sessionId;
    private String description;
    private Integer timer;
    private Integer votesApproved;
    private Integer votesRejected;
    private boolean finished;
}
