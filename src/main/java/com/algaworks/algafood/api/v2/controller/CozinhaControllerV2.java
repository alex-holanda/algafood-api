package com.algaworks.algafood.api.v2.controller;

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

import com.algaworks.algafood.api.v2.assembler.CozinhaModelAssemblerV2;
import com.algaworks.algafood.api.v2.disassembler.CozinhaInputDisassemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 {

	@Autowired
	private CozinhaRepository cozinhaRepoistory;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssemblerV2 cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassemblerV2 disassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourceAssembler;
	
	@GetMapping
	public ResponseEntity<PagedModel<CozinhaModelV2>> listar(Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepoistory.findAll(pageable);

		var cozinhasPagedModel = pagedResourceAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return ResponseEntity.ok().body(cozinhasPagedModel);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CozinhaModelV2> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalahar(id))); 
	}
	
	@PostMapping
	public ResponseEntity<CozinhaModelV2> adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
		
		Cozinha cozinha = cadastroCozinha.salvar(disassembler.toDomainObject(cozinhaInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cozinha.getId()).toUri();
		
		return ResponseEntity.created(uri).body(cozinhaModelAssembler.toModel(cozinha));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CozinhaModelV2> atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalahar(id);
		
		disassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) { 
		
		cadastroCozinha.excluir(id);
		return ResponseEntity.noContent().build();
	}
}