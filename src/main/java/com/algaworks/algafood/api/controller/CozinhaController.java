package com.algaworks.algafood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
	
	@GetMapping
	public ResponseEntity<List<Cozinha>> listar() {
		
		return ResponseEntity.ok().body(cozinhaRepoistory.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		
//		Optional<Cozinha> cozinha = cozinhaRepoistory.findById(id);
//		return cozinha.isPresent() ? ResponseEntity.ok(cozinha.get()) : ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(cadastroCozinha.buscarOuFalahar(id)); 
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
		
		cozinha = cadastroCozinha.salvar(cozinha);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cozinha.getId()).toUri();
		
		return ResponseEntity.created(uri).body(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalahar(id);
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
		return ResponseEntity.ok(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) { 
		
		cadastroCozinha.excluir(id);
		return ResponseEntity.noContent().build();
	}
}