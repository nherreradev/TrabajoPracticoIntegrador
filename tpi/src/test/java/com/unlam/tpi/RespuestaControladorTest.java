package com.unlam.tpi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.unlam.tpi.core.servicio.CategoriaServicio;
import com.unlam.tpi.core.servicio.PreguntaServicio;
import com.unlam.tpi.core.servicio.RespuestaServicio;
import com.unlam.tpi.core.servicio.SeccionServicio;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RespuestaControladorTest {

	@Autowired
	private RespuestaServicio respuestaServicio;
	
	@Autowired
	private PreguntaServicio preguntaServicio;
	
	@Autowired
	private CategoriaServicio categoriaServicio;
	
	@Autowired
	private SeccionServicio seccionServicio;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void cuandoCargoUnExcel_VerificoSuEstado() throws Exception {
		givenSeCreaLacategoria();
		givenSeCreaLaSeccion();
		givenSeCreaLaPregunta();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("excelRespuesta", "pregunta.xls",
				"application/x-xlsx", new ClassPathResource("pregunta.xlsx").getInputStream());
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/api/respuesta/carga-respuesta-excel").file(mockMultipartFile))
				.andExpect(status().isOk());
	}
	
	private void givenSeCreaLacategoria() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("excelCategoria", "pregunta.xls",
				"application/x-xlsx", new ClassPathResource("pregunta.xlsx").getInputStream());
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/api/categoria/carga-categoria-excel").file(mockMultipartFile))
				.andExpect(status().isOk());
	}
	
	private void givenSeCreaLaSeccion() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("excelSeccion", "pregunta.xls",
				"application/x-xlsx", new ClassPathResource("pregunta.xlsx").getInputStream());
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/api/seccion/carga-seccion-excel").file(mockMultipartFile))
				.andExpect(status().isOk());
	}
	
	private void givenSeCreaLaPregunta() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("excelPregunta", "pregunta.xls",
				"application/x-xlsx", new ClassPathResource("pregunta.xlsx").getInputStream());
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/api/pregunta/carga-pregunta-excel").file(mockMultipartFile))
				.andExpect(status().isOk());
	}

	public RespuestaServicio getRespuestaServicio() {
		return respuestaServicio;
	}

	public void setRespuestaServicio(RespuestaServicio respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}

	public PreguntaServicio getPreguntaServicio() {
		return preguntaServicio;
	}

	public void setPreguntaServicio(PreguntaServicio preguntaServicio) {
		this.preguntaServicio = preguntaServicio;
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

	public SeccionServicio getSeccionServicio() {
		return seccionServicio;
	}

	public void setSeccionServicio(SeccionServicio seccionServicio) {
		this.seccionServicio = seccionServicio;
	}

}