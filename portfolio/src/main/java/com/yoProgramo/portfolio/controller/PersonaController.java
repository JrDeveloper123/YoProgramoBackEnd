/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yoProgramo.portfolio.controller;

import com.yoProgramo.portfolio.dto.DtoPersona;
import com.yoProgramo.portfolio.model.Persona;
import com.yoProgramo.portfolio.security.util.Mensaje;
import com.yoProgramo.portfolio.service.PersonaService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persona")
@CrossOrigin()
public class PersonaController {

    @Autowired
    PersonaService personaService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list() {
        List<Persona> list = personaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id) {
        if (!personaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        
        Persona persona = personaService.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!personaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        personaService.delete(id);
        return new ResponseEntity(new Mensaje("Persona eliminada"), HttpStatus.OK);
    }
    
     @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoPersona dtoPersona) {
        if (StringUtils.isBlank(dtoPersona.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (personaService.existsByNombre(dtoPersona.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        Persona persona = new Persona(dtoPersona.getNombre(), dtoPersona.getApellido(),dtoPersona.getPuesto(),dtoPersona.getDescripcion(),dtoPersona.getImg());
        personaService.save(persona);

        return new ResponseEntity(new Mensaje("Persona creada exitosamente"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoPersona dtoPersona) {
        //Validamos si existe el ID
        if (!personaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
         //Compara nombre
        if (personaService.existsByNombre(dtoPersona.getNombre()) && personaService.getByNombre(dtoPersona.getNombre()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        //No puede estar vacio
        if (StringUtils.isBlank(dtoPersona.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Persona persona = personaService.getOne(id).get();
        persona.setNombre(dtoPersona.getNombre());
        persona.setApellido(dtoPersona.getApellido());
        persona.setPuesto(dtoPersona.getPuesto());
        persona.setDescripcion((dtoPersona.getDescripcion()));
        persona.setImg(dtoPersona.getImg());

        personaService.save(persona);
        return new ResponseEntity(new Mensaje("Atualizada"), HttpStatus.OK);
    }

    
}

