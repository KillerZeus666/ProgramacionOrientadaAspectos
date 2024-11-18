package edu.javeriana.tallernotasAOP.aspecto;

import edu.javeriana.tallernotasAOP.error.RespuestaError;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ManejoExcepcionesAspecto {

    // Este método intercepta cualquier excepción lanzada por los métodos de los controladores
    @AfterThrowing(pointcut = "execution(* edu.javeriana.tallernotasAOP.controlador.*.*(..))", throwing = "ex")
    public ResponseEntity<RespuestaError> manejarExcepciones(Exception ex) {
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());

        RespuestaError error = new RespuestaError("Error Interno del Servidor", detalles);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Método específico para manejar excepciones de tipo RuntimeException (cuando no se encuentra un registro)
    @AfterThrowing(pointcut = "execution(* edu.javeriana.tallernotasAOP.controlador.*.get*(..))", throwing = "ex")
    public ResponseEntity<RespuestaError> manejarRegistroNoEncontrado(RuntimeException ex) {
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());
        RespuestaError error = new RespuestaError("Registro No Encontrado", detalles);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
