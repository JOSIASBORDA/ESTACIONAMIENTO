/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Controller.Rest;

import com.estacionamiento.estacionamiento.Entity.Pagos;
import com.estacionamiento.estacionamiento.Entity.RegistroVehiculos;
import com.estacionamiento.estacionamiento.Entity.TipoDocumento;
import com.estacionamiento.estacionamiento.Entity.Usuario;
import com.estacionamiento.estacionamiento.Respository.TipoDocumentoRepository;
import com.estacionamiento.estacionamiento.Service.Impl.UsuarioServiceImpl;
import com.estacionamiento.estacionamiento.Service.PagosService;
import com.estacionamiento.estacionamiento.Service.RegistroVehiculosService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Josias
 */
@RestController
@RequestMapping("/pagos")
public class UsuarioControllerRest {

    @Autowired
    private PagosService pagosService;

    @PostMapping
    public ResponseEntity<Pagos> registrarPago(@RequestParam Integer idRegistro,
            @RequestParam Double monto,
            @RequestParam Integer idTipoPago) {
        Pagos pago = pagosService.registrarPago(idRegistro, monto, idTipoPago);
        return ResponseEntity.ok(pago);
    }
}
