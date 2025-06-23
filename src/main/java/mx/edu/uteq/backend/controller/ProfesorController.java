package mx.edu.uteq.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uteq.backend.model.Entity.Profesor;
import mx.edu.uteq.backend.model.dto.Grupo;
import mx.edu.uteq.backend.service.ProfesorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/profesor")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    public List<Profesor> getAll(@RequestParam boolean activos) {
        if(activos){
            return profesorService.getAll().stream().filter(Profesor::isActivo).toList();
        }
        return profesorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return profesorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Profesor profesor) {
        Profesor profesorDB = profesorService.save(profesor);         
        return ResponseEntity.ok(profesorDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProfesor (@PathVariable int id, @RequestBody Profesor profesor) {
        Optional<Profesor> opt = profesorService.getById(id);
        if(opt.isPresent()){
            Profesor profesorDB = opt.get();
            profesorDB.setNombre(profesor.getNombre());
            profesorDB.setCorreo(profesor.getCorreo());
            profesorDB.setCubiculo(profesor.getCubiculo());
            profesorDB.setActivo(profesor.isActivo());
            return ResponseEntity.ok(profesorService.save(profesorDB));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{idProfesor}/grupos")
    public ResponseEntity<?> addProfesorGrupo(@PathVariable int idProfesor, @RequestBody List<Grupo> grupos) {        
        for(int i=0; i<grupos.size();i++)
        {
            if(!profesorService.addProfesorGrupo(idProfesor, grupos.get(i).getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algunos grupos no pudieron ser asignados o no existen.");
            }
        }
        return ResponseEntity.ok("Grupos asignados exitosamente.");
    }
    
    @PutMapping("/{idProfesor}/grupos")
    public ResponseEntity<?> removeProfesorGrupo(@PathVariable int idProfesor, @RequestBody List<Grupo> grupos) {
        for(int i=0; i<grupos.size();i++)
        {
            if(!profesorService.addProfesorGrupo(idProfesor, grupos.get(i).getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algunos grupos no pudieron ser eliminados o no existen.");
            }
        }
        return ResponseEntity.ok("Grupos eliminados exitosamente.");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable int id) {
        Optional<Profesor> opt = profesorService.getById(id);
        if (opt.isPresent()) {
            profesorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
    
}
