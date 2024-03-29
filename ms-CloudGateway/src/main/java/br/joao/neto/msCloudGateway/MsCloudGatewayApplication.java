package br.joao.neto.msCloudGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCloudGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsCloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder.
			routes()
			.route(r -> r.path("/employees/**").uri("lb://msEmployee"))
			.route(r -> r.path("/proposals/**").uri("lb://msProposal"))
			.route(r -> r.path("/votingSession/VotingSession/**").uri("lb://msVotingSession"))
			.route(r -> r.path("/votingSession/Votes/**").uri("lb://msVotingSession"))	
		.build();
	}
}
