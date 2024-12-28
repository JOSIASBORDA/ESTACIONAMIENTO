/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "pagos")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Pago")  // Nombre de la columna en la base de datos
    private long id_Pago;

    @OneToOne
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro", nullable = false)
    private RegistroVehiculos registroVehiculo;

    @Column(name = "Monto", nullable = false)
    private double monto;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "Fecha_Pago", nullable = false)
    private LocalDateTime fechaPago;

    @ManyToOne
    @JoinColumn(name = "idTipoPago", referencedColumnName = "id_Tipo_Pago", nullable = false)  // Relación con la tabla tipopago
    private TipoPago tipoPago;
}
