package br.joao.neto.msVotingSession.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.joao.neto.msVotingSession.model.Votes;
import br.joao.neto.msVotingSession.model.VotingSession;
import br.joao.neto.msVotingSession.repository.VotesRepository;
import br.joao.neto.msVotingSession.repository.VotingSessionRepository;

@Service
public class VotesService {
    private VotesRepository repository;
    private VotingSessionRepository votingRepository;
    
    @Autowired
    public VotesService(VotesRepository repository, VotingSessionRepository votingRepository) {
        this.repository = repository;
        this.votingRepository = votingRepository;
    }

    public ResponseEntity<Votes> createVote(Votes vote) {
        return new ResponseEntity<>(repository.save(vote), HttpStatus.CREATED);
    }

    public ResponseEntity<Page<Votes>> getVotesByVotingSessionId(UUID votingSessionId, int page, int qtd) {
        Pageable pageable = PageRequest.of(page, qtd);
        VotingSession votingSession = new VotingSession(); 
        if (votingRepository.existsById(votingSessionId)) {
            votingSession = votingRepository.findById(votingSessionId).get();
        }

        Page<Votes> votesPage = repository.findByVotingSession(votingSession, pageable);
        return new ResponseEntity<>(votesPage, HttpStatus.OK);
    }
}

