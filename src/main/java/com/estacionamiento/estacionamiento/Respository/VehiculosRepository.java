/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Respository;

import com.estacionamiento.estacionamiento.Entity.Vehiculos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Josias
 */
public interface VehiculosRepository extends JpaRepository<Vehiculos, Long>{
    
    Optional<Vehiculos> findByPlaca(String placa);
    
}
