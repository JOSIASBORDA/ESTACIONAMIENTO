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
import java.util.Set;
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
@Table(name = "tipopago")
public class TipoPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Tipo_Pago")  // Nombre de la columna en la base de datos
    private int id_Tipo_Pago;

    @Column(name = "TipoPago", nullable = false)  // Especifica que la columna TipoPago no puede ser nula
    private String TipoPago;
    
    // Relación One-to-Many con Pagos
    @OneToMany(mappedBy = "tipoPago")
    private Set<Pagos> pagos; // Un tipo de pago puede tener múltiples pagos
    
}
