package br.joao.neto.msEmployees.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.joao.neto.msEmployees.client.model.Proposal;
import br.joao.neto.msEmployees.client.model.Votes;
import br.joao.neto.msEmployees.client.model.VotesResponse;
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
    public ResponseEntity<List<Employee>> getAll(@RequestParam("page") int page, @RequestParam("quantity") int qtd) {
       return service.findAll(page, qtd);
    }

    @GetMapping("/getByCpf")
    public ResponseEntity<Employee> getByCpf(@RequestParam("cpf") String cpf) {
       return service.findByCpf(cpf);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Page<EmployeeGetResponse>> getByName(
        @RequestParam("name") String name,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return service.findByName(name, page, pageSize);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PostMapping("/proposal")
    public ResponseEntity<Proposal> createProposal(@RequestParam("cpf") String cpf, @RequestBody Proposal proposal) {
        return service.createProposal(cpf, proposal.getTitle(), proposal.getDescription(), proposal.getTimer());
    }


    @PostMapping("/vote")
    public ResponseEntity<VotesResponse> createVote(@RequestParam("cpf") String cpf, @RequestParam("title") String title, @RequestParam("vote") Boolean vote) {
        return service.Voting(cpf, title, vote);
    }

    @PutMapping("{id}/{name}")
    public ResponseEntity<EmployeeGetResponse> update(@PathVariable("id") UUID id, @PathVariable("name") String name) {
        return service.update(id, name);
    }
}
