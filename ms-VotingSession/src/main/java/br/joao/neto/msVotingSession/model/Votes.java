package br.joao.neto.msVotingSession.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "votes")
public class Votes{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "voting_session_id", nullable = false)
    private VotingSession votingSession;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "voteBool", nullable = false)
    private boolean vote;

    public Votes(UUID employeId, UUID votingSession, boolean vote){
        this.employeeId = employeId;
        this.votingSession.setId(votingSession);
        this.vote = vote;
    }
}