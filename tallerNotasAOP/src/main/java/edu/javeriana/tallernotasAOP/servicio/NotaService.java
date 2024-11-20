package edu.javeriana.tallernotasAOP.servicio;

import edu.javeriana.tallernotasAOP.modelo.Estudiante;
import edu.javeriana.tallernotasAOP.modelo.Nota;
import edu.javeriana.tallernotasAOP.repositorio.RepositorioNota;
import org.springframework.stereotype.Service;

import java.util.List;

// NotaService.java
@Service
public class NotaService {
    private final RepositorioNota repositorioNota;

    public NotaService(RepositorioNota repositorioNota) {
        this.repositorioNota = repositorioNota;
    }

    public Nota crearNota(Nota nota) {
        return repositorioNota.save(nota);
    }

    public Nota actualizarNota(Nota nota) {
        return repositorioNota.save(nota);
    }

    public void eliminarNota(Nota nota) {
        repositorioNota.delete(nota);
    }

    public List<Nota> obtenerNotasPorEstudiante(Estudiante estudiante) {
        return repositorioNota.findByEstudiante(estudiante);
    }
}