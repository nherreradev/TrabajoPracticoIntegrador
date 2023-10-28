package com.unlam.tpi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class SeccionControladorTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void cuandoCargoUnExcelVerificoSuEstado() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("excelSeccion", "pregunta.xls",
				"application/x-xlsx", new ClassPathResource("pregunta.xlsx").getInputStream());
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/api/seccion/carga-seccion-excel").file(mockMultipartFile))
				.andExpect(status().isOk());
	}
}