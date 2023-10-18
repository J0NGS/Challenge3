package br.joao.neto.msEmployees.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.service.EmployeeService;

@RestController
@RequestMapping("/employees")
class EmployeeController {
    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") UUID id) {
       return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") UUID id, @RequestBody Employee employee) {
        return service.update(employee);
    }
}
