/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Controller;

import com.estacionamiento.estacionamiento.Service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Josias
 */
@Controller
public class TarifasController {
    
    @Autowired
    private TarifaService tarifaService;
    
    @GetMapping("/tarifas")
    public String frmtarifas(Model model) {
        model.addAttribute("listatarifas", tarifaService.listarTarifas());
        model.addAttribute("contentFragment", "fragments/tarifas");
        return "base1";  // Retorna la vista principal (base.html)
    }
    
}
