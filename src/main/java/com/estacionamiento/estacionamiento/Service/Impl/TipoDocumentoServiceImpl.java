/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.TipoDocumento;
import com.estacionamiento.estacionamiento.Respository.TipoDocumentoRepository;
import com.estacionamiento.estacionamiento.Service.TipoDocumentoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public List<TipoDocumento> listaTipoDoc() {
        return tipoDocumentoRepository.findAll();
    }
    
    @Override
    public TipoDocumento obtenerTipoDocumentoPorId(int id) {
        return tipoDocumentoRepository.findById(id).orElse(null); // Devuelve null si no se encuentra
    }
    
}
