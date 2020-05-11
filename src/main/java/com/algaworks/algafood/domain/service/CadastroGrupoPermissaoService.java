package com.algaworks.algafood.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;

@Service
public class CadastroGrupoPermissaoService {

	@Autowired
	private CadastroGrupoService grupoService;
	
	@Autowired
	private CadastroPermissaoService permissaoService;
	
	public Collection<Permissao> listar(Long grupoId) {
		Grupo grupo = grupoService.buscar(grupoId);
		
		return grupo.getPermissoes();
	}
	
	@Transactional
	public void associar(Long grupoId, Long permissaoId) {
		Grupo grupo = grupoService.buscar(grupoId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		
		grupo.associar(permissao);
	}
	
	@Transactional
	public void desassociar(Long grupoId, Long permissaoId) {
		Grupo grupo = grupoService.buscar(grupoId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		
		grupo.desassociar(permissao);
	}
}
