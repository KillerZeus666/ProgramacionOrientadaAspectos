package edu.javeriana.tallernotasAOP.repositorio;

import edu.javeriana.tallernotasAOP.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEstudiante extends JpaRepository<Estudiante, Integer> {
}