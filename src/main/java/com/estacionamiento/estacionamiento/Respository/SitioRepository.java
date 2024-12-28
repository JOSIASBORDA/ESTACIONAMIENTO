/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Respository;

import com.estacionamiento.estacionamiento.Entity.Sitio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Josias
 */
@Repository
public interface SitioRepository extends JpaRepository<Sitio, Integer> {
    List<Sitio> findByEstado(boolean estado);
}
