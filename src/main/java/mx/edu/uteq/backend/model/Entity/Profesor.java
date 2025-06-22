package mx.edu.uteq.backend.model.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import mx.edu.uteq.backend.model.dto.Grupo;

@Entity
@Data
public class Profesor {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String correo;
    private String cubiculo;

    @Transient
    private List<Grupo> grupos;
}
