/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.Tarifa;
import com.estacionamiento.estacionamiento.Respository.TarifaRepository;
import com.estacionamiento.estacionamiento.Service.TarifaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
public class TarifaServiceImpl implements TarifaService {
    
    @Autowired
    private TarifaRepository tarifaRepository;

    @Override
    public Tarifa registrarTarifa(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    @Override
    public List<Tarifa> listarTarifas() {
        return tarifaRepository.findAll();
    }

    @Override
    public Tarifa editarTarifa(int id, Tarifa tarifa) {
        Optional<Tarifa> tarifaExistenteOpt = tarifaRepository.findById(id);

        if (tarifaExistenteOpt.isPresent()) {
            Tarifa tarifaExistente = tarifaExistenteOpt.get();
            tarifaExistente.setTarifa(tarifa.getTarifa());
            tarifaExistente.setTipo_vehiculo(tarifa.getTipo_vehiculo()); // Corregido el nombre del campo
            return tarifaRepository.save(tarifaExistente);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarTarifa(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
