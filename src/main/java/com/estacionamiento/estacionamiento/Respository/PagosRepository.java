/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Respository;

import com.estacionamiento.estacionamiento.Entity.Pagos;
import com.estacionamiento.estacionamiento.Entity.RegistroVehiculos;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Josias
 */
@Repository
public interface PagosRepository extends JpaRepository<Pagos, Long> {
    
    boolean existsByRegistroVehiculo(RegistroVehiculos registroVehiculo);
    
    @Query("SELECT new map(p.id_Pago as idPago, v.vehiculo.placa as placa, t.TipoPago as tipoPago, p.monto as monto) " +
           "FROM Pagos p " +
           "JOIN p.registroVehiculo v " +
           "JOIN p.tipoPago t")
    List<Map<String, Object>> listarPagosConDetalles();
    
    @Query("SELECT new map(p.id_Pago as idPago, v.vehiculo.placa as placa, t.TipoPago as tipoPago, p.monto as monto) " +
           "FROM Pagos p " +
           "JOIN p.registroVehiculo v " +
           "JOIN p.tipoPago t " +
           "WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    List<Map<String, Object>> listarPagosConDetallesPorFecha(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                                             @Param("fechaFin") LocalDateTime fechaFin);
    
}
