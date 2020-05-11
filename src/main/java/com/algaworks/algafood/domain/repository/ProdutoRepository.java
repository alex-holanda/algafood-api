package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> 		findByRestaurante(Restaurante restaurante);
	
	Optional<Produto>	findByRestauranteAndId(Restaurante restaurante, Long id);
}
