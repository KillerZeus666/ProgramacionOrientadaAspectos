package edu.javeriana.tallernotasAOP.controlador;

import edu.javeriana.tallernotasAOP.modelo.Estudiante;
import edu.javeriana.tallernotasAOP.repositorio.RepositorioEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ControladorEstudiante {

    @Autowired
    private RepositorioEstudiante repositorioEstudiante;

    // Getter
    public RepositorioEstudiante getRepositorioEstudiante() {
        return repositorioEstudiante;
    }

    // Setter
    public void setRepositorioEstudiante(RepositorioEstudiante repositorioEstudiante) {
        this.repositorioEstudiante = repositorioEstudiante;
    }

    @PostMapping("/crea")
    public Estudiante creaEstudiante(@RequestBody Estudiante estudiante) {
        return repositorioEstudiante.save(estudiante);
    }

    @GetMapping("/estudiantes")
    public List<Estudiante> traeEstudiantes() {
        return repositorioEstudiante.findAll();
    }

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<Estudiante> traeEstudiante(@PathVariable Integer id) {
        Estudiante estudiante = repositorioEstudiante.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el estudiante con id: " + id));
        return ResponseEntity.ok(estudiante);
    }

    @PutMapping("/act/{id}")
    public Estudiante actualizaEstudiante(@PathVariable Integer id, @RequestBody Estudiante estudiante) {
        Estudiante nuevoEstudiante = repositorioEstudiante.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el estudiante con id: " + id));

        nuevoEstudiante.setNombre(estudiante.getNombre());
        nuevoEstudiante.setApellido(estudiante.getApellido());
        nuevoEstudiante.setCorreo(estudiante.getCorreo());

        return repositorioEstudiante.save(nuevoEstudiante);
    }

    @DeleteMapping("/borra/{id}")
    public ResponseEntity<HttpStatus> borraEstudiante(@PathVariable Integer id) {
        Estudiante estudiante = repositorioEstudiante.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el estudiante con id: " + id));
        repositorioEstudiante.delete(estudiante);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
