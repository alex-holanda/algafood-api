package com.algaworks.algafood.core.jackson;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

//Não está sendo mais utilizado
//@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
//		gen.writeStartObject();
//		
//		gen.writeObjectField("content", page.getContent());
//		gen.writeNumberField("size", page.getSize());
//		gen.writeNumberField("totalElements", page.getNumberOfElements());
//		gen.writeNumberField("totalPages", page.getTotalPages());
//		gen.writeNumberField("number", page.getNumber());
//		
//		gen.writeEndObject();
	}

	
}
