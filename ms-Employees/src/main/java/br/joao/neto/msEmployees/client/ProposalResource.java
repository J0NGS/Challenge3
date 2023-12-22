package br.joao.neto.msEmployees.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.joao.neto.msEmployees.client.model.Proposal;

@FeignClient(value = "msProposal", path = "proposals")
public interface ProposalResource {
    @PostMapping()
    public ResponseEntity<Proposal> create(@RequestBody Proposal proposal);

    @GetMapping("/title")
    public ResponseEntity<Proposal> getByTitle(@RequestParam("title") String title);
}
