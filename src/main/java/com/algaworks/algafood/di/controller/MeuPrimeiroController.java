package com.algaworks.algafood.di.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {

	@Autowired
	private AtivacaoClienteService ativacaoClienteService;
	
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		
		Cliente joao = new Cliente("João", "joao@xzy.com", "11-9990-999");
	
		ativacaoClienteService.ativar(joao);
		
		return "Hello";
	}
}