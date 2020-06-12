package com.algaworks.algafood.infrastructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;
	
//	PASSANDO O VALOR DA PROPRIEDADE POR VALUE -> DE FORMA EXTERNA
//	@Value("${algafood.storage.local.diretorio}")
//	PASSANDO O VALOR NA PRÓPRIA CLASSE
//	private Path diretorioFotos = Path.of("C:/AppStore/").toAbsolutePath().normalize();
	
	@Override
		public FotoRecuperada recuperar(String nomeArquivo) {
			
			try {
				Path arquivoPath = getArquivoPath(nomeArquivo);
				
				var fotoRecuperada = FotoRecuperada.builder()
						.inputStream(Files.newInputStream(arquivoPath)).build();
				
				return fotoRecuperada;
			} catch (IOException e) {
				throw new StorageException("Não foi possível recuperar o arquivo.", e); 
			}
		}
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (IOException e) {
			throw new StorageException("Não foi possível armazenar o arquivo.", e);
		}
	}
	
	@Override
	public void remover(String nomeArquivo) {
		
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(arquivoPath);
		} catch (IOException e) {
			throw new StorageException("Não foi possível excluir o arquivo.", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		
		return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}

}
