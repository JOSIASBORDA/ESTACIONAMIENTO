/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.RegistroVehiculos;
import com.estacionamiento.estacionamiento.Entity.Sitio;
import com.estacionamiento.estacionamiento.Entity.Tarifa;
import com.estacionamiento.estacionamiento.Entity.Usuario;
import com.estacionamiento.estacionamiento.Entity.Vehiculos;
import com.estacionamiento.estacionamiento.Respository.RegistroVehiculosRepository;
import com.estacionamiento.estacionamiento.Respository.SitioRepository;
import com.estacionamiento.estacionamiento.Respository.TarifaRepository;
import com.estacionamiento.estacionamiento.Respository.UsuarioRepository;
import com.estacionamiento.estacionamiento.Respository.VehiculosRepository;
//import com.estacionamiento.estacionamiento.Respository.TarifaRepository;
import com.estacionamiento.estacionamiento.Service.RegistroVehiculosService;
import com.estacionamiento.estacionamiento.Service.SitioService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
@Transactional
public class RegistroVehiculosServiceImpl implements RegistroVehiculosService {

    @Autowired
    private RegistroVehiculosRepository registroVehiculosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SitioService sitioService;

    @Autowired
    private VehiculosRepository vehiculosRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Override
    public RegistroVehiculos registrarVehiculo(RegistroVehiculos registro) {
        if (registro.getFechaIngreso() == null) {
            registro.setFechaIngreso(LocalDateTime.now());
        }

        // Obtener el usuario autenticado (asumiendo que estás usando Spring Security)
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Obtener el usuario por su nombre (suponiendo que el nombre de usuario es único)
        Usuario usuario = usuarioRepository.findByNumDocumento(userDetails.getUsername()).orElse(null);

        if (usuario == null) {
            // Si el usuario no se encuentra en la base de datos, lanzamos un error o lo gestionamos de alguna forma.
            throw new RuntimeException("Usuario no encontrado");
        }

        // Asignar el usuario al registro de vehículo
        registro.setUsuario(usuario);

        // Verificar si el vehículo ya existe
        Vehiculos vehiculo = vehiculosRepository.findByPlaca(registro.getVehiculo().getPlaca()).orElse(null);

        if (vehiculo == null) {
            // Si no existe, crear un nuevo vehículo
            vehiculo = new Vehiculos();
            vehiculo.setPlaca(registro.getVehiculo().getPlaca());
            vehiculosRepository.save(vehiculo);
        }

        // Asignar el vehículo al registro
        registro.setVehiculo(vehiculo);

        // Actualizar estado del sitio
        sitioService.actualizarEstadoSitio(registro.getSitio(), false);

        // Guardar el registro de vehículo
        return registroVehiculosRepository.save(registro);
    }

    @Override
    public RegistroVehiculos registrarSalidaVehiculo(Integer idRegistro) {
        // Recuperar el registro existente desde la base de datos
        RegistroVehiculos registro = registroVehiculosRepository.findById(idRegistro)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        // Procesar la salida del vehículo
        if (registro.getFechaSalida() == null) {
            registro.setFechaSalida(LocalDateTime.now());
        }

        // Calcular tiempo de estancia
        long minutosEstancia = calcularTiempoEstancia(registro);
        registro.setTiempoEstancia(minutosEstancia);

        // Calcular el costo
        double montoCobrado = calcularCosto(minutosEstancia, registro.getTarifa().getTarifa());
        registro.setCosto(montoCobrado);

        // Actualizar el estado del sitio
        sitioService.actualizarEstadoSitio(registro.getSitio(), true);

        // Guardar y devolver el registro actualizado
        return registroVehiculosRepository.save(registro);
    }

    @Override
    public List<RegistroVehiculos> listarRegistros() {
        return registroVehiculosRepository.findAll();
    }

    @Override
    public RegistroVehiculos editarRegistro(Integer id, RegistroVehiculos registro) {
        Optional<RegistroVehiculos> registroExistenteOpt = registroVehiculosRepository.findById(id);

        if (registroExistenteOpt.isPresent()) {
            RegistroVehiculos registroExistente = registroExistenteOpt.get();
            registroExistente.setSitio(registro.getSitio());
            registroExistente.setTarifa(registro.getTarifa());
            registroExistente.setDescripcion(registro.getDescripcion());
            registroExistente.setFechaIngreso(registro.getFechaIngreso());
            registroExistente.setFechaSalida(registro.getFechaSalida());

            return registroVehiculosRepository.save(registroExistente);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarRegistro(Integer id) {
        registroVehiculosRepository.deleteById(id);
    }

    @Override
    public RegistroVehiculos obtenerPorId(Integer idRegistro) {
        return registroVehiculosRepository.findById(idRegistro).orElse(null);
    }

    @Override
    public long calcularTiempoEstancia(RegistroVehiculos registro) {
        if (registro.getFechaIngreso() != null && registro.getFechaSalida() != null) {
            Duration duration = Duration.between(registro.getFechaIngreso(), registro.getFechaSalida());
            return duration.toMinutes();
        }
        return 0;
    }

    @Override
    public double calcularCosto(long minutosEstancia, double tarifaPorHora) {
        // Convertir minutos a horas y redondear hacia arriba
        long horasEstancia = (long) Math.ceil(minutosEstancia / 60.0);
        return horasEstancia * tarifaPorHora;
    }

    @Override
    public byte[] generarTicketEntrada(int id) throws IOException, DocumentException {
        // Obtener el registro utilizando el ID proporcionado
        RegistroVehiculos registro = obtenerPorId(id);

        // Validar que el registro exista
        if (registro == null) {
            throw new RuntimeException("Registro no encontrado");
        }

        // Formatear la fecha de ingreso
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String horaIngreso = registro.getFechaIngreso() != null ? registro.getFechaIngreso().format(formatter) : "Fecha no disponible";

        // Crear el documento PDF
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        Rectangle one = new Rectangle(224, 255);
        document.setPageSize(one);
        document.setMargins(5, 5, 2, 2);
        document.open();

        // Agregar el logo
        String logoPath = "src/main/resources/static/img/LOGO-MERCADO-TICKET.png"; // Cambia esto a la ruta real de tu logo
        Image logo = Image.getInstance(logoPath);
        logo.scaleAbsolute(150, 150); // Ajusta las dimensiones del logo
        logo.setAlignment(Image.ALIGN_CENTER); // Centra el logo
        document.add(logo); // Agrega el logo al documento

        // Definir la fuente
        Font font = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLACK);
        Paragraph p0 = new Paragraph("** Bienvenido al Estacionamiento **", font);
        Paragraph p1 = new Paragraph("=======================================\n==== Por Favor, conserve su Ticket ====\n=======================================", font);

        // Obtener datos desde el registro
        String placa = registro.getVehiculo().getPlaca();
        String tarifaPorHora = "S/" + registro.getTarifa().getTarifa();

        // Crear el párrafo con los detalles del ticket
        Paragraph p2 = new Paragraph("Placa           : " + placa
                + "\nTarifa por Hora : " + tarifaPorHora
                + "\nHora Ingreso    : " + horaIngreso, font);

        // Agregar párrafos al documento
        document.add(p0);
        document.add(p1);
        document.add(p2);
        document.close();

        return outputStream.toByteArray();  // Retornar el byte array con el contenido PDF
    }

    @Override
    public byte[] generarTicketSalida(int id) throws IOException, DocumentException {
        // Obtener el registro utilizando el ID proporcionado
        RegistroVehiculos registro = obtenerPorId(id);

        // Validar que el registro exista
        if (registro == null) {
            throw new RuntimeException("Registro no encontrado");
        }

        // Formatear las fechas de ingreso y salida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String horaIngreso = registro.getFechaIngreso() != null ? registro.getFechaIngreso().format(formatter) : "Fecha no disponible";
        String horaSalida = registro.getFechaSalida() != null ? registro.getFechaSalida().format(formatter) : "Fecha no disponible";

        // Validar que exista una hora de salida registrada
        if (registro.getFechaSalida() == null) {
            throw new RuntimeException("El registro no tiene hora de salida");
        }

        // Crear el documento PDF
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        Rectangle one = new Rectangle(224, 280);
        document.setPageSize(one);
        document.setMargins(5, 5, 2, 2);
        document.open();

        // Agregar el logo
        String logoPath = "src/main/resources/static/img/LOGO-MERCADO-TICKET.png"; // Cambia esto a la ruta real de tu logo
        Image logo = Image.getInstance(logoPath);
        logo.scaleAbsolute(150, 150); // Ajusta las dimensiones del logo
        logo.setAlignment(Image.ALIGN_CENTER); // Centra el logo
        document.add(logo); // Agrega el logo al documento

        // Definir la fuente
        Font font = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLACK);
        Paragraph p0 = new Paragraph("** Gracias por visitarnos **", font);
        Paragraph p1 = new Paragraph("=======================================\n==== Por Favor, conserve su Ticket ====\n=======================================", font);

        // Obtener datos desde el registro
        String placa = registro.getVehiculo().getPlaca();
        String tarifaPorHora = "S/" + registro.getTarifa().getTarifa();
        String montoTotal = "S/" + registro.getCosto(); // Monto total a pagar

        // Crear el párrafo con los detalles del ticket
        Paragraph p2 = new Paragraph("Placa           : " + placa
                + "\nTarifa por Hora : " + tarifaPorHora
                + "\nHora Ingreso    : " + horaIngreso
                + "\nHora Salida     : " + horaSalida
                + "\nMonto Total     : " + montoTotal, font);

        // Agregar párrafos al documento
        document.add(p0);
        document.add(p1);
        document.add(p2);
        document.close();

        return outputStream.toByteArray();  // Retornar el byte array con el contenido PDF
    }

    @Override
    public List<RegistroVehiculos> buscarPorPlaca(String placa) {
        return registroVehiculosRepository.buscarPorPlaca(placa);
    }

}
