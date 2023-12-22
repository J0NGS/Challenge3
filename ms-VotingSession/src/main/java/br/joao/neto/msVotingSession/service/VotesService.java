package br.joao.neto.msVotingSession.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msVotingSession.model.Votes;
import br.joao.neto.msVotingSession.model.VotingSession;
import br.joao.neto.msVotingSession.model.DTO.VoteRequest;
import br.joao.neto.msVotingSession.model.DTO.VotesResponse;
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

    public ResponseEntity<VotesResponse> createVote(VoteRequest vote) {
        Votes voteSave = new Votes();
        Optional<VotingSession> session = votingRepository.findByProposalId(vote.getVotingSession());
        if (!session.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session does not exist");
        }
        
        voteSave.setVotingSession(session.get());
        voteSave.setEmployeeId(vote.getEmployeeId());
        voteSave.setVote(vote.isVote());
        UUID idVotingSession = voteSave.getVotingSession().getId();
        Date now = new Date();
        

        if(votingRepository.findById(idVotingSession).get()
            .getSessionDateOver().after(now) 
            && 
            !repository.existsByEmployeeIdAndVotingSession(voteSave.getEmployeeId(), voteSave.getVotingSession()))
            {
                Votes voteReturn = repository.save(voteSave);
                VotesResponse response = new VotesResponse(voteReturn);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        else
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Session over or  user has already voted");
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

    public ResponseEntity<List<Votes>> getAllVotesBySessionId(UUID votingSessionId){
        VotingSession session = new VotingSession(); 
        if(votingRepository.existsById(votingSessionId))
            session = votingRepository.findById(votingSessionId).get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session does not exist");
        return new ResponseEntity<>(repository.findByVotingSession(session), HttpStatus.OK);
    }
}

