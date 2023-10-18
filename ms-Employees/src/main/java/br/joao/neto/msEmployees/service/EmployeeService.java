package br.joao.neto.msEmployees.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public Employee create(Employee employee){
        return repository.save(employee);
    }

    public Employee findById(UUID id){
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent())
            return employee.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empolyee with id " + id + " not found");
    }

    public Employee findByCpf(String cpf){
        Optional<Employee> employee = repository.findBycpf(cpf);
        if(employee.isPresent())
            return employee.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empolyee with cpf " + cpf + " not found");
    }

    public Employee findByName(String name){
        Optional<Employee> employee = repository.findBynameContainsAllIgnoringCase(name);
        if(employee.isPresent())
            return employee.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empolyee with name " + name + " not found");
    }
}