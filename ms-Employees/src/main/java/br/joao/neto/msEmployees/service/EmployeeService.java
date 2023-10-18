package br.joao.neto.msEmployees.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.repo.EmployeeRepository;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Employee> create(Employee employee){
        return new ResponseEntity<>(repository.save(employee), HttpStatus.CREATED);
    }

    public ResponseEntity<Employee> findById(UUID id){
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent())
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.FOUND);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empolyee with id " + id + " not found");
    }

    public ResponseEntity<Employee> findByCpf(String cpf){
        Optional<Employee> employee = repository.findBycpf(cpf);
        if(employee.isPresent())
            return new ResponseEntity<>(repository.findBycpf(cpf).get(), HttpStatus.FOUND);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empolyee with cpf " + cpf + " not found");
    }

    public ResponseEntity<Employee> findByName(String name){
        Optional<Employee> employee = repository.findByNameContainingIgnoreCase(name);
        if(employee.isPresent())
            return new ResponseEntity<>(repository.findByNameContainingIgnoreCase(name).get(), HttpStatus.FOUND);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empolyee with name " + name + " not found");
    }

    public ResponseEntity<Employee> update(Employee employee){
        Employee updateEmployee = new Employee();
        updateEmployee.setId(employee.getId());
        if(!employee.getName().isBlank())
            updateEmployee.setName(employee.getName());
        else
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "empolyee name is empty");
        return new ResponseEntity<>(repository.save(employee), HttpStatus.OK);
    }
}