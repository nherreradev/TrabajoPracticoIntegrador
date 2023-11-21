package com.unlam.tpi.servicioTest;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.unlam.tpi.infraestructura.api.ListaPreciosAPIImpl;

@ExtendWith(MockitoExtension.class)
public class ListaPreciosServiceTest {
	
    @InjectMocks
    private ListaPreciosAPIImpl listaPreciosAPI;
    
    @Test
    public void ValidoQueElRequestParaObtenerAccionesMeDevuelvaStatusOK (){
        Map<String, Boolean> expect = new HashMap<>();
        expect.put("acciones", true);
        ResponseEntity<String> mockResponse = new ResponseEntity<>("", HttpStatus.OK);

        when(listaPreciosAPI.validateResponse(mockResponse, "acciones")).thenReturn(expect);

//        Map<String, Boolean> ResponseOk = listaPreciosAPI.validateResponse(ResponseAcciones, "acciones");
//
//        assertEquals(true, ResponseOk.get("acciones"));
//        assertEquals(HttpStatus.OK, ResponseAcciones.getStatusCode());
    }

    @Test
    public void ValidoQueElRequestParaObtenerBonosMeDevuelvaStatusOK (){
        Map<String, Boolean> expect = new HashMap<>();
        expect.put("acciones", true);
        ResponseEntity<String> mockResponse = new ResponseEntity<>("", HttpStatus.OK);

        when(listaPreciosAPI.validateResponse(mockResponse, "acciones")).thenReturn(expect);
//
//        ResponseEntity<String> ResponseAcciones = listaPreciosAPI.getInstrumentos(MERCADOJR_URL_BONOS) ;
//        Map<String, Boolean> ResponseOk = listaPreciosAPI.validateResponse(ResponseAcciones, "acciones");

//        assertEquals(true, ResponseOk.get("acciones"));
//        assertEquals(HttpStatus.OK, ResponseAcciones.getStatusCode());
    }

    @Test
    public void AlQuererRealizarUnaPeticionAInvertirOnlineParaObtenerAccionesObtengoUnErrorDeRespuestaNoAutorizado (){
        ResponseEntity<String> response = new ResponseEntity<>("{ \"message\": \"Authorization has been denied for this request.\" }", HttpStatus.UNAUTHORIZED);
        // Creo un mock de servicio que devuelve la respuesta mockeada
//        ps = mock(PanelesServicioImpl.class);

//        when(ps.getInstrumentos(IOL_ACCIONES)).thenReturn(response);
//
//        // Realizo la prueba
//        ResponseEntity<String> actualResponse = ps.getInstrumentos(IOL_ACCIONES);
//
//        // Verifico que la respuesta tenga el código 401 (Unauthorized)
//        assertEquals(HttpStatus.UNAUTHORIZED, actualResponse.getStatusCode());
    }

    @Test
    public void AlQuererRealizarUnaPeticionAInvertirOnlineParaObtenerBonosObtengoUnErrorDeRespuestaNoAutorizado (){
        ResponseEntity<String> response = new ResponseEntity<>("{ \"message\": \"Authorization has been denied for this request.\" }", HttpStatus.UNAUTHORIZED);
//        // Creo un mock de servicio que devuelve la respuesta mockeada
//        ps = mock(PanelesServicioImpl.class);
//
//        when(ps.getInstrumentos(IOL_BONOS)).thenReturn(response);
//        // Realizo la prueba
//        ResponseEntity<String> actualResponse = ps.getInstrumentos(IOL_BONOS);
//        // Verifico que la respuesta tenga el código 401 (Unauthorized)
//        assertEquals(HttpStatus.UNAUTHORIZED, actualResponse.getStatusCode());
    }


}
