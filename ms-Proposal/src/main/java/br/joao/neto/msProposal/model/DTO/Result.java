package br.joao.neto.msProposal.model.DTO;

import br.joao.neto.msProposal.model.Proposal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Result {
    private String title;
    private Integer votesApproved, votesRejected, total, diff;
    private boolean finished;

    public Result toModel(Proposal proposal) {
        this.votesApproved = proposal.getVotesApproved();
        this.votesRejected = proposal.getVotesRejected();
        this.total = proposal.getVotesApproved() + proposal.getVotesRejected();
        this.diff = Math.abs(proposal.getVotesApproved() - proposal.getVotesRejected());
        this.finished = proposal.isFinished();
        return this;
    }
}
