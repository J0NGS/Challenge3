package br.joao.neto.msVotingSession.repository;

import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import br.joao.neto.msVotingSession.model.VotingSession;
import java.util.Optional;


@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, UUID> {
    Page<VotingSession> findAll(Pageable pageable);
    Optional<VotingSession> findById(UUID id);
    Optional<VotingSession> findByProposalId(UUID votingSession);
}
