package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.Notificador;

@Component
public class AtivacaoClienteService {

	@Autowired(required = false)
	private Notificador notificador;
	
//	PONTO DE INJEÇÃO DE DEPENDÊNCIA ATRAVÉS DO CONSTRUTOR
//	@Autowired
//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;
//	}	

	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		if (notificador != null) {
			notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
		} else {
			System.out.println("Não existe notificador, mas cliente foi ativado");
		}
	}
	
//	PONTO DE INJEÇÃO DE DEPENDÊNCIA ATRAVÉS DO SETTER
//	@Autowired
//	public void setNotificador(Notificador notificador) {
//		this.notificador = notificador;
//	}
}
