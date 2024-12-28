/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service;

import com.estacionamiento.estacionamiento.Entity.TipoDocumento;
import java.util.List;

/**
 *
 * @author Josias
 */
public interface TipoDocumentoService {
    
    public List<TipoDocumento> listaTipoDoc();
    public TipoDocumento obtenerTipoDocumentoPorId(int id);
    
}
