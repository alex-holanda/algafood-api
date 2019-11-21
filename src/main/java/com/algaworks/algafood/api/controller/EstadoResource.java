package com.algaworks.algafood.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		
		return ResponseEntity.ok().body(estadoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		
		Optional<Estado> estado = estadoRepository.findById(id);
		
		return estado.isPresent() ? ResponseEntity.ok(estado.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
		estado = cadastroEstado.salvar(estado);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estado.getId()).toUri();
		
		return ResponseEntity.created(uri).body(estado);
	}
	
	@PutMapping
	public ResponseEntity<Estado> atualizar(@RequestBody Estado estado) {
		Optional<Estado> estadoAtual = estadoRepository.findById(estado.getId());
		
		if (estadoAtual.isPresent()) {
			BeanUtils.copyProperties(estado, estadoAtual.get());
			
			Estado estadoAtualizado = cadastroEstado.salvar(estadoAtual.get());
			
			return ResponseEntity.ok(estadoAtualizado);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> remover(@RequestBody Estado estado) {
		try {
			cadastroEstado.excluir(estado);
			return ResponseEntity.noContent().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
