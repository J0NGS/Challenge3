package br.joao.neto.msEmployees.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.joao.neto.msEmployees.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    Optional<Employee> findBycpf(String cpf);
    Optional<Employee> findBynameContainsAllIgnoringCase(String name);
}
