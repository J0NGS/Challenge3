package br.joao.neto.msProposal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsProposalApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsProposalApplication.class, args);
	}
	
}
