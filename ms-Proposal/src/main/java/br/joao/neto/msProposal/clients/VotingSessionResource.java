package br.joao.neto.msProposal.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.joao.neto.msProposal.clients.model.VotingSession;

@FeignClient(value = "msVotingSession", path = "/votingSession")
public interface VotingSessionResource {
    @PostMapping
    public ResponseEntity<VotingSession> createVotingSession(@RequestBody VotingSession votingSession);

    @GetMapping("/{id}")
    public ResponseEntity<VotingSession> getVotingSessionById(@PathVariable UUID id);
}
