package br.joao.neto.msProposal.clients.model;

import java.util.Date;
import java.util.List;
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
public class VotingSession {
    private UUID id;
    private UUID proposalId;
    private List<Votes> votes;
    private Date sessionDateOver;
}
