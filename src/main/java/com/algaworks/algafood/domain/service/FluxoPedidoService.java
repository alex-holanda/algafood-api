package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private CadastroPedidoService pedidoService;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);

		pedido.confirmar();
	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		
		pedido.cancelar();
	}
}
