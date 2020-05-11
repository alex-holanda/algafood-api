package com.algaworks.algafood.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;

@Service
public class CadastroUsuarioGrupoService {

	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private CadastroGrupoService grupoService;
	
	public Collection<Grupo> listarGrupos(Long usuarioId) {
		Usuario usuario = usuarioService.buscar(usuarioId);
		
		return usuario.getGrupos();
	}
	
	@Transactional
	public void associar(Long usuarioId, Long grupoId) {
		Usuario usuario = usuarioService.buscar(usuarioId);
		Grupo grupo = grupoService.buscar(grupoId);
		
		usuario.associar(grupo);
	}
	
	@Transactional
	public void desassociar(Long usuarioId, Long grupoId) {
		Usuario usuario = usuarioService.buscar(usuarioId);
		Grupo grupo = grupoService.buscar(grupoId);
		
		usuario.desassociar(grupo);
	}
}
