package com.algaworks.algafood.di.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.service.ClienteAtivadoEvent;

@Component
public class EmissaoNotaFiscalService {

	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent event) {
		System.out.println("EMITITNDO NOTA FISCAL");
		System.out.println("Nota fiscal criada para " + event.getCliente().getNome());
	}
}
