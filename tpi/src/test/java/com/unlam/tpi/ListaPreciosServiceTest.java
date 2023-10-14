package com.unlam.tpi;

import com.unlam.tpi.servicio.ListaPreciosServicioImpl;
import com.unlam.tpi.servicio.PanelesServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ListaPreciosServiceTest {
    @Mock
    private final RestTemplate restTemplate = new RestTemplate();
    private ListaPreciosServicioImpl lp = new ListaPreciosServicioImpl(restTemplate);
    @InjectMocks
    private PanelesServiceImpl ps = new PanelesServiceImpl();
    private static final String MERCADOJR_URL_ACCIONES = "https://api.mercadojunior.com.ar/list/precios/acciones";
    private static final String MERCADOJR_URL_BONOS = "https://api.mercadojunior.com.ar/list/precios/bonos";
    private static final String IOL_ACCIONES = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=acciones&cotizacionInstrumentoModel.pais=argentina";
    private static final String IOL_BONOS = "https://api.invertironline.com/api/v2/Cotizaciones/todos/argentina/Todos?cotizacionInstrumentoModel.instrumento=titulosPublicos&cotizacionInstrumentoModel.pais=argentina";

    @Test
    public void ValidoQueElRequestParaObtenerAccionesMeDevuelvaStatusOK (){
        Map<String, Boolean> expect = new HashMap<>();
        expect.put("acciones", true);
        ResponseEntity<String> mockResponse = new ResponseEntity<>("", HttpStatus.OK);

        lp = mock(ListaPreciosServicioImpl.class);
        ps = mock(PanelesServiceImpl.class);
        when(ps.getInstrumentos(MERCADOJR_URL_ACCIONES)).thenReturn(mockResponse);
        when(lp.ValidateResponse(mockResponse, "acciones")).thenReturn(expect);

        ResponseEntity<String> ResponseAcciones = ps.getInstrumentos(MERCADOJR_URL_ACCIONES) ;
        Map<String, Boolean> ResponseOk = lp.ValidateResponse(ResponseAcciones, "acciones");

        assertEquals(true, ResponseOk.get("acciones"));
        assertEquals(HttpStatus.OK, ResponseAcciones.getStatusCode());
    }

    @Test
    public void ValidoQueElRequestParaObtenerBonosMeDevuelvaStatusOK (){
        Map<String, Boolean> expect = new HashMap<>();
        expect.put("acciones", true);
        ResponseEntity<String> mockResponse = new ResponseEntity<>("", HttpStatus.OK);

        lp = mock(ListaPreciosServicioImpl.class);
        PanelesServiceImpl ps = mock(PanelesServiceImpl.class);
        when(ps.getInstrumentos(MERCADOJR_URL_BONOS)).thenReturn(mockResponse);
        when(lp.ValidateResponse(mockResponse, "acciones")).thenReturn(expect);

        ResponseEntity<String> ResponseAcciones = ps.getInstrumentos(MERCADOJR_URL_BONOS) ;
        Map<String, Boolean> ResponseOk = lp.ValidateResponse(ResponseAcciones, "acciones");

        assertEquals(true, ResponseOk.get("acciones"));
        assertEquals(HttpStatus.OK, ResponseAcciones.getStatusCode());
    }

    @Test
    public void AlQuererRealizarUnaPeticionAInvertirOnlineParaObtenerAccionesObtengoUnErrorDeRespuestaNoAutorizado (){
        ResponseEntity<String> response = new ResponseEntity<>("{ \"message\": \"Authorization has been denied for this request.\" }", HttpStatus.UNAUTHORIZED);

        // Creo un mock de servicio que devuelve la respuesta mockeada
        ps = mock(PanelesServiceImpl.class);
        when(ps.getInstrumentos(IOL_ACCIONES)).thenReturn(response);

        // Realizo la prueba
        ResponseEntity<String> actualResponse = ps.getInstrumentos(IOL_ACCIONES);

        // Verifico que la respuesta tenga el código 401 (Unauthorized)
        assertEquals(HttpStatus.UNAUTHORIZED, actualResponse.getStatusCode());
    }

    @Test
    public void AlQuererRealizarUnaPeticionAInvertirOnlineParaObtenerBonosObtengoUnErrorDeRespuestaNoAutorizado (){
        ResponseEntity<String> response = new ResponseEntity<>("{ \"message\": \"Authorization has been denied for this request.\" }", HttpStatus.UNAUTHORIZED);

        // Creo un mock de servicio que devuelve la respuesta mockeada
        ps = mock(PanelesServiceImpl.class);
        when(ps.getInstrumentos(IOL_BONOS)).thenReturn(response);

        // Realizo la prueba
        ResponseEntity<String> actualResponse = ps.getInstrumentos(IOL_BONOS);

        // Verifico que la respuesta tenga el código 401 (Unauthorized)
        assertEquals(HttpStatus.UNAUTHORIZED, actualResponse.getStatusCode());
    }


}
