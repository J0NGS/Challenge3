package br.joao.neto.msVotingSession.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.joao.neto.msVotingSession.model.Votes;
import br.joao.neto.msVotingSession.model.VotingSession;

@Repository
public interface VotesRepository extends JpaRepository<Votes, UUID> {
    List<Votes> findByvotingSession(VotingSession votingSessionId);
    Page<Votes> findByVotingSession(VotingSession votingSession, Pageable pageable);
}
