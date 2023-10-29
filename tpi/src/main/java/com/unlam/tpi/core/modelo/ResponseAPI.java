package com.unlam.tpi.core.modelo;

import org.springframework.http.HttpStatus;

public class ResponseAPI {
    public String titulo;
    public String detalle;
    public HttpStatus status;

    public ResponseAPI(String titulo, String detalle, HttpStatus status){
        this.titulo = titulo;
        this.detalle = detalle;
        this.status = status;
    }

    public ResponseAPI(){}


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public ResponseAPI MensajeDeExito(){
        return new ResponseAPI("Solicitud éxitosa", "La solicitud se ha procesado con éxito.", HttpStatus.OK);
    }

    public ResponseAPI MensajeDeErrorEnRequest(){
        return new ResponseAPI("Solicitud incorrecta", "La solicitud no pudo ser procesada debido a datos incorrectos o incompletos.", HttpStatus.BAD_REQUEST);
    }

    public ResponseAPI MensajeDeErrorRecursoNoEncontrado(){
        return new ResponseAPI("Recurso no encontrado", "El recurso solicitado no se encontró en el servidor.", HttpStatus.NOT_FOUND);
    }

    public ResponseAPI RecursoYaExistente(){
        return new ResponseAPI("Recurso ya existente", "Ya existe un recurso con los datos brindados.", HttpStatus.CONFLICT);
    }

    public ResponseAPI MensajeDeErrorInterno(){
        return new ResponseAPI("Error interno del servidor", "Se ha producido un error interno en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
