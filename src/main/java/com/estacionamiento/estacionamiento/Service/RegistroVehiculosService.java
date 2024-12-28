/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service;

import com.estacionamiento.estacionamiento.Entity.RegistroVehiculos;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Josias
 */
public interface RegistroVehiculosService {
    
    public RegistroVehiculos registrarVehiculo(RegistroVehiculos registro);
    public RegistroVehiculos registrarSalidaVehiculo(Integer idRegistro);
    public List<RegistroVehiculos> listarRegistros();
    public RegistroVehiculos editarRegistro(Integer id, RegistroVehiculos registro);
    public void eliminarRegistro(Integer id);
    public RegistroVehiculos obtenerPorId(Integer idRegistro);
    public List<RegistroVehiculos> buscarPorPlaca(String placa);
    public long calcularTiempoEstancia(RegistroVehiculos registro);
    public double calcularCosto(long minutosEstancia, double tarifaPorHora);
    public byte[] generarTicketEntrada(int id) throws IOException, DocumentException;
    public byte[] generarTicketSalida(int id) throws IOException, DocumentException;
    
}
