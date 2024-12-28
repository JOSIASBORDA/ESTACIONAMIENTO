/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Controller;

import com.estacionamiento.estacionamiento.Entity.Pagos;
import com.estacionamiento.estacionamiento.Entity.RegistroVehiculos;
import com.estacionamiento.estacionamiento.Entity.Sitio;
import com.estacionamiento.estacionamiento.Entity.Tarifa;
import com.estacionamiento.estacionamiento.Entity.TipoPago;
import com.estacionamiento.estacionamiento.Respository.VehiculosRepository;
import com.estacionamiento.estacionamiento.Service.RegistroVehiculosService;
import com.estacionamiento.estacionamiento.Service.SitioService;
import com.estacionamiento.estacionamiento.Service.TarifaService;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.estacionamiento.estacionamiento.Service.PagosService;
import com.estacionamiento.estacionamiento.Service.TipoPagoService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Josias
 */
@Controller
public class VehiculosController {

    @Autowired
    private RegistroVehiculosService registroVehiculosService;
    @Autowired
    private SitioService sitioService;
    @Autowired
    private TarifaService tarifaService;
    @Autowired
    private PagosService pagosService;
    @Autowired
    private TipoPagoService tipoPagoService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("listavehiculos", registroVehiculosService.listarRegistros());
        model.addAttribute("contentFragment", "fragments/index");
        model.addAttribute("tiposPago", tipoPagoService.listaTipoPago());
        return "base1";  // Nombre de tu plantilla principal
    }

    @GetMapping("/sitios/datos")
    @ResponseBody
    public List<Sitio> obtenerSitios() {
        return sitioService.listarSitios();
    }

    @ModelAttribute("tiposPago")
    public List<TipoPago> cargarTiposPago() {
        return tipoPagoService.listaTipoPago();
    }

    @GetMapping("/vehiculos/nuevo")
    public String agregarVehiculo(Model model) {
        RegistroVehiculos vehiculo = new RegistroVehiculos();
        model.addAttribute("vehiculo", vehiculo);

        List<Tarifa> tarifa = tarifaService.listarTarifas();

        // Ahora solo se cargan los sitios disponibles
        List<Sitio> sitiosDisponibles = sitioService.listarSitiosDisponibles();

        model.addAttribute("tarifa", tarifa);
        model.addAttribute("sitio", sitiosDisponibles); // Solo sitios disponibles

        model.addAttribute("contentFragment", "fragments/vehiculos_add");
        return "base1";
    }

    @PostMapping("/vehiculos/guardar")
    public String guardarVehiculo(@ModelAttribute("vehiculo") RegistroVehiculos vehiculo) {
        registroVehiculosService.registrarVehiculo(vehiculo);
        return "redirect:/index";
    }

    @PostMapping("/vehiculos/salida/{id}")
    public String registrarSalida(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Registrar la salida del vehículo
            RegistroVehiculos registro = registroVehiculosService.registrarSalidaVehiculo(id);

            // Pasar los datos necesarios para la vista de la siguiente petición
            redirectAttributes.addFlashAttribute("registro", registro);
            redirectAttributes.addFlashAttribute("tiempoEstacionado", registro.getTiempoEstancia());
            redirectAttributes.addFlashAttribute("costo", registro.getCosto());

        } catch (RuntimeException e) {
            // Manejar el error si el registro no se encuentra
            redirectAttributes.addFlashAttribute("error", "Registro no encontrado o ya procesado.");
            return "redirect:/index";  // Redirigir a otra página con el mensaje de error
        }

        return "redirect:/index";  // Redirigir a la vista deseada
    }

    @PostMapping("/registrarpago")
    public ResponseEntity<?> registrarPago(@RequestParam Integer idRegistro,
            @RequestParam double monto,
            @RequestParam Integer idTipoPago) {
        try {
            // Llamar al servicio para registrar el pago
            Pagos pago = pagosService.registrarPago(idRegistro, monto, idTipoPago);

            // Retornar el pago registrado
            return ResponseEntity.ok(pago);
        } catch (IllegalArgumentException e) {
            // Retornar error si hay argumentos inválidos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Manejar cualquier otro error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el pago: " + e.getMessage());
        }
    }

    @GetMapping("/imprimir-ticket-entrada/{id}")
    public ResponseEntity<byte[]> downloadReporte(@PathVariable("id") int id) throws IOException, DocumentException {
        try {
            // Llamar al servicio para generar el ticket
            byte[] ticketPdf = registroVehiculosService.generarTicketEntrada(id);

            // Configurar los encabezados para la respuesta PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(ticketPdf.length);

            // Retornar el PDF generado como respuesta
            return new ResponseEntity<>(ticketPdf, headers, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si el registro no se encuentra, retornar un error
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/imprimir-ticket-salida/{id}")
    public ResponseEntity<byte[]> downloadTicketSalida(@PathVariable("id") int id) throws IOException, DocumentException {
        try {
            // Llamar al servicio para generar el ticket de salida
            byte[] ticketPdf = registroVehiculosService.generarTicketSalida(id);

            // Configurar los encabezados para abrir en el navegador
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(ticketPdf.length);

            // Retornar el PDF generado como respuesta
            return new ResponseEntity<>(ticketPdf, headers, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si el registro no se encuentra o hay otro problema, retornar un error
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/vehiculos/detalle-pago/{id}")
    @ResponseBody
    public Map<String, Object> obtenerDatosPago(@PathVariable("id") Integer id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            RegistroVehiculos registro = registroVehiculosService.obtenerPorId(id);

            if (registro == null) {
                throw new IllegalArgumentException("Registro no encontrado.");
            }

            respuesta.put("tiempoEstancia", registro.getTiempoEstancia());
            respuesta.put("costoSugerido", registro.getCosto());

            return respuesta;
        } catch (Exception e) {
            respuesta.put("error", "Error al obtener los datos: " + e.getMessage());
            return respuesta;
        }
    }

    @GetMapping("/buscar")
    @ResponseBody
    public List<RegistroVehiculos> buscarVehiculos(@RequestParam("placa") String placa) {
        if (placa == null || placa.isEmpty()) {
            // Devuelve todos los registros si no hay búsqueda
            return registroVehiculosService.listarRegistros();
        }
        // Devuelve los registros filtrados por placa
        return registroVehiculosService.buscarPorPlaca(placa);
    }

}
