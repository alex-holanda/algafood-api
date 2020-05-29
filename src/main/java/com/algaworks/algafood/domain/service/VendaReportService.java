package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.fielter.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
