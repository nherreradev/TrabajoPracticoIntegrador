package com.unlam.tpi.servicioTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.unlam.tpi.core.interfaces.HistoricoPrecioAPI;
import com.unlam.tpi.core.interfaces.ListaPreciosIOL;
import com.unlam.tpi.core.modelo.FechaRequestHistorico;
import com.unlam.tpi.core.servicio.HistoricoServicioImpl;


@ExtendWith(MockitoExtension.class)
public class HistoricoServicioTest {

	@InjectMocks
	private HistoricoServicioImpl historicoServicio;
	
	@Mock
	private HistoricoPrecioAPI historicoPrecioAPI;

	@Mock
	private ListaPreciosIOL listaPreciosIOl;
	
    @Mock
    private RestTemplate restTemplate;

    String JSON = "{\"titulos\": [{\"simbolo\":\"5913\",\"puntas\":null,\"ultimoPrecio\":0.0,\"variacionPorcentual\":0.0,\"apertura\":0.0,\"maximo\":0.0,\"minimo\":0.0,\"ultimoCierre\":0.0,\"volumen\":0.0,\"cantidadOperaciones\":0.0,\"fecha\":\"2023-09-21T03:00:00.87\",\"tipoOpcion\":null,\"precioEjercicio\":null,\"fechaVencimiento\":null,\"mercado\":\"1\",\"moneda\":\"1\",\"descripcion\":\"OCCIDENTAL PETROLEUM CORP. (OXY)\",\"plazo\":\"T2\",\"laminaMinima\":1,\"lote\":1}]}";
    		
	@Test
	void testGuardarHistorico() {
		FechaRequestHistorico fechaRequestHistorico = new FechaRequestHistorico();
		fechaRequestHistorico.setFechaDesde(LocalDate.now());
		fechaRequestHistorico.setInstrumento("Acciones");
		historicoServicio.guardarHistorico(fechaRequestHistorico, "Acciones");
		verify(historicoServicio, times(1)).guardarHistorico(eq(fechaRequestHistorico), any());
	}

	
}