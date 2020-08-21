package com.algaworks.algafood.api.v1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteModelAssembler assembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler RestauranteApenasNomeModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler disassembler;
	
	
	@GetMapping
	public ResponseEntity<CollectionModel<RestauranteBasicoModel>> listar() {

		return ResponseEntity.ok(restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll()));
	}
	
	@GetMapping(params = "projecao=apenas-nome")
	public ResponseEntity<CollectionModel<RestauranteApenasNomeModel>> listarApenasNome() {

		return ResponseEntity.ok(RestauranteApenasNomeModelAssembler
					.toCollectionModel(restauranteRepository.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestauranteModel> buscar(@PathVariable Long id) {

		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id); 
		
		RestauranteModel restauranteModel = assembler.toModel(restaurante);
		
		return ResponseEntity.ok(restauranteModel);
	}

	@PostMapping
	public ResponseEntity<RestauranteModel> adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

		try {
			Restaurante restaurante = cadastroRestaurante.salvar(disassembler.toDomainObject(restauranteInput));
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(restaurante.getId())
					.toUri();
			
			return ResponseEntity.created(uri).body(assembler.toModel(restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestauranteModel> atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {

		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
	
			disassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return ResponseEntity.ok(assembler.toModel(cadastroRestaurante.salvar(restauranteAtual)));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}/ativo")
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ativacoes")
	public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		
		cadastroRestaurante.ativar(restauranteIds);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/ativacoes")
	public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		
		cadastroRestaurante.inativar(restauranteIds);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{restauranteId}/abertura")
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		
		cadastroRestaurante.abrir(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		
		cadastroRestaurante.fechar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
}
