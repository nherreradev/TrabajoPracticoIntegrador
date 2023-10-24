package com.unlam.tpi.servicio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unlam.tpi.interfaces.HistoricoServicio;
import com.unlam.tpi.interfaces.ListaPreciosServicio;
import com.unlam.tpi.modelo.rest.FechaRequestHistorico;
import com.unlam.tpi.repositorio.HistoricoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoricoServicioImpl implements HistoricoServicio {
    @Autowired
    HistoricoRepositorio historicoRepositorio;
    @Autowired
    ListaPreciosServicio listaPreciosServicio;
    @Autowired
    private final RestTemplate restTemplate;
    public HistoricoServicioImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    //1 será un mes,
    //2 serán 3 meses
    //3 serán 6 meses
    @Override
    public String GetHistoricoMongo(String rango, String instrumento) {
        List<String> response = null;
        Integer index = null;
        try{
            switch (rango){
                case "mensual":
                    response = this.historicoRepositorio.GetInstrumentoPorRangoFechaSinId("mensual", instrumento);
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
                case "trimestral":
                    response = this.historicoRepositorio.GetInstrumentoPorRangoFechaSinId("trimestral", instrumento);
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
                case "semestral":
                    response = this.historicoRepositorio.GetInstrumentoPorRangoFechaSinId("semestral", instrumento);
                    index = DeterminarIndexRandomDelArray(response);
                    return response.get(index);
            }
            return null;
        }catch (Exception e){
            System.out.println("Error al obtener información de mongo "+ e);
            e.printStackTrace();
            return null;
        }
    }

    private Integer DeterminarIndexRandomDelArray(List<String> response) {
        return null;
    }

    @Override
    public void GuardarHistorico(FechaRequestHistorico fechaRequestHistorico, String instrumento) {
        String rango = DeterminarRangoDeFecha(fechaRequestHistorico);
        if (rango == null){
            System.out.println("Rango de fecha no válido");
        }
        String historico = ConsultarHistoricoIOL(fechaRequestHistorico, instrumento);

    }

    private String ConsultarHistoricoIOL(FechaRequestHistorico fechaRequestHistorico, String instrumento) {
        List<String> simbolos = ObtenerSimbolosDeInstrumentos(instrumento);
        String mercado = ObtenerMercado(instrumento);
        ResponseEntity<String> res = null;
        if (simbolos.size() == 0) {
            System.out.println("Error al obtener simbolos");
        }else if (mercado == null)  {
            System.out.println("Error al obtener mercado");
        }
        for(int i=0; i<simbolos.size(); i++){
            String url = ArmarURL(fechaRequestHistorico, mercado, simbolos.get(i));
            res = RealizarPeticionIOL(url);
            System.out.println(res.getBody());
        }
        return null;
    }

    private ResponseEntity<String> RealizarPeticionIOL(String url) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJzdWIiOiIxNzU5ODkxIiwiSUQiOiIxNzU5ODkxIiwianRpIjoiMTNhYTIyZWMtNjcwZS00MjY1LWI5MzAtYzIyZGU3NDA4OTJiIiwiY29uc3VtZXJfdHlwZSI6IjEiLCJ0aWVuZV9jdWVudGEiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYnVyc2F0aWwiOiJUcnVlIiwidGllbmVfcHJvZHVjdG9fYXBpIjoiVHJ1ZSIsInRpZW5lX1R5QyI6IlRydWUiLCJuYmYiOjE2OTgxMTM5MTEsImV4cCI6MTY5ODExNDgxMSwiaWF0IjoxNjk4MTEzOTExLCJpc3MiOiJJT0xPYXV0aFNlcnZlciIsImF1ZCI6IklPTE9hdXRoU2VydmVyIn0.jcbJYDitgt3uB91JSDIMixtNFAEXqbyXX86-LTZik9ujykm77jeqaabJyUgYKTlGxhkXxpmLEUfp8o12aHgDcG9k-iRgZ7hKLfz8JV1hElW5ajJ9lmA74tZnSNswcDPPqI_8SQrPV5ERLAIxEIQI7ORiEXKAlnbYjes1PMA5eRKdmkhKeNI53PSSY89QI_0IOmlUxSKKpH9_BiqIi53TSEp0Tx-uNAAHpC-fCgjFoNnoD-uPoPnazPydxd2E3pjLHbGgJ_Dj_J_claY0mbrwZiVgPO2fRQGeAC9tlyKrPyfOyF5fEXlB7qx_tQknhN3LyLxsmsA2dDB504ICobyNRw";
        Map<String, Boolean> ResponseOK = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RequestEntity<?> requestEntity;
        ResponseEntity<String> responseEntity = null;
        requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity;
    }

    private String ArmarURL(FechaRequestHistorico fechaRequestHistorico, String mercado, String simbolo) {
        return "https://api.invertironline.com/api/v2/"+mercado+"/Titulos/"+simbolo+"/Cotizacion/seriehistorica/"+fechaRequestHistorico.getFecha_desde()+"/"+fechaRequestHistorico.getMeses_atras()+"/ajustada";
    }

    private String ObtenerMercado(String instrumento) {
        switch (instrumento){
            case "acciones":
                //SETEO bCBA para hacer pruebas
                return "bCBA";
            case "bonos":
                return "b";
            case "cedears":
                return "c";
        }
        return null;
    }

    private List<String> ObtenerSimbolosDeInstrumentos(String instrumento) {
        List<String> ArraySimbolos = new ArrayList<>();
        String JsonMongo = listaPreciosServicio.GetPriceListMongo(instrumento);
        JsonObject JsonParseado = JsonParser.parseString(JsonMongo).getAsJsonObject();
        JsonArray TituloArray = JsonParseado.getAsJsonArray("titulos");

        for (int i = 0; i < TituloArray.size(); i++) {
            JsonObject titulo = TituloArray.get(i).getAsJsonObject();
            String simbolo = titulo.get("simbolo").getAsString();
            ArraySimbolos.add(simbolo);
        }
        return ArraySimbolos;
    }

    private String DeterminarRangoDeFecha(FechaRequestHistorico fechaRequestHistorico) {
        try {
            Period period = Period.between(fechaRequestHistorico.getFecha_desde(), fechaRequestHistorico.getMeses_atras());
            int mesesDiferencia = Math.abs(period.getMonths());
            switch (mesesDiferencia){
                case 1:
                    return "mensual";
                case 3:
                    return "trimetral";
                case 6:
                    return "semestral";
                default:
                    return null;
            }
        }catch (Exception e){
            System.out.println("Error al determinar rango de fechas "+ e);
            e.printStackTrace();
            return null;
        }
    }
}
