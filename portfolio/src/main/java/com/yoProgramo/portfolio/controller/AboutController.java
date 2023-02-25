/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yoProgramo.portfolio.controller;

import com.yoProgramo.portfolio.dto.DtoAbout;
import com.yoProgramo.portfolio.model.About;
import com.yoProgramo.portfolio.security.util.Mensaje;
import com.yoProgramo.portfolio.service.AboutService;
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
@RequestMapping("/sobremi")
@CrossOrigin(origins = {"https://front-ef.web.app", "http://localhost:4200"})
public class AboutController  {
     @Autowired
    AboutService aboutMiService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<About>> list() {
        List<About> list = aboutMiService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<About> getById(@PathVariable("id") int id) {
        if (!aboutMiService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        
        About about = aboutMiService.getOne(id).get();
        return new ResponseEntity(about, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!aboutMiService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        aboutMiService.delete(id);
        return new ResponseEntity(new Mensaje("Descripición eliminada"), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoAbout dtoAbout) {
        if (StringUtils.isBlank(dtoAbout.getDescripcion())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (aboutMiService.existsByDescripcion(dtoAbout.getDescripcion())) {
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        About about = new About(dtoAbout.getDescripcion());
        aboutMiService.save(about);

        return new ResponseEntity(new Mensaje("Skill creada exitosamente"), HttpStatus.OK);
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoAbout dtoAbout) {
        //Validamos si existe el ID
        if (!aboutMiService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.BAD_REQUEST);
        }
         //Compara nombre
        if (aboutMiService.existsByDescripcion(dtoAbout.getDescripcion()) && aboutMiService.getByDescripcion(dtoAbout.getDescripcion()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("La descripción ya existe"), HttpStatus.BAD_REQUEST);
        }
        //No puede estar vacio
        if (StringUtils.isBlank(dtoAbout.getDescripcion())) {
            return new ResponseEntity(new Mensaje("Es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        About about = aboutMiService.getOne(id).get();
        about.setDescripcion(dtoAbout.getDescripcion());
    

        aboutMiService.save(about);
        return new ResponseEntity(new Mensaje("Descripción Actualizada"), HttpStatus.OK);
    }
}

