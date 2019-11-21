package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteResource {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		
		return ResponseEntity.ok(restauranteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		
		return restaurante.isPresent() ? ResponseEntity.ok(restaurante.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
		
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(restaurante.getId()).toUri();
		
			return ResponseEntity.created(uri).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
			
		try {
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restaurante.getId());
			
			if (restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
			
				Restaurante restauranteAtualizado = cadastroRestaurante.salvar(restauranteAtual.get());
				
				return ResponseEntity.ok(restauranteAtualizado);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualiarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);
		
		if (restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual.get());
		
		return atualizar(id, restauranteAtual.get());
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach( (propriedade, valor) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, propriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
