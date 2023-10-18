package br.joao.neto.msEmployees.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.joao.neto.msEmployees.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    Optional<Employee> findByCPF(String cpf);
    Optional<Employee> findByNameContainsAllIgnoringCase(String name);
}