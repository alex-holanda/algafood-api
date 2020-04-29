package com.algaworks.algafood.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoModelAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> listar() {
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroFormaPagamento.listar()));
	}
	
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId) {
		
		return ResponseEntity.ok(assembler.toModel(cadastroFormaPagamento.buscar(formaPagamentoId)));
	}
	
	@PostMapping
	public ResponseEntity<FormaPagamentoModel> adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		
		try {
			FormaPagamento formaPagamento = cadastroFormaPagamento
						.salvar(disassembler.toDomainObject(formaPagamentoInput));
			
			URI uri = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{formaPagamentoId}")
						.buildAndExpand(formaPagamento.getId())
						.toUri();
			
			return ResponseEntity.created(uri).body(assembler.toModel(formaPagamento));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> ataulizar(@PathVariable Long formaPagamentoId, 
				@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		
		try {
			FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(formaPagamentoId);
			
			disassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);
			
			return ResponseEntity.ok(assembler.toModel(cadastroFormaPagamento.salvar(formaPagamento)));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> remover(@PathVariable Long formaPagamentoId) {
		
		cadastroFormaPagamento.excluir(formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
}
