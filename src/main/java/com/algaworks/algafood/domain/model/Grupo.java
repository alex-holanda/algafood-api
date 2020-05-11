package com.algaworks.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "grupo")
public class Grupo {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao",
			joinColumns = @JoinColumn(name = "grupo_id"),
			inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	private Set<Permissao> permissoes = new HashSet<>();
	
	public boolean associar(Permissao permissao) {
		return getPermissoes().add(permissao);
	}
	
	public boolean desassociar(Permissao permissao) {
		return getPermissoes().remove(permissao);
	}
}
