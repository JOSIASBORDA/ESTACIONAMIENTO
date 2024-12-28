/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.Sitio;
import com.estacionamiento.estacionamiento.Respository.SitioRepository;
import com.estacionamiento.estacionamiento.Service.SitioService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
public class SitioServiceImpl implements SitioService {
    
    @Autowired
    private SitioRepository sitioRepository;

    @Override
    public Sitio registrarSitio(Sitio sitio) {

        if (sitio.getEstado() == null) {
            sitio.setEstado(true); 
        }
        return sitioRepository.save(sitio);  // Guardar el sitio en la base de datos
    }

    @Override
    public List<Sitio> listarSitios() {
        return sitioRepository.findAll();
    }

    @Override
    public Sitio editarSitio(int id, Sitio sitio) {
        Optional<Sitio> sitioExistenteOpt = sitioRepository.findById(id);

        if (sitioExistenteOpt.isPresent()) {
            Sitio sitioExistente = sitioExistenteOpt.get();
            sitioExistente.setEstado(sitio.getEstado());
            sitioExistente.setNumeroSitio(sitio.getNumeroSitio());
            return sitioRepository.save(sitioExistente);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarSitio(int id) {
        sitioRepository.deleteById(id);
    }

    @Override
    public Sitio obtenerSitioPorId(int id) {
        return sitioRepository.findById(id).orElse(null);
    }

    @Override
    public void actualizarEstadoSitio(Sitio sitio, boolean estado) {
        if (sitio != null) {
            Sitio sitioExistente = sitioRepository.findById(sitio.getIdSitio()).orElse(null);
            if (sitioExistente != null) {
                sitioExistente.setEstado(estado);
                sitioRepository.save(sitioExistente);
            }
        }
    }

    @Override
    public List<Sitio> listarSitiosDisponibles() {
        return sitioRepository.findByEstado(true);
    }
    
}
