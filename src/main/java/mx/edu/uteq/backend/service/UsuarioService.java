package mx.edu.uteq.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import feign.FeignException;
import mx.edu.uteq.backend.client.ProfesorRest;
import mx.edu.uteq.backend.model.Entity.Usuario;
import mx.edu.uteq.backend.model.Repositroty.UsuarioRepo;
import mx.edu.uteq.backend.model.dto.Profesor;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo repo;

    @Autowired
    private ProfesorRest profesorRest;

    public Optional<Map<String, Object>> authenticateAndGetInfo(String nombre, String contrasena) {
    Optional<Usuario> optionalUser = repo.findByNombre(nombre);

    if (optionalUser.isPresent()) {
        Usuario usuario = optionalUser.get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(contrasena, usuario.getContrasena())) {
            Map<String, Object> response = new HashMap<>();
            response.put("idUsuario", usuario.getId());
            response.put("nombre", usuario.getNombre());
            response.put("rol", usuario.getRol());

            if ("profesor".equalsIgnoreCase(usuario.getRol())) {
                try {
                    ResponseEntity<Profesor> profesorResponse = profesorRest.getIdsByUsuarioId(usuario.getId());
                    if (profesorResponse.getStatusCode().is2xxSuccessful() && profesorResponse.getBody() != null) {
                        response.put("idProfesor", profesorResponse.getBody().getId());
                    }
                } catch (FeignException e) {
                    System.err.println("Advertencia: Usuario con rol 'maestro' no encontrado en el microservicio de profesores. ID: " + usuario.getId());
                    response.put("idProfesor", null);
                }
            } else {
                response.put("idProfesor", null);
            }

            return Optional.of(response);
        }
    }

    return Optional.empty();
}
}