package mx.edu.uteq.backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mx.edu.uteq.backend.model.dto.Profesor;

@FeignClient(name = "servicio-profesores", url = "http://localhost:8085") 
public interface ProfesorRest {
    @GetMapping("/api/profesor/usuario/{idUsuario}")
    ResponseEntity<Profesor> getIdsByUsuarioId(@PathVariable("idUsuario") Integer idUsuario);

}
