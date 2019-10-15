package com.algaworks.algafood.api.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaResource {

	@Autowired
	private CozinhaRepository cozinhaRepoistory;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public ResponseEntity<List<Cozinha>> listar() {
		
		return ResponseEntity.ok().body(cozinhaRepoistory.listar());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<CozinhasXmlWrapper> listarXml() {
		
		return ResponseEntity.ok().body(new CozinhasXmlWrapper(cozinhaRepoistory.listar()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		
		Cozinha cozinha = cozinhaRepoistory.buscar(id);
		
		return cozinha != null ? ResponseEntity.ok(cozinha) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
		
		cozinha = cadastroCozinha.salvar(cozinha);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(cozinha.getId()).toUri();
		
		return ResponseEntity.created(uri).body(cozinha);
	}
	
	@PutMapping
	public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha) {
		
		Cozinha cozinhaAtual = cozinhaRepoistory.buscar(cozinha.getId());
		
		if (cozinhaAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual);
		
		cozinhaRepoistory.salvar(cozinhaAtual);
		
		return ResponseEntity.ok(cozinhaAtual);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> remover(@RequestBody Cozinha cozinha) { 
		
		try {
			cozinhaRepoistory.remover(cozinha);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
