package com.algaworks.algafood.api.v1.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepoistory;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler disassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourceAssembler;
	
	@GetMapping
	@CheckSecurity.Cozinhas.PodeConsultar
	public ResponseEntity<PagedModel<CozinhaModel>> listar(Pageable pageable) {
		log.info("Consultando cozinhas com páginas de {} registros...", pageable.getPageSize());
		
		Page<Cozinha> cozinhasPage = cozinhaRepoistory.findAll(pageable);

		var cozinhasPagedModel = pagedResourceAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return ResponseEntity.ok().body(cozinhasPagedModel);
	}
	
	@GetMapping("/{id}")
	@CheckSecurity.Cozinhas.PodeConsultar
	public ResponseEntity<CozinhaModel> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalahar(id))); 
	}
	
	@PostMapping
	@CheckSecurity.Cozinhas.PodeEditar
	public ResponseEntity<CozinhaModel> adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cadastroCozinha.salvar(disassembler.toDomainObject(cozinhaInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cozinha.getId()).toUri();
		
		return ResponseEntity.created(uri).body(cozinhaModelAssembler.toModel(cozinha));
	}
	
	@PutMapping("/{id}")
	@CheckSecurity.Cozinhas.PodeEditar
	public ResponseEntity<CozinhaModel> atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalahar(id);
		
		disassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual)));
	}
	
	@DeleteMapping("/{id}")
	@CheckSecurity.Cozinhas.PodeEditar
	public ResponseEntity<Void> remover(@PathVariable Long id) { 
		
		cadastroCozinha.excluir(id);
		return ResponseEntity.noContent().build();
	}
}