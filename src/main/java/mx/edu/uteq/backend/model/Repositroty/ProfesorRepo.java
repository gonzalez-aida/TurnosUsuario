package mx.edu.uteq.backend.model.Repositroty;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.uteq.backend.model.Entity.Profesor;

public interface ProfesorRepo extends JpaRepository <Profesor, Integer> {
    
}
