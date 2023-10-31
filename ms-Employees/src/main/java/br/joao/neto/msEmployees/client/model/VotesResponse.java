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
public class VotesResponse{
    private UUID id;
    private UUID employeeId;
    private boolean vote;


    public VotesResponse(UUID id, UUID employeeId, boolean vote) {
        this.id = id;
        this.employeeId = employeeId;
        this.vote = vote;
    }
}
