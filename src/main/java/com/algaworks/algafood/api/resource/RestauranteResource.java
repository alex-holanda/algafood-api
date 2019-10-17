package com.algaworks.algafood.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteResource {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		
		return ResponseEntity.ok(restauranteRepository.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteRepository.buscar(id);
		
		return restaurante != null ? ResponseEntity.ok(restaurante) : ResponseEntity.notFound().build();
	}
}
