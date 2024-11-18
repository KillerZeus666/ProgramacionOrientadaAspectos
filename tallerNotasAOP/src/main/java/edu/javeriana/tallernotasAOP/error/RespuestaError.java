package edu.javeriana.tallernotasAOP.error;

import java.util.List;

public class RespuestaError {

    private String mensaje;
    private List<String> detalles;

    // Constructor
    public RespuestaError(String mensaje, List<String> detalles) {
        this.mensaje = mensaje;
        this.detalles = detalles;
    }

    // Getters y setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<String> detalles) {
        this.detalles = detalles;
    }
}
