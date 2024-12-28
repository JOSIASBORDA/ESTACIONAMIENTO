/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author Josias
 */
@Entity

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sitios")
public class Sitio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sitio")
    private int idSitio;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "numero_sitio")
    private String numeroSitio;
    
    // Relación OneToMany con RegistroVehiculos
    @OneToMany(mappedBy = "sitio", cascade = CascadeType.ALL)
    @JsonBackReference  // Usamos @JsonBackReference aquí para evitar la serialización infinita
    private Set<RegistroVehiculos> registrosVehiculos;

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNumeroSitio() {
        return numeroSitio;
    }

    public void setNumeroSitio(String numeroSitio) {
        this.numeroSitio = numeroSitio;
    }

    public Set<RegistroVehiculos> getRegistrosVehiculos() {
        return registrosVehiculos;
    }

    public void setRegistrosVehiculos(Set<RegistroVehiculos> registrosVehiculos) {
        this.registrosVehiculos = registrosVehiculos;
    }
    
    

}
