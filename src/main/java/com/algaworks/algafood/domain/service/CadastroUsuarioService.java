package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	public Usuario buscar(Long id) {
		return buscarOuFalhar(id);
	}
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		if (usuario.isNovo()) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario atualizar(Usuario usuario) {
		return salvar(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
		
		Usuario usuario = buscarOuFalhar(id);
		
		if (!(new BCryptPasswordEncoder().matches(senhaAtual, usuario.getSenha()))) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
	}
	
	private Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
}
