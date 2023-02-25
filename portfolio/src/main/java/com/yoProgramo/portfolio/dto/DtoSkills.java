/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yoProgramo.portfolio.dto;

import javax.validation.constraints.NotBlank;

public class DtoSkills {
    @NotBlank
    private String nombreS;
    @NotBlank
    private int porcentajeS;
    
     //Constructores

    public DtoSkills() {
    }

    public DtoSkills(String nombreS, int porcentajeS) {
        this.nombreS = nombreS;
        this.porcentajeS = porcentajeS;
    }
    
     //Getters & Setters

    public String getNombreS() {
        return nombreS;
    }

    public void setNombreS(String nombreS) {
        this.nombreS = nombreS;
    }

    public int getPorcentajeS() {
        return porcentajeS;
    }

    public void setPorcentajeS(int porcentajeS) {
        this.porcentajeS = porcentajeS;
    }

   
}
