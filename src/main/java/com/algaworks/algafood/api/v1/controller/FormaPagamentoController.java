package com.algaworks.algafood.api.v1.controller;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoModelAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;
	
	@CheckSecurity.FormasPagamento.PodeConsultar
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = cadastroFormaPagamento.getUltimaDataAtualizacao();
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(assembler.toCollectionModel(cadastroFormaPagamento.listar()));
	}
	
	@CheckSecurity.FormasPagamento.PodeConsultar
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacaoById = cadastroFormaPagamento.getUltimaDataAtualizacaoById(formaPagamentoId);
		if (dataUltimaAtualizacaoById != null) {
			eTag = String.valueOf(dataUltimaAtualizacaoById.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
		
		return ResponseEntity
				.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(assembler.toModel(cadastroFormaPagamento.buscar(formaPagamentoId)));
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar
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
	
	@CheckSecurity.FormasPagamento.PodeEditar
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
	
	@CheckSecurity.FormasPagamento.PodeEditar
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> remover(@PathVariable Long formaPagamentoId) {
		
		cadastroFormaPagamento.excluir(formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
}
