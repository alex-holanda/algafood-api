package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.domain.dto.VendaDiaria;
import com.algaworks.algafood.domain.fielter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/v1/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstatisticasController {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public ResponseEntity<EstatisticasModel> estatisticas() {
		
		var estaticasModel = new EstatisticasModel();
		
		estaticasModel.add(algaLinks.linkToVendasDiarias("vendas-diarias"));
		
		return ResponseEntity.ok(estaticasModel);
	}
	
	@GetMapping("/vendas-diarias")
	public ResponseEntity<List<VendaDiaria>> consultarVendasDiarias(VendaDiariaFilter filtro, 
				@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		return ResponseEntity.ok(vendaQueryService.consultarVendasDiarias(filtro, timeOffset));
	}
	
	@GetMapping(path = "/vendas-diarias", produces = { MediaType.APPLICATION_PDF_VALUE })
	public ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filtro, 
				@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_PDF)
					.headers(headers)
					.body(bytesPdf);
	}
	
	private static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
		
	}
}
