package com.algaworks.algafood.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig {
	
//	@Bean
//	public AtivacaoClienteService ativacaoClienteService(Notificador notificador) {
//		AtivacaoClienteService ativacaoClienteService = new AtivacaoClienteService(notificador);
//		return ativacaoClienteService;
//	}
	
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public AtivacaoClienteService ativacaoClienteService() {
		return new AtivacaoClienteService();
	}
}
