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
@Table(name = "tarifavehiculo")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private int idTarifa;
    
    @Column(name = "tipo_vehiculo")
    private String tipo_vehiculo;

    @Column(name = "tarifa")
    private Double tarifa;
    
    // Relación OneToMany con RegistroVehiculos
    @OneToMany(mappedBy = "tarifa", cascade = CascadeType.ALL)
    @JsonBackReference  // Usamos @JsonBackReference aquí para evitar la serialización infinita
    private Set<RegistroVehiculos> registrosVehiculos;

    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }

    public Set<RegistroVehiculos> getRegistrosVehiculos() {
        return registrosVehiculos;
    }

    public void setRegistrosVehiculos(Set<RegistroVehiculos> registrosVehiculos) {
        this.registrosVehiculos = registrosVehiculos;
    }
    
    

}
