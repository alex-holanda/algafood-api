package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.algalinks.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public ProdutoModelAssembler() {
		super(RestauranteProdutoController.class, ProdutoModel.class);
	}
	
	public ProdutoModel toModel(Produto produto) {
		var restauranteId = produto.getRestaurante().getId();
		var produtoModel = createModelWithId(produto.getId(), produto, restauranteId);
		mapper.map(produto, produtoModel);
		
		produtoModel.add(algaLinks.linkToRestauranteProduto(restauranteId, "produtos"));
		produtoModel.add(algaLinks.linkToRestauranteProdutoFoto(restauranteId, produtoModel.getId(), "foto"));
		
		return produtoModel;
	}
}
