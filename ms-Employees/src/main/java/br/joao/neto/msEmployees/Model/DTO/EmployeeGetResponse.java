package br.joao.neto.msEmployees.model.DTO;

import br.joao.neto.msEmployees.model.Employee;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class EmployeeGetResponse {
    private String name;
    private String cpf;

    public EmployeeGetResponse toModel(Employee employee){
        this.name = employee.getName();
        this.cpf = employee.getCpf();
        return this;
    }

}
