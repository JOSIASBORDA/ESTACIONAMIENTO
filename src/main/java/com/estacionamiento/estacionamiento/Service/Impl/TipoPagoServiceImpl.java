/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.TipoPago;
import com.estacionamiento.estacionamiento.Respository.TipoPagoRepository;
import com.estacionamiento.estacionamiento.Service.TipoPagoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
public class TipoPagoServiceImpl implements TipoPagoService{
    
    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Override
    public List<TipoPago> listaTipoPago() {
        return tipoPagoRepository.findAll();
    }

    @Override
    public TipoPago obtenerTipoPagoPorId(int id) {
        return tipoPagoRepository.findById(id).orElse(null);
    }
}
