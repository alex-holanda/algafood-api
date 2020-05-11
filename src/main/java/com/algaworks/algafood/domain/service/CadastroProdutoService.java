package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	public List<Produto> listar(Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		return produtoRepository.findByRestaurante(restaurante);
	}
	
	public Produto buscar(Long restauranteId, Long produtoId) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		return buscarOuFalahr(restaurante, produtoId);
	}
	
	@Transactional
	public Produto salvar(Long restauranteId, Produto produto) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		produto.setRestaurante(restaurante);
		
		return produtoRepository.save(produto);
	}
	
	public Produto atualizar(Long restauranteId, Produto produto) {
		return salvar(restauranteId, produto);
	}
	
	private Produto buscarOuFalahr(Restaurante restaurante, Long produtoId) {
		return produtoRepository.findByRestauranteAndId(restaurante, produtoId)
					.orElseThrow(() -> new ProdutoNaoEncontradoException(restaurante.getId(), produtoId));
	}
}
