package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;

//@Component
public class AtivacaoClienteService {

	@TipoDoNotificador(NivelUrgencia.URGENTE)
	@Autowired
	private Notificador notificador;
	
//	PONTO DE INJEÇÃO DE DEPENDÊNCIA ATRAVÉS DO CONSTRUTOR
//	@Autowired
//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;
//	}	

//	INICIA APÓS O CONSTRUTOR
//	@PostConstruct
	public void init() {
		System.out.println("INID");
	}
	
//	@PreDestroy
	public void destroy() {
		System.out.println("DESTROY");
	}
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
	}
	
//	PONTO DE INJEÇÃO DE DEPENDÊNCIA ATRAVÉS DO SETTER
//	@Autowired
//	public void setNotificador(Notificador notificador) {
//		this.notificador = notificador;
//	}
}
