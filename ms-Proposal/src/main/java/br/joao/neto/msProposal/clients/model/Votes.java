package br.joao.neto.msProposal.clients.model;

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
    private UUID id;
    private UUID votingSession;
    private UUID employeeId;
    private boolean vote;
}
