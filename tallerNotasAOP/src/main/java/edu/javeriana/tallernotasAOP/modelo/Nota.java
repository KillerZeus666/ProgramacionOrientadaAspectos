package edu.javeriana.tallernotasAOP.modelo;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "nota")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String observacion;
    private Double valor;
    private Double porcentaje;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")  // Esta es la columna que mapea la relación
    private Estudiante estudiante;

}
