package br.joao.neto.msProposal.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "session_id", nullable = true)
    private UUID sessionId;

    @Column(name = "description", nullable = false)
    @Size(max = 400, message = "max 400 caracters")
    @NotBlank
    private String description;

    @Column(name = "timer", nullable = false)
    private Integer timer;

    @Column(name = "votes_approved")
    private Integer votesApproved;
    
    @Column(name = "votes_rejected")
    private Integer votesRejected;

    @Column(name = "finished", nullable = false)
    private boolean finished;

    public Proposal(String title,String description, Integer timer) {
        this.title = title;
        this.description = description;
        this.timer = timer;
        this.votesApproved = 0;
        this.votesRejected = 0;
        this.finished = false;
    }
}
