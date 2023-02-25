/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yoProgramo.portfolio.service;

import com.yoProgramo.portfolio.model.About;
import com.yoProgramo.portfolio.repository.AboutRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AboutService  {
      
    @Autowired
    AboutRepository sobreMiRepository;
    
    public List<About> list(){
        return sobreMiRepository.findAll();
    }
    
    public Optional<About> getOne(int id){
        return sobreMiRepository.findById(id);
    }
    
    public Optional<About> getByDescripcion(String descripcion){
        return sobreMiRepository.findByDescripcion(descripcion);
    }
    
    public void save(About sobreMi){
        sobreMiRepository.save(sobreMi);
    }
    
    public void delete(int id){
        sobreMiRepository.deleteById(id);
    }
    
    public boolean existsById(int id){
        return sobreMiRepository.existsById(id);
    }
    
    public boolean existsByDescripcion(String descripcion){
        return sobreMiRepository.existsByDescripcion(descripcion);
    }
}

