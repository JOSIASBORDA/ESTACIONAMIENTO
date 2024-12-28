/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.Roles;
import com.estacionamiento.estacionamiento.Respository.RolesRepository;
import com.estacionamiento.estacionamiento.Service.RolesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
public class RolesServiceImpl implements RolesService{
    
    @Autowired
    private RolesRepository rolesRepository; 

    @Override
    public List<Roles> obtenerRoles() {
        return rolesRepository.findAll();
    }


}
