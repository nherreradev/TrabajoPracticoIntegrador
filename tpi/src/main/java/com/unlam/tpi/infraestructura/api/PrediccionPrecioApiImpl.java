package com.unlam.tpi.infraestructura.api;

import java.io.IOException;
import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unlam.tpi.core.interfaces.PrediccionPrecioApi;
import com.unlam.tpi.core.modelo.ServiceException;
import com.unlam.tpi.delivery.dto.PrediccionPrecioDTO;

@Service
public class PrediccionPrecioApiImpl implements PrediccionPrecioApi{

	private RestTemplate restTemplate =null;

	private static ObjectMapper mapper;
	
	@Value("${dolar.prediccion.url}")
	private String dolarUrl;
	
	static {
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
	}
	
	@PostConstruct
	private void initRest() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(mapper));
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setOutputStreaming(false);
		restTemplate.setRequestFactory(requestFactory);
	}
	
	@Override
	public PrediccionPrecioDTO obtenerPrecio() {
		try {
			RequestEntity<Object> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(dolarUrl));
			ResponseEntity<String> response = restTemplate.exchange(dolarUrl, HttpMethod.GET, requestEntity, String.class);

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new ServiceException("Error realizando la consulta a la API Prediccion de doalar");
			}
			PrediccionPrecioDTO prediccionPrecioDTO = (PrediccionPrecioDTO) getMapper()
					.readerFor(PrediccionPrecioDTO.class).readValue(response.getBody());
			return prediccionPrecioDTO;
		} catch (IOException e) {
			throw new ServiceException("Error realizando la consulta a la API Prediccion de dolar", e);
		}catch (Exception e) {
			throw e;
		}
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static void setMapper(ObjectMapper mapper) {
		PrediccionPrecioApiImpl.mapper = mapper;
	}
	
	
}
