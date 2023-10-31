package br.joao.neto.msVotingSession.model.DTO;

import java.util.UUID;

import br.joao.neto.msVotingSession.model.Votes;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VotesResponse {
    private UUID id;
    private UUID employeeId;
    private boolean vote;

    public VotesResponse(Votes vote){
        this.id = vote.getId();
        this.employeeId = vote.getEmployeeId();
        this.vote = vote.isVote();
    }
}
