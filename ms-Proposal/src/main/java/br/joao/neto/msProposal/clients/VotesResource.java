package br.joao.neto.msProposal.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "msVotes", path = "/voting")
public interface VotesResource {
    
}
