package br.joao.neto.msEmployees.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;
    @Column(name = "cpf", nullable = false, unique = true)
    @CPF(message = "Invalid cpf value")
    @NotBlank
    private String cpf;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @PrePersist
    protected void onCreate() {
        registrationDate = new Date();
    }

    public Employee(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }
}
