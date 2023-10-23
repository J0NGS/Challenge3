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

import br.joao.neto.msVotingSession.model.Votes;
import br.joao.neto.msVotingSession.service.VotesService;

@RestController
@RequestMapping("/votes")
public class VotesController {
    private VotesService votesService;

    @Autowired
    public VotesController(VotesService votesService) {
        this.votesService = votesService;
    }

    @PostMapping
    public ResponseEntity<Votes> createVote(@RequestBody Votes vote) {
        return votesService.createVote(vote);
    }

    @GetMapping("/voting-session/{votingSessionId}")
    public ResponseEntity<Page<Votes>> getVotesByVotingSessionId(
        @PathVariable UUID votingSessionId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return votesService.getVotesByVotingSessionId(votingSessionId, page, size);
    }
}
