package com.algaworks.algafood.api.v1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public ResponseEntity<CollectionModel<ProdutoModel>> listar(@PathVariable Long restauranteId, 
				@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		
		List<Produto> produtos = produtoService.listar(restauranteId, incluirInativos);
		
		var produtosModel = produtoModelAssembler.toCollectionModel(produtos);
		
		produtosModel.add(algaLinks.linkToRestauranteProduto(restauranteId));
		
		return ResponseEntity.ok(produtosModel);
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<ProdutoModel> buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = produtoService.buscar(restauranteId, produtoId);
		
		return ResponseEntity.ok(produtoModelAssembler.toModel(produto));
	}
	
	@PostMapping
	public ResponseEntity<ProdutoModel> adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = produtoService.salvar(restauranteId, produtoInputDisassembler.toDomainObject(produtoInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand("/{produtoId}").toUri();
		
		return ResponseEntity.created(uri).body(produtoModelAssembler.toModel(produto));
	}
	
	@PutMapping("/{produtoId}")
	public ResponseEntity<ProdutoModel> atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
				@RequestBody @Valid ProdutoInput produtoInput) {
		
		Produto produto = produtoService.buscar(restauranteId, produtoId);
		
		produtoInputDisassembler.copyToDomainObject(produtoInput, produto);
		
		return ResponseEntity.ok(produtoModelAssembler.toModel(produtoService.atualizar(restauranteId, produto)));
	}
}
