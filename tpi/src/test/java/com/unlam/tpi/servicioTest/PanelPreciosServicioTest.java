package com.unlam.tpi.servicioTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unlam.tpi.core.modelo.Instrumento;
import com.unlam.tpi.core.modelo.Puntas;
import com.unlam.tpi.core.servicio.PanelesServicioImpl;

@ExtendWith(MockitoExtension.class)
public class PanelPreciosServicioTest {

	@Mock
	private Instrumento instrumentoMock;

	@InjectMocks
	private PanelesServicioImpl panelPrecios;

	@Test
	public void agregarInstrumentosAlPanelDeBonos() {

		List<Instrumento> instrumentos = new ArrayList<>();
		instrumentos.add(instrumentoMock);

		when(instrumentoMock.getSimbolo()).thenReturn("GD30");
		when(instrumentoMock.getPuntas()).thenReturn(mock(Puntas.class));
		when(instrumentoMock.getPuntas().getPrecioCompra()).thenReturn(new BigDecimal(100));
		when(instrumentoMock.getPuntas().getPrecioVenta()).thenReturn(new BigDecimal(105.0));

		panelPrecios.agregarInstrumentosAlPanelDeBonos(instrumentos);

		assertEquals(1, PanelesServicioImpl.panelBonos.size());
		assertEquals(instrumentoMock, PanelesServicioImpl.panelBonos.get("GD30"));
	}

	@Test
	public void agregarInstrumentosAlPanelDeCedears() {

		List<Instrumento> instrumentos = new ArrayList<>();
		instrumentos.add(instrumentoMock);

		when(instrumentoMock.getSimbolo()).thenReturn("AAPL");
		when(instrumentoMock.getPuntas()).thenReturn(mock(Puntas.class));
		when(instrumentoMock.getPuntas().getPrecioCompra()).thenReturn(new BigDecimal(200.0));
		when(instrumentoMock.getPuntas().getPrecioVenta()).thenReturn(new BigDecimal(205.0));

		panelPrecios.agregarInstrumentosAlPanelDeCedears(instrumentos);

		assertEquals(1, PanelesServicioImpl.panelCedears.size());
		assertEquals(instrumentoMock, PanelesServicioImpl.panelCedears.get("AAPL"));
	}

}
