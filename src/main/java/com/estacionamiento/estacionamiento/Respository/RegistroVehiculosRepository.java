/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Respository;

import com.estacionamiento.estacionamiento.Entity.RegistroVehiculos;
import com.estacionamiento.estacionamiento.Entity.Vehiculos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Josias
 */
@Repository
public interface RegistroVehiculosRepository extends JpaRepository<RegistroVehiculos, Integer> {
    Optional<RegistroVehiculos> findByVehiculoPlaca(String placa);
    
    @Query("SELECT rv FROM RegistroVehiculos rv WHERE LOWER(rv.vehiculo.placa) LIKE LOWER(CONCAT('%', :placa, '%'))")
    List<RegistroVehiculos> buscarPorPlaca(@Param("placa") String placa);
    
}
