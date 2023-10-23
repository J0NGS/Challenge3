package br.joao.neto.msProposal.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.joao.neto.msProposal.clients.model.VotingSession;
import br.joao.neto.msProposal.model.Proposal;
import br.joao.neto.msProposal.model.DTO.Result;
import br.joao.neto.msProposal.service.ProposalService;

@RestController
@RequestMapping("/proposals")
public class ProposalController {
    private ProposalService service;

    @Autowired
    public ProposalController(ProposalService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Proposal> create(@RequestBody Proposal proposal) {
        return service.create(proposal);
    }
    
    @GetMapping("/result")
    public ResponseEntity<Result> getResult(@RequestParam("title") String title) {
        return service.getResult(title);
    }

    @PostMapping("/votingSession")
    public ResponseEntity<VotingSession> openVotingSession(@RequestParam UUID uuid) {
        return service.openVotingSession(uuid);
    }
}
