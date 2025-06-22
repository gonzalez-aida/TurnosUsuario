package mx.edu.uteq.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.uteq.backend.client.GrupoRest;
import mx.edu.uteq.backend.model.Entity.Profesor;
import mx.edu.uteq.backend.model.Entity.ProfesorGrupo;
import mx.edu.uteq.backend.model.Repositroty.ProfesorRepo;
import mx.edu.uteq.backend.model.dto.Grupo;

@Service
public class ProfesorService {
    @Autowired
    private ProfesorRepo repo;

    @Autowired
    private GrupoRest grupoClient;

    @Transactional(readOnly = true)
    public List<Profesor> getAll(){
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Profesor> getById(int id){
        return repo.findById(id);
    }

    @Transactional
    public Profesor save(Profesor profesor){
        return repo.save(profesor);
    }

    @Transactional
    public void deleteById(int id){
        repo.deleteById(id);
    }

    @Transactional
    public boolean addProfesorGrupo (int profesorId, int grupoid){

        Optional<Profesor> opt = repo.findById(profesorId);
        if(opt.isPresent()){
            //Se busca al profesor para saber que Id de profesor corresponde al grupo ingresado
            Profesor profesor = opt.get();

            ProfesorGrupo profesorGrupo = new ProfesorGrupo();
            profesorGrupo.setGrupoId(grupoid);

            profesor.addProfesoresGrupos(profesorGrupo);

            repo.save(profesor);

            return true;
        }
        return false;
    }
}
