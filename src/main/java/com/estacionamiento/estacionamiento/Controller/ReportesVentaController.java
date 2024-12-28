/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Controller;

import com.estacionamiento.estacionamiento.Service.PagosService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Josias
 */
@Controller
public class ReportesVentaController {

    @Autowired
    private PagosService pagosService;

    @GetMapping("/pagos")
    public String listarPagos(Model model) {
        model.addAttribute("pagos", pagosService.obtenerTodosLosPagos());
        model.addAttribute("contentFragment", "fragments/reportesventa");
        return "base1";
    }

    @GetMapping("/pagos/filtrar")
    public String filtrarPagosPorFecha(@RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {
        // Obtener los pagos según el rango de fechas
        List<Map<String, Object>> pagos = pagosService.listarPagosPorFecha(fechaInicio, fechaFin);

        // Pasar los datos al modelo
        model.addAttribute("pagos", pagos);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("contentFragment", "fragments/reportesventa");
        return "base1";
    }

    @GetMapping("/pagos/reporte-pdf")
    public ResponseEntity<byte[]> generarReportePagos(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        try {
            byte[] pdf = pagosService.generarReporteVentas(fechaInicio, fechaFin);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("reporte_ventas.pdf").build());
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
