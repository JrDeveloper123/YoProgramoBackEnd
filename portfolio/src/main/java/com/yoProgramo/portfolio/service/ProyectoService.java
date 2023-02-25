/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yoProgramo.portfolio.service;

import com.yoProgramo.portfolio.model.Proyecto;
import com.yoProgramo.portfolio.repository.ProyectoRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProyectoService {
    @Autowired
    ProyectoRepository proyectoRepository;
    
    public List<Proyecto> list() {
        return proyectoRepository.findAll();
    }
    
    public Optional<Proyecto> getOne(int id) {
        return proyectoRepository.findById(id);
    }
    
    public Optional<Proyecto> getByNombreP (String nombreP) {
        return proyectoRepository.findByNombreP(nombreP);
    }
    
    public void save(Proyecto proyecto) {
        proyectoRepository.save(proyecto);
    }
    
    public void delete(int id) {
        proyectoRepository.deleteById(id);
    }
    
    public boolean existsById(int id) {
        return proyectoRepository.existsById(id);
    }
    
    public boolean existsByNombreP(String nombreP) {
        return proyectoRepository.existsByNombreP(nombreP);
    }
}
