package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

	@PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Void> atualizarFoto(@PathVariable Long restauranteId, 
				@PathVariable Long produtoId, 
				@Valid FotoProdutoInput fotoProdutoInput) {
		
		System.out.println("Descrição: " + fotoProdutoInput.getDescricao());
		System.out.println("Nome do arquivo: " + fotoProdutoInput.getArquivo().getOriginalFilename());
		
		return ResponseEntity.ok().build();
	}
}
