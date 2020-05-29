package com.algaworks.algafood.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepoistory;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler assembler;
	
	@Autowired
	private CozinhaInputDisassembler disassembler;
	
	@GetMapping
	public ResponseEntity<Page<CozinhaModel>> listar(Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepoistory.findAll(pageable);
		List<CozinhaModel> cozinhasModel = assembler.toCollectionModel(cozinhasPage.getContent());
		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
		
		return ResponseEntity.ok().body(cozinhasModelPage);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CozinhaModel> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(assembler.toModel(cadastroCozinha.buscarOuFalahar(id))); 
	}
	
	@PostMapping
	public ResponseEntity<CozinhaModel> adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cadastroCozinha.salvar(disassembler.toDomainObject(cozinhaInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cozinha.getId()).toUri();
		
		return ResponseEntity.created(uri).body(assembler.toModel(cozinha));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CozinhaModel> atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalahar(id);
		
		disassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		return ResponseEntity.ok(assembler.toModel(cadastroCozinha.salvar(cozinhaAtual)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) { 
		
		cadastroCozinha.excluir(id);
		return ResponseEntity.noContent().build();
	}
}