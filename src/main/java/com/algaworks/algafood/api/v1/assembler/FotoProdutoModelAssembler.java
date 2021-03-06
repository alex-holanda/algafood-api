package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public FotoProdutoModelAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
	}

	public FotoProdutoModel toModel(FotoProduto foto) {

//		IMPLEMENTAÇÃO DO CURSO
//		FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);
//		        
//        fotoProdutoModel.add(algaLinks.linkToFotoProduto(
//                foto.getRestauranteId(), foto.getProduto().getId()));

		var fotoProdutoModel = createModelWithId(foto.getId(), foto, foto.getRestauranteId(),
				foto.getProduto().getId());
		mapper.map(foto, fotoProdutoModel);

		if (algaSecurity.podeConsultarRestaurantes()) {
			fotoProdutoModel
					.add(algaLinks.linkToRestauranteProdutoFoto(foto.getRestauranteId(), foto.getProduto().getId()));

			fotoProdutoModel.add(
					algaLinks.linkToRestauranteProduto(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
		}

		return fotoProdutoModel;
	}
}
