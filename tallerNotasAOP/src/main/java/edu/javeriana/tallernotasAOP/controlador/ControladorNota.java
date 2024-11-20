package edu.javeriana.tallernotasAOP.controlador;

import edu.javeriana.tallernotasAOP.servicio.NotaService;
import edu.javeriana.tallernotasAOP.modelo.Nota;
import edu.javeriana.tallernotasAOP.repositorio.RepositorioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList; // Asegúrate de importar ArrayList
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/notas")
public class ControladorNota {

    private final NotaService notaService;

    @Autowired
    private RepositorioNota repositorioNota;

    public ControladorNota(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping
    public Nota crearNota(@RequestBody Nota nota) {
        return notaService.crearNota(nota);
    }

    @PutMapping("/{id}")
    public Nota actualizarNota(@PathVariable Integer id, @RequestBody Nota nota) {
        nota.setId(id);
        return notaService.actualizarNota(nota);
    }


}