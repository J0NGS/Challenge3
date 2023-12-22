package br.joao.neto.msVotingSession.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.joao.neto.msVotingSession.model.VotingSession;
import br.joao.neto.msVotingSession.service.VotingSessionService;

@RestController
@RequestMapping("/votingSession")
public class VotingSessionController {

    private VotingSessionService votingSessionService;

    @Autowired
    public VotingSessionController(VotingSessionService votingSessionService) {
        this.votingSessionService = votingSessionService;
    }

    @PostMapping
    public ResponseEntity<VotingSession> createVotingSession(@RequestBody VotingSession votingSession) {
        return votingSessionService.createVotingSession(votingSession);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VotingSession> getVotingSessionById(@PathVariable UUID id) {
        return votingSessionService.getVotingSessionById(id);
    }

    @GetMapping
    public ResponseEntity<Page<VotingSession>> getAllVotingSessions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return votingSessionService.getAllVotingSessions(page, size);
    }
}
