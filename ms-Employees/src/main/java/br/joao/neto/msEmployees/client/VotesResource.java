package br.joao.neto.msEmployees.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.joao.neto.msEmployees.client.model.Votes;
import br.joao.neto.msEmployees.client.model.VotesResponse;

@FeignClient(value = "msVotingSession", path = "votes")
public interface VotesResource {
    @PostMapping
    public ResponseEntity<VotesResponse> createVote(@RequestBody Votes vote);
}
