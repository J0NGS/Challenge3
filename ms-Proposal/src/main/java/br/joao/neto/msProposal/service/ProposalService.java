package br.joao.neto.msProposal.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msProposal.clients.model.Votes;
import br.joao.neto.msProposal.model.Proposal;
import br.joao.neto.msProposal.model.DTO.Result;
import br.joao.neto.msProposal.repo.ProposalRepository;
import jakarta.transaction.Transactional;

@Service
public class ProposalService {
    private ProposalRepository repository;
    
    @Autowired
    public ProposalService(ProposalRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseEntity<Proposal> create(Proposal proposal) {
        if(repository.existsByTitleIgnoreCase(proposal.getTitle()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Title already exists");
        return new ResponseEntity<>(repository.save(proposal), HttpStatus.CREATED);
    }


    public ResponseEntity<Integer> learnVote(Votes vote, UUID idProposal) {
        Proposal proposal = repository.findById(idProposal).orElseThrow(() -> 
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposal not found"));

        if (vote.isVote()){
            proposal.setVotesApproved(proposal.getVotesApproved() + 1);
            return new ResponseEntity<Integer>(proposal.getVotesApproved(), HttpStatus.ACCEPTED);
        }else{ 
            proposal.setVotesRejected(proposal.getVotesRejected() + 1);
            return new ResponseEntity<Integer>(proposal.getVotesRejected(), HttpStatus.ACCEPTED);
        } 
    }

    public ResponseEntity<Result> getResult(String title){
        if(repository.existsByTitleIgnoreCase(title)){
            Result result = new Result(); 
            result = result.toModel(repository.findByTitleIgnoreCase(title).get());
            return new ResponseEntity<Result>(result, HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposal not found");
        }
    }

    public ResponseEntity<Proposal> getById(UUID uuid){
        Proposal proposal = repository.findById(uuid).orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposal not found"));
        return new ResponseEntity<Proposal>(proposal, HttpStatus.FOUND);
    }

    @Transactional
    public ResponseEntity<Result> updateStatus(UUID uuid) {
        Proposal proposal = repository.findById(uuid).orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposal not found"));
        proposal.setFinished(!proposal.isFinished());
        repository.save(proposal);
        Result result = new Result();
        result = result.toModel(proposal);
        return new ResponseEntity(result, HttpStatus.ACCEPTED);
    }
}