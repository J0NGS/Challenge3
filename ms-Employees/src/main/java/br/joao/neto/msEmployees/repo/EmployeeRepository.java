package br.joao.neto.msEmployees.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.joao.neto.msEmployees.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    boolean existsByCpf(String cpf);
    Optional<Employee> findBycpf(String cpf);
    Optional<List<Employee>> findByNameIgnoreCase(String name);
    Optional<List<Employee>> findByNameContainingIgnoreCase(String name);
    Page<Employee> findAll(Pageable pageable);
}
