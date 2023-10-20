package br.joao.neto.msEmployees.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.model.DTO.EmployeeGetResponse;
import br.joao.neto.msEmployees.service.EmployeeService;

@RestController
@RequestMapping("employees")
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

    @GetMapping
    public ResponseEntity<List<Employee>> getByName(@RequestParam("page") int page, @RequestParam("quantity") int qtd) {
       return service.findAll(page, qtd);
    }

    @GetMapping
    public ResponseEntity<Employee> getByCpf(@RequestParam("cpf") String cpf) {
       return service.findByCpf(cpf);
    }

    @GetMapping("/getByName")
    public ResponseEntity<List<EmployeeGetResponse>> getByName(@RequestParam("name") String name) {
       return service.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PutMapping("{id}/{name}")
    public ResponseEntity<EmployeeGetResponse> update(@PathVariable("id") UUID id, @PathVariable("name") String name) {
        return service.update(id, name);
    }
}
