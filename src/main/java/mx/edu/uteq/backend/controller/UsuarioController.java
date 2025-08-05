package mx.edu.uteq.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uteq.backend.model.Entity.Usuario;
import mx.edu.uteq.backend.model.Repositroty.UsuarioRepo;
import mx.edu.uteq.backend.service.UsuarioService;

@CrossOrigin("http://localhost:5173/")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private UsuarioService serv;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return usuarioRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        Usuario usuarioDB = usuarioRepo.save(usuario);
        return ResponseEntity.ok(usuarioDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        return usuarioRepo.findById(id)
                .map(existingUsuario -> {
                    existingUsuario.setNombre(usuario.getNombre());
                    existingUsuario.setEstatus(usuario.isEstatus());
                    existingUsuario.setRol(usuario.getRol());
                    existingUsuario.setContrasena(usuario.getContrasena());
                    Usuario updatedUsuario = usuarioRepo.save(existingUsuario);
                    return ResponseEntity.ok(updatedUsuario);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario loginRequest) {
        Optional<Map<String, Object>> response = serv.authenticateAndGetInfo(
            loginRequest.getNombre(),
            loginRequest.getContrasena()
        );

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
