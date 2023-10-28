package com.unlam.tpi.servicioTest;

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

import com.unlam.tpi.core.interfaces.SeccionServicio;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SeccionControladorTest {

	@Autowired
	private SeccionServicio seccionServicio;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void cuandoCargoUnExcel_VerificoSuEstado() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("excelSeccion", "pregunta.xls",
				"application/x-xlsx", new ClassPathResource("pregunta.xlsx").getInputStream());
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/api/seccion/carga-seccion-excel").file(mockMultipartFile))
				.andExpect(status().isOk());
	}

	public SeccionServicio getSeccionServicio() {
		return seccionServicio;
	}

	public void setSeccionServicio(SeccionServicio seccionServicio) {
		this.seccionServicio = seccionServicio;
	}
}