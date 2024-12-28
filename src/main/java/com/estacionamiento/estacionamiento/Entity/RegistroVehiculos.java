/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
@Table(name = "registrovehiculos")
public class RegistroVehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer idRegistro;

    @ManyToOne
    @JoinColumn(name = "id_sitio", referencedColumnName = "id_sitio", nullable = false)
    private Sitio sitio;  // Relación con el sitio del estacionamiento

    // Relación ManyToOne con Tarifa
    @ManyToOne
    @JoinColumn(name = "id_tarifa", referencedColumnName = "id_tarifa", nullable = false)  // La columna id_tarifa en la tabla registrovehiculos
    private Tarifa tarifa;
    
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime fechaIngreso;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime fechaSalida; 
    
    @Column(name = "tiempoEstancia")
    private long tiempoEstancia;
    
    @Column(name = "descripcion")
    private String descripcion;

    // Relación ManyToOne con Usuario (Un registro de vehículo tiene un único usuario)
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)  // Relación con la tabla usuarios
    private Usuario usuario;  // Referencia al usuario que registró el vehículo
    
    @Column(name = "costo")
    private Double costo;
    
    // Relación ManyToOne con Vehiculos
    @ManyToOne
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo", nullable = false)  // Relación con la tabla vehiculos
    private Vehiculos vehiculo;  // Vehículo asociado a este registro
    
    @OneToOne(mappedBy = "registroVehiculo", cascade = CascadeType.ALL)
    private Pagos pago;

}