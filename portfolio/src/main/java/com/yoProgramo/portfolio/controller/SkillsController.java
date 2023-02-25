/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yoProgramo.portfolio.controller;

import com.yoProgramo.portfolio.dto.DtoSkills;
import com.yoProgramo.portfolio.model.Skills;
import com.yoProgramo.portfolio.security.util.Mensaje;
import com.yoProgramo.portfolio.service.SkillsService;
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
@RequestMapping("/skills")
@CrossOrigin(origins = {"https://front-ef.web.app", "http://localhost:4200"})
public class SkillsController {
     @Autowired
    SkillsService skillsService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Skills>> list() {
        List<Skills> list = skillsService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Skills> getById(@PathVariable("id") int id) {
        if (!skillsService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        
        Skills skills = skillsService.getOne(id).get();
        return new ResponseEntity(skills, HttpStatus.OK);
    }
    
        @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!skillsService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        skillsService.delete(id);
        return new ResponseEntity(new Mensaje("Skill eliminada"), HttpStatus.OK);
    }

        @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoSkills dtoSkills) {
        if (StringUtils.isBlank(dtoSkills.getNombreS())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (skillsService.existsByNombreS(dtoSkills.getNombreS())) {
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        Skills skills = new Skills(dtoSkills.getNombreS(), dtoSkills.getPorcentajeS());
        skillsService.save(skills);

        return new ResponseEntity(new Mensaje("Skill creada exitosamente"), HttpStatus.OK);
    }

        @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoSkills dtoSkills) {
        //Validamos si existe el ID
        if (!skillsService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
         //Compara nombre
        if (skillsService.existsByNombreS(dtoSkills.getNombreS()) && skillsService.getByNombreS(dtoSkills.getNombreS()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("La skill ya existe"), HttpStatus.BAD_REQUEST);
        }
        //No puede estar vacio
        if (StringUtils.isBlank(dtoSkills.getNombreS())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Skills skills = skillsService.getOne(id).get();
        skills.setNombreS(dtoSkills.getNombreS());
        skills.setPorcentajeS((dtoSkills.getPorcentajeS()));

        skillsService.save(skills);
        return new ResponseEntity(new Mensaje("Skill Actualizada"), HttpStatus.OK);
    }
}
