package br.joao.neto.msVotingSession.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "votingsessionmodel")
public class VotingSession implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id_voting_session")
    private UUID id;

    @Column(name = "proposalId")
    private UUID proposalId;

    @Column(name = "votes")
    @OneToMany(mappedBy = "votingSession", cascade = CascadeType.ALL)
    private List<Votes> votes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionDate;

    @PrePersist
    protected void onCreate() {
        sessionDate = new Date();
    }

    public VotingSession(UUID proposalId) {
        this.proposalId = proposalId;
    }
}