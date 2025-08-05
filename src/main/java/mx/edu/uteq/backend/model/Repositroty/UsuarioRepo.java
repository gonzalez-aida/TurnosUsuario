package mx.edu.uteq.backend.model.Repositroty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.uteq.backend.model.Entity.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombre(String nombre);
    
}