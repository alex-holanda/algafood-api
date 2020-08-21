package com.algaworks.algafood.core.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("logging.loggly")
public class LoggingProperties {

	private String token;
	
}
