package br.joao.neto.msEmployees.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.joao.neto.msEmployees.client.ProposalResource;
import br.joao.neto.msEmployees.client.VotesResource;
import br.joao.neto.msEmployees.client.model.Proposal;
import br.joao.neto.msEmployees.client.model.Votes;
import br.joao.neto.msEmployees.client.model.VotesResponse;
import br.joao.neto.msEmployees.model.Employee;
import br.joao.neto.msEmployees.model.DTO.EmployeeGetResponse;
import br.joao.neto.msEmployees.repo.EmployeeRepository;

@Service
public class EmployeeService {
    private EmployeeRepository repository;
    private ProposalResource proposalResource;
    private VotesResource votersResource;

    @Autowired
    public EmployeeService(EmployeeRepository repository, ProposalResource proposalResource, VotesResource votersResource) {
        this.repository = repository;
        this.proposalResource = proposalResource;
        this.votersResource = votersResource;
    }

    public ResponseEntity<Employee> create(Employee employee){
        String cpf = employee.getCpf().replaceAll("[^0-9]", "");       
        employee.setCpf(cpf);
        
        if(repository.existsByCpf(employee.getCpf()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "CPF already exists");
        return new ResponseEntity<>(repository.save(employee), HttpStatus.CREATED);
    }

    public ResponseEntity<Employee> findById(UUID id){
        Optional<Employee> employee = repository.findById(id);
        return employee.map(emp -> new ResponseEntity<>(emp, HttpStatus.FOUND))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with id [ " + id + " ] not found"));
    }

    public ResponseEntity<Employee> findByCpf(String cpf){
        Optional<Employee> employee = repository.findBycpf(cpf);
        return employee.map(emp -> new ResponseEntity<>(emp, HttpStatus.FOUND))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with cpf [ " + cpf + " ] not found"));
    }

    public ResponseEntity<Page<EmployeeGetResponse>> findByName(String name, int page, int pageSize) {
        Page<Employee> employees = repository.findByNameContainingIgnoreCase(name, PageRequest.of(page, pageSize));

        if (employees.hasContent()) {
            Page<EmployeeGetResponse> responsePage = employees.map(employee -> new EmployeeGetResponse().toModel(employee));
            return new ResponseEntity<>(responsePage, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employees with name [" + name + "] not found");
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

    public ResponseEntity<Proposal> createProposal(String cpf, String title, String description, Integer timer){
        if(repository.existsByCpf(cpf)){
            Employee employee = repository.findBycpf(cpf).get();
            Proposal proposal = new Proposal(title, description, timer, employee.getId());
            
            ResponseEntity<Proposal> result = proposalResource.create(proposal);
            return result;
        }else { 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with [ "+ cpf +" ] is not found");
        }
    }

    public ResponseEntity<VotesResponse> Voting(String cpf, String title, Boolean vote){
        if(!repository.existsByCpf(cpf))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "employee with [ "+ cpf +" ] is not found");       
        Employee employee = repository.findBycpf(cpf).get();
        Votes votes = new Votes(employee.getId(), proposalResource.getByTitle(title).getBody().getId(), vote);
        return votersResource.createVote(votes);
    }
}