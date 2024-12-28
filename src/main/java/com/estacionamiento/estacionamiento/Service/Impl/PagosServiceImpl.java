/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.Pagos;
import com.estacionamiento.estacionamiento.Entity.RegistroVehiculos;
import com.estacionamiento.estacionamiento.Entity.TipoPago;
import com.estacionamiento.estacionamiento.Respository.RegistroVehiculosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.estacionamiento.estacionamiento.Service.PagosService;
import com.estacionamiento.estacionamiento.Respository.PagosRepository;
import com.estacionamiento.estacionamiento.Respository.TipoPagoRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
public class PagosServiceImpl implements PagosService {

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private RegistroVehiculosRepository registroVehiculosRepository;

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Override
    @Transactional
    public Pagos registrarPago(Integer idRegistro, double monto, Integer id_Tipo_Pago) {
        // Buscar el registro del vehículo
        Optional<RegistroVehiculos> registroOptional = registroVehiculosRepository.findById(idRegistro);

        if (registroOptional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el registro del vehículo con el ID: " + idRegistro);
        }

        RegistroVehiculos registroVehiculo = registroOptional.get();

        // Crear y guardar el pago correspondiente sin modificar el costo del registro
        Pagos pago = new Pagos();
        pago.setRegistroVehiculo(registroVehiculo);
        pago.setMonto(monto); // Monto ajustado por el usuario
        pago.setFechaPago(LocalDateTime.now());

        // Buscar el tipo de pago
        Optional<TipoPago> tipoPagoOptional = tipoPagoRepository.findById(id_Tipo_Pago);
        if (tipoPagoOptional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el tipo de pago con el ID: " + id_Tipo_Pago);
        }

        TipoPago tipoPago = tipoPagoOptional.get();
        pago.setTipoPago(tipoPago);

        // Guardar el pago en la base de datos
        return pagosRepository.save(pago);
    }
    
    
    @Override
    @Transactional
    public List<Map<String, Object>> obtenerTodosLosPagos() {
        // Usar una consulta personalizada o procesar los datos de la entidad `Pagos`
        List<Pagos> pagos = pagosRepository.findAll();

        // Convertir los datos a un formato adecuado para la vista
        return pagos.stream().map(pago -> {
            Map<String, Object> detalles = new HashMap<>();
            detalles.put("idPago", pago.getId_Pago());
            detalles.put("placa", pago.getRegistroVehiculo().getVehiculo().getPlaca());
            detalles.put("tipoPago", pago.getTipoPago().getTipoPago());
            detalles.put("monto", pago.getMonto());
            return detalles;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> listarPagosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        // Convertir las fechas a LocalDateTime para cubrir todo el día
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);

        // Llamar al repositorio para obtener los pagos
        return pagosRepository.listarPagosConDetallesPorFecha(inicio, fin);
    }

    @Override
    public byte[] generarReporteVentas(LocalDate fechaInicio, LocalDate fechaFin) throws IOException, DocumentException {
        // Obtener los pagos por fecha
        List<Map<String, Object>> pagos = listarPagosPorFecha(fechaInicio, fechaFin);

        // Crear el documento PDF
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Agregar título al documento
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reporte de Pagos", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Agregar rango de fechas al reporte
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        String fechaInicioStr = fechaInicio.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String fechaFinStr = fechaFin.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Paragraph dateRange = new Paragraph("Rango de fechas: " + fechaInicioStr + " - " + fechaFinStr, dateFont);
        dateRange.setAlignment(Element.ALIGN_CENTER);
        dateRange.setSpacingAfter(20);
        document.add(dateRange);

        // Crear tabla para los pagos
        PdfPTable table = new PdfPTable(4); // 4 columnas: ID, Placa, Tipo de Pago, Monto
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        // Encabezados de la tabla
        Stream.of("ID", "Placa", "Tipo de Pago", "Monto").forEach(header -> {
            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        });

        // Agregar datos a la tabla
        for (Map<String, Object> pago : pagos) {
            table.addCell(pago.get("idPago").toString());
            table.addCell(pago.get("placa").toString());
            table.addCell(pago.get("tipoPago").toString());
            table.addCell(pago.get("monto").toString());
        }

        // Agregar la tabla al documento
        document.add(table);
        document.close();

        return outputStream.toByteArray(); // Retorna el contenido del PDF
    }

}
