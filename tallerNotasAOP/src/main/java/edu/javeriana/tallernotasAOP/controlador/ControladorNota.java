package edu.javeriana.tallernotasAOP.controlador;

import edu.javeriana.tallernotasAOP.modelo.Nota;
import edu.javeriana.tallernotasAOP.repositorio.RepositorioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ControladorNota {

    @Autowired
    private RepositorioNota repositorioNota;

    @GetMapping("/notas")
    public List<Nota> traerTodas() {
        return repositorioNota.findAll();
    }

    @GetMapping("/nota/{id}")
    public ResponseEntity<Nota> traeNota(@PathVariable Integer id) {
        // Si la nota no se encuentra, el Aspecto manejará la excepción
        Nota nota = repositorioNota.findById(id).orElse(null);

        if (nota == null) {
            return ResponseEntity.notFound().build(); // Retornamos un 404 si no se encuentra la nota
        }

        return ResponseEntity.ok(nota);  // Si se encuentra, retornamos un 200 OK con la nota
    }

    @PostMapping("/nota/crea")
    public Nota creaNota(@RequestBody Nota nota) {
        return repositorioNota.save(nota);
    }
}
