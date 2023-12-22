package br.joao.neto.msVotingSession.model.DTO;

import java.util.UUID;

import br.joao.neto.msVotingSession.model.Votes;
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
public class VoteRequest {
    private UUID votingSession;
    private UUID employeeId;
    private boolean vote;


    public Votes toVote(){
        return new Votes(employeeId, votingSession, vote);
    }
}
