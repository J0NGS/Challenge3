package br.joao.neto.msProposal.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.joao.neto.msProposal.clients.model.Employee;

@FeignClient(value = "msEmployee", path = "/employees")
public interface EmployeeResource {
    @GetMapping("/getByCpf")
    public ResponseEntity<Employee> getByCpf(@RequestParam("cpf") String cpf);
}
