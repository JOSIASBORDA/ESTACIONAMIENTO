/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service;

import com.estacionamiento.estacionamiento.Entity.Pagos;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Josias
 */
public interface PagosService {
    
    public Pagos registrarPago(Integer idRegistro, double monto, Integer id_Tipo_Pago);
    public List<Map<String, Object>> obtenerTodosLosPagos();
    public List<Map<String, Object>> listarPagosPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
    public byte[] generarReporteVentas(LocalDate fechaInicio, LocalDate fechaFin) throws IOException, DocumentException;
}
