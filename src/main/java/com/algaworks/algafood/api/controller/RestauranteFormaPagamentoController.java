package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoAssembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> listar(@PathVariable Long restauranteId) {

		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return ResponseEntity.ok(formaPagamentoAssembler.toCollectionModel(restaurante.getFormasPagamento()));
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		
		cadastroRestaurante.desassociarFormaDePagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		
		cadastroRestaurante.associarFormaDePagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
}