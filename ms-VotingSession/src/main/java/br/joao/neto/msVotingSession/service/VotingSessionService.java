package br.joao.neto.msVotingSession.service;

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

import br.joao.neto.msVotingSession.model.VotingSession;
import br.joao.neto.msVotingSession.repository.VotingSessionRepository;
import jakarta.transaction.Transactional;

@Service
public class VotingSessionService {
    private VotingSessionRepository repository;
    

    @Autowired
    public VotingSessionService(VotingSessionRepository repository) {
        this.repository = repository;
   }

    @Transactional
    public ResponseEntity<VotingSession> createVotingSession(VotingSession votingSession) {
        return new ResponseEntity<>(repository.save(votingSession), HttpStatus.CREATED);
    }

    public ResponseEntity<VotingSession> getVotingSessionById(UUID id) {
        Optional<VotingSession> result = repository.findById(id);
        return result.map(emp -> new ResponseEntity<>(emp, HttpStatus.FOUND))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with id [ " + id + " ] not found"));
    }

    public ResponseEntity<Page<VotingSession>> getAllVotingSessions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VotingSession> votingSessionsPage = repository.findAll(pageable);
        return new ResponseEntity<>(votingSessionsPage, HttpStatus.OK);
    }
}



