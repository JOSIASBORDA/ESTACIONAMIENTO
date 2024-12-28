/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service;

import com.estacionamiento.estacionamiento.Entity.Tarifa;
import java.util.List;

/**
 *
 * @author Josias
 */
public interface TarifaService {
    
    public Tarifa registrarTarifa(Tarifa tarifa);
    public List<Tarifa> listarTarifas();
    public Tarifa editarTarifa(int id, Tarifa tarifa);
    public void eliminarTarifa(int id);
    
}
