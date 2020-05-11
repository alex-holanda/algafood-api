package com.algaworks.algafood.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;

@Service
public class CadastroRestauranteUsuarioService {

	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	public Collection<Usuario> responsaveis(Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		return restaurante.getResponsaveis();
	}
	
	@Transactional
	public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscar(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void removerResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscar(usuarioId);
		
		restaurante.removerResponsavel(usuario);
	}
}
