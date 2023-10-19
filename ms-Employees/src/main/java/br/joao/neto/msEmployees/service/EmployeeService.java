package br.joao.neto.msEmployees.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.model.DTO.EmployeeGetResponse;
import br.joao.neto.msEmployees.repo.EmployeeRepository;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Employee> create(Employee employee){
        if(repository.existsByCpf(employee.getCpf()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "CPF already exists");
        return new ResponseEntity<>(repository.save(employee), HttpStatus.CREATED);
    }

    public ResponseEntity<Employee> findById(UUID id){
        Optional<Employee> employee = repository.findById(id);
        return employee.map(emp -> new ResponseEntity<>(emp, HttpStatus.FOUND))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with id [ " + id + " ] not found"));
    }

    public ResponseEntity<EmployeeGetResponse> findByCpf(String cpf){
        Optional<Employee> employee = repository.findBycpf(cpf);
        EmployeeGetResponse response = new EmployeeGetResponse();
        return employee.map(emp -> new ResponseEntity<>(response.toModel(emp), HttpStatus.FOUND))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with cpf [ " + cpf + " ] not found"));
    }

    public ResponseEntity<List<EmployeeGetResponse>> findByName(String name){
        Optional<List<Employee>> employee = repository.findByNameIgnoreCase(name);
        EmployeeGetResponse response = new EmployeeGetResponse();
        if (employee.isPresent()){
            List<EmployeeGetResponse> list = new ArrayList<>();
            for (Employee emp : employee.get()) {
                list.add(response.toModel(emp));
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with name [ " + name + " ] not found");
        }
    }

    public ResponseEntity<List<Employee>> findAll(int page, int quantity){
        Pageable pageable = PageRequest.of(page, quantity);
        Page<Employee> response = repository.findAll(pageable);

        List<Employee> list = new ArrayList<>();

        list = response.getContent();

        if(response.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page is empty");
        else 
            return new ResponseEntity<>(list, HttpStatus.OK);
    }   


    public ResponseEntity<EmployeeGetResponse> update(UUID id, String name){
        Employee updateEmployee = findById(id).getBody();
        if (!name.isBlank()) {
            updateEmployee.setName(name);
            repository.save(updateEmployee);
            EmployeeGetResponse response = new EmployeeGetResponse();
            return new ResponseEntity<>(response.toModel(updateEmployee), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "name is empty");
        }
    }
}