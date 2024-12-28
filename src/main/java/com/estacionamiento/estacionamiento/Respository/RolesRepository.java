/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Respository;

import com.estacionamiento.estacionamiento.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Josias
 */
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    
}
