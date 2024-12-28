/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service;

import com.estacionamiento.estacionamiento.Entity.Sitio;
import java.util.List;

/**
 *
 * @author Josias
 */
public interface SitioService {
    
    public Sitio registrarSitio(Sitio sitio);
    public List<Sitio> listarSitios();
    public List<Sitio> listarSitiosDisponibles();
    public Sitio editarSitio(int id, Sitio sitio);
    public void eliminarSitio(int id);
    public Sitio obtenerSitioPorId(int id);
    public void actualizarEstadoSitio(Sitio sitio, boolean estado);
    
}
