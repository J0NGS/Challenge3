package br.joao.neto.msEmployees.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.joao.neto.msEmployees.client.model.Proposal;

@FeignClient(value = "msProposal", path = "/proposals")
public interface ProposalResource {
    @PostMapping()
    public ResponseEntity<Proposal> create(@RequestBody Proposal proposal);
}
