package br.joao.neto.msProposal.repo;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.joao.neto.msProposal.model.Proposal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, UUID>{
    boolean existsByTitleIgnoreCase(String title);
    Optional<List<Proposal>> findByAuthorId(UUID authorId);
    Page<Proposal> findAll(Pageable pageable);
    Optional<Proposal> findByTitleIgnoreCase(String title);
}