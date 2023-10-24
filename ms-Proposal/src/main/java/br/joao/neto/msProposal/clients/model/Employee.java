package br.joao.neto.msProposal.clients.model;

import java.util.Date;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {
    private UUID id;
    private String name;
    private String cpf;
    private Date registrationDate;
}
