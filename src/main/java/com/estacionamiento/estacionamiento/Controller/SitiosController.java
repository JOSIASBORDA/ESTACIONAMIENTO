/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Controller;

import com.estacionamiento.estacionamiento.Entity.Sitio;
import com.estacionamiento.estacionamiento.Service.SitioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Josias
 */
@Controller
public class SitiosController {

    @Autowired
    private SitioService sitioService;

    @GetMapping("/sitios")
    public String frmsitios(Model model) {
        model.addAttribute("sitios", sitioService.listarSitios());
        model.addAttribute("contentFragment", "fragments/sitiosconfig");
        return "base1";  // Retorna la vista principal (base.html)
    }

}
