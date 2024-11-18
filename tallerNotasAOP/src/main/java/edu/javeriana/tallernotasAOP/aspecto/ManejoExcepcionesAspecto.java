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

    /**
     * Este método intercepta cualquier excepción lanzada por los métodos de los controladores
     * y devuelve un error genérico @param ex La excepción lanzada
     * @return ResponseEntity con el mensaje de error
     */
    @AfterThrowing(pointcut = "execution(* edu.javeriana.tallernotasAOP.controlador.*.*(..))", throwing = "ex")
    public ResponseEntity<RespuestaError> manejarExcepciones(Exception ex) {
        // Creación de la lista de detalles de la excepción
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());

        // Creación de la respuesta de error
        RespuestaError error = new RespuestaError("Error Interno del Servidor", detalles);

        // Devolver el ResponseEntity con el código HTTP 500 (Error Interno del Servidor)
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Método específico para manejar excepciones de tipo RuntimeException,
     * que usualmente indican que no se encontró un registro.
     * @param ex La excepción lanzada
     * @return ResponseEntity con el mensaje de error para registros no encontrados
     */
    @AfterThrowing(pointcut = "execution(* edu.javeriana.tallernotasAOP.controlador.*.get*(..))", throwing = "ex")
    public ResponseEntity<RespuestaError> manejarRegistroNoEncontrado(RuntimeException ex) {
        // Creación de la lista de detalles de la excepción
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());

        // Creación de la respuesta de error para registros no encontrados
        RespuestaError error = new RespuestaError("Registro No Encontrado", detalles);

        // Devolver el ResponseEntity con el código HTTP 404 (No Encontrado)
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
