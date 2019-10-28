package com.algaworks.algafood.api.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;

@RestController
@RequestMapping("/teste")
public class TesteController {

//	@Autowired
//	private CozinhaRepository cozinhaRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public ResponseEntity<List<Cozinha>> cozinhasPorNome(@RequestParam String nome) {
		
		List<Cozinha> cozinhas = null;
//				cozinhaRepository.consultarPorNome(nome);
		
		return ResponseEntity.ok(cozinhas);
	}
}
