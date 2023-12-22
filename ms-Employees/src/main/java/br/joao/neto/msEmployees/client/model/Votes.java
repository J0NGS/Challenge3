package br.joao.neto.msEmployees.client.model;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Votes{
    private UUID votingSession;
    private UUID employeeId;
    private boolean vote;


    public Votes(UUID employeeId, UUID votingSession, boolean vote) {
        this.employeeId = employeeId;
        this.votingSession = votingSession;
        this.vote = vote;
    }
}
