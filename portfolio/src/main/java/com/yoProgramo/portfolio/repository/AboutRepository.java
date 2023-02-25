/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.yoProgramo.portfolio.repository;

import com.yoProgramo.portfolio.model.About;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
public interface AboutRepository extends JpaRepository< About, Integer>{

    public Optional< About> findByDescripcion(String descripcion);
    public boolean existsByDescripcion(String descripcion);
}

