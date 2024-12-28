/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service;
import com.estacionamiento.estacionamiento.Entity.TipoPago;
import java.util.List;

/**
 *
 * @author Josias
 */
public interface TipoPagoService {
    
    public List<TipoPago> listaTipoPago();
    public TipoPago obtenerTipoPagoPorId(int id);
}
