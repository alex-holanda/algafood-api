package com.algaworks.algafood.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public ResponseEntity<List<Cidade>> listar() {
		return ResponseEntity.ok(cidadeRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		
		return cidade.isPresent() ? ResponseEntity.ok(cidade.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade) {
		
		cidade = cadastroCidade.salvar(cidade);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cidade.getId()).toUri();
		
		return ResponseEntity.created(uri).body(cidade);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody Cidade cidade) {
		Optional<Cidade> cidadeAtual = cidadeRepository
				.findById(cidade.getId());
		
		if (cidadeAtual.isPresent()) {
			BeanUtils.copyProperties(cidade, cidadeAtual.get());
			
			try {
				Cidade cidadeAtualizada = cadastroCidade.salvar(cidadeAtual.get());
			
				return ResponseEntity.ok(cidadeAtualizada);
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> remover(@RequestBody Cidade cidade) {
		try {
			cadastroCidade.excluir(cidade);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
