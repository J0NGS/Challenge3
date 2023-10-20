package br.joao.neto.msProposal.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "msEmployee", path = "/employees")
public interface EmployeeResource {
    
}
