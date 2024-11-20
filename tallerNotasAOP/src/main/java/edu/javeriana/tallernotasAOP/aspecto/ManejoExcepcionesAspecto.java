package edu.javeriana.tallernotasAOP.aspecto;

import edu.javeriana.tallernotasAOP.error.RespuestaError;
import edu.javeriana.tallernotasAOP.modelo.Estudiante;
import edu.javeriana.tallernotasAOP.modelo.Nota;
import edu.javeriana.tallernotasAOP.servicio.NotaService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
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

    private final NotaService notaService;

    public ManejoExcepcionesAspecto(NotaService notaService) {
        this.notaService = notaService;
    }

    // Punto común para operaciones de crear y actualizar notas
    @Pointcut("execution(* edu.javeriana.tallernotasAOP.servicio.NotaService.crear*(..))" +
            "|| execution(* edu.javeriana.tallernotasAOP.servicio.NotaService.actualizar*(..))")
    public void notaOperations() {
    }

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

    // Validación de porcentaje acumulado
    @AfterReturning("notaOperations()")
    public void validateNotePercentage(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Nota nota = (Nota) args[0];
        Estudiante estudiante = nota.getEstudiante();

        double porcentajeAcumulado = calcularPorcentajeAcumulado(estudiante);
        if (porcentajeAcumulado + nota.getPorcentaje() > 100.0) {
            throw new IllegalArgumentException("El porcentaje acumulado de las notas del estudiante no puede superar el 100%.");
        }
    }

    private double calcularPorcentajeAcumulado(Estudiante estudiante) {
        List<Nota> notas = notaService.obtenerNotasPorEstudiante(estudiante);
        return notas.stream()
                .mapToDouble(Nota::getPorcentaje)
                .sum();
    }

    // Manejo específico para IllegalArgumentException
    @AfterThrowing(pointcut = "execution(* edu.javeriana.tallernotasAOP.servicio.NotaService.*(..))", throwing = "ex")
    public ResponseEntity<RespuestaError> manejarValidacionPorcentaje(IllegalArgumentException ex) {
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());

        RespuestaError error = new RespuestaError("Validación de Porcentaje Fallida", detalles);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
