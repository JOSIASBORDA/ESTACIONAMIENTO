/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Josias
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehiculos")
public class Vehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")  // Nombre de la columna en la tabla
    private long id_vehiculo;

    @Column(name = "placa", unique = true, nullable = false)  // La placa es única y no puede ser nula
    private String placa;
    
    // Relación OneToMany con RegistroVehiculos
    @OneToMany(mappedBy = "vehiculo")
    private List<RegistroVehiculos> registrosVehiculos;  // Lista de registros asociados a este vehículo
}
