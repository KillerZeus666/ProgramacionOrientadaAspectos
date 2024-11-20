package edu.javeriana.tallernotasAOP.repositorio;

import edu.javeriana.tallernotasAOP.modelo.Estudiante;
import edu.javeriana.tallernotasAOP.modelo.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioNota extends JpaRepository<Nota, Long> {
    List<Nota> findByEstudiante(Estudiante estudiante);
}
