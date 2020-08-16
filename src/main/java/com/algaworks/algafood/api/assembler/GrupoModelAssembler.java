package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.algalinks.AlgaLinks;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}
	
	@Override
	public GrupoModel toModel(Grupo grupo) {
		var grupoModel = createModelWithId(grupo.getId(), grupo);
		mapper.map(grupo, grupoModel);
		
		grupoModel.add(algaLinks.linkToGrupos("grupos"));
		grupoModel.add(algaLinks.linkToGrupoPermissoesAssociar(grupo.getId(), "permissoes"));
		
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		return super.toCollectionModel(entities)
					.add(algaLinks.linkToGrupos());
	}
}
