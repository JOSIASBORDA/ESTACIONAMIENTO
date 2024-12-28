package com.estacionamiento.estacionamiento.Controller;

import com.estacionamiento.estacionamiento.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaginasController {
    
    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "expired", required = false) String expired, 
                        Model model) {

        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas");
        }

        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión exitosamente.");
        }

        if (expired != null) {
            model.addAttribute("message", "Tu sesión ha expirado.");
        }

        return "login"; // Retorna la vista de login
    }

//    @GetMapping("/index")
//    public String index(Model model) {
//        model.addAttribute("contentFragment", "fragments/index");
//        return "base1";  // Nombre de tu plantilla principal
//    }

//    @GetMapping("/usuarios")
//    public String usuarios(Model model) {
//        // Obtén la lista de usuarios desde el servicio
//        model.addAttribute("listausuarios", usuarioService.listarUsuarios());
//        model.addAttribute("contentFragment", "fragments/usuarios");
//        return "base";  // Retorna la vista principal (base.html)
//    }

    @GetMapping("/sitiosconfig")
    public String sitios(Model model) {
        model.addAttribute("contentFragment", "fragments/sitiosconfig");
        return "base1";  // Nombre de tu plantilla principal
    }

//    @GetMapping("/tarifas")
//    public String tarifas(Model model) {
//        model.addAttribute("contentFragment", "fragments/tarifas");
//        return "base1";  // Nombre de tu plantilla principal
//    }
//    @GetMapping("/usuarios_add")
//    public String usuariosadd(Model model) {
//        model.addAttribute("contentFragment", "fragments/usuarios_add");
//        return "base1";  // Nombre de tu plantilla principal
//    }


}
