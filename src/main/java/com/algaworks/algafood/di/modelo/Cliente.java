package com.algaworks.algafood.di.modelo;

public class Cliente {
	
	private String nome;
	private String email;
	private String telefone;
	private Boolean ativo = false;
	
	public Cliente(String nome, String email, String telefone) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public void ativar() {
		this.ativo = true;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public Boolean getAtivo() {
		return ativo;
	}
}
