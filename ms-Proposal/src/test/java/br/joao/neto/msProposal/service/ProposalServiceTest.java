package br.joao.neto.msProposal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msProposal.model.Proposal;
import br.joao.neto.msProposal.model.DTO.Result;
import br.joao.neto.msProposal.repo.ProposalRepository;

public class ProposalServiceTest {

    @InjectMocks
    private ProposalService proposalService;

    @Mock
    private ProposalRepository proposalRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProposal() {
        Proposal proposal = new Proposal("Test Proposal", "Description", 5, 0, 0);

        Mockito.when(proposalRepository.existsByTitleIgnoreCase("Test Proposal")).thenReturn(false);
        Mockito.when(proposalRepository.save(proposal)).thenReturn(proposal);

        ResponseEntity<Proposal> response = proposalService.create(proposal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(proposal, response.getBody());
    }

    @Test
    void testCreateProposalWithTitleAlreadyExists() {
        Proposal proposal = new Proposal("Existing Proposal", "Description", 5, 0, 0);

        Mockito.when(proposalRepository.existsByTitleIgnoreCase("Existing Proposal")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> proposalService.create(proposal));
    }

    @Test
    void testLearnVoteApproved() {
        UUID proposalId = UUID.randomUUID();
        Proposal proposal = new Proposal("Test Proposal", "Description", 5, 0, 0);

        Mockito.when(proposalRepository.findById(proposalId)).thenReturn(Optional.of(proposal));

        ResponseEntity<Integer> response = proposalService.learnVote(true, proposalId);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    void testLearnVoteRejected() {
        UUID proposalId = UUID.randomUUID();
        Proposal proposal = new Proposal("Test Proposal", "Description", 5, 0, 0);

        Mockito.when(proposalRepository.findById(proposalId)).thenReturn(Optional.of(proposal));

        ResponseEntity<Integer> response = proposalService.learnVote(false, proposalId);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    void testLearnVoteProposalNotFound() {
        UUID proposalId = UUID.randomUUID();

        Mockito.when(proposalRepository.findById(proposalId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> proposalService.learnVote(true, proposalId));
    }

    @Test
    void testGetResult() {
        String title = "Test Proposal";
        Proposal proposal = new Proposal(title, "Description", 5, 2, 1);

        Mockito.when(proposalRepository.existsByTitleIgnoreCase(title)).thenReturn(true);
        Mockito.when(proposalRepository.findByTitleIgnoreCase(title)).thenReturn(Optional.of(proposal));

        ResponseEntity<Result> response = proposalService.getResult(title);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Result expectedResult = new Result();
        expectedResult.toModel(proposal);
        assertEquals(expectedResult, response.getBody());
    }

    @Test
    void testGetResultProposalNotFound() {
        String title = "Non-existent Proposal";

        Mockito.when(proposalRepository.existsByTitleIgnoreCase(title)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> proposalService.getResult(title));
    }
}
