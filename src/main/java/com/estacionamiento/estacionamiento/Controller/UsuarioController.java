package com.estacionamiento.estacionamiento.Controller;

import com.estacionamiento.estacionamiento.Entity.Roles;
import com.estacionamiento.estacionamiento.Entity.TipoDocumento;
import com.estacionamiento.estacionamiento.Entity.Usuario;
import com.estacionamiento.estacionamiento.Service.Impl.TipoDocumentoServiceImpl;
import com.estacionamiento.estacionamiento.Service.Impl.UsuarioServiceImpl;
import com.estacionamiento.estacionamiento.Service.RolesService;
import com.estacionamiento.estacionamiento.Service.TipoDocumentoService;
import com.estacionamiento.estacionamiento.Service.UsuarioService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TipoDocumentoService tipoDocumentoService;
    @Autowired
    private RolesService rolesService;

    @GetMapping("/usuarios")
    public String frmusuario(Model model) {
        model.addAttribute("usuario", usuarioService.listarUsuarios());
        model.addAttribute("contentFragment", "fragments/usuarios");
        return "base1";  // Retorna la vista principal (base.html)
    }

    @GetMapping("/usuarios/nuevo")
    public String agregarUsuarios(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);

        // Obtener listas de tipos de documento y roles desde el servicio
        List<TipoDocumento> tiposDocumento = tipoDocumentoService.listaTipoDoc();
        List<Roles> roles = rolesService.obtenerRoles();

        model.addAttribute("tiposDocumento", tiposDocumento);
        model.addAttribute("roles", roles);

        model.addAttribute("contentFragment", "fragments/usuarios_add");
        return "base1";  // Retorna la vista principal (base.html)
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.registrarUsuario(usuario);  // Guarda el usuario usando el servicio
        return "redirect:/usuarios";  // Redirige a la lista de usuarios
    }

    // Mostrar formulario de edición
    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable int id, Model modelo) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario != null) {
            modelo.addAttribute("usuario", usuario);

            // Obtener listas de tipos de documento y roles desde el servicio
            List<TipoDocumento> tiposDocumento = tipoDocumentoService.listaTipoDoc();
            List<Roles> roles = rolesService.obtenerRoles();

            modelo.addAttribute("tiposDocumento", tiposDocumento);
            modelo.addAttribute("roles", roles);

            // Añadir el fragmento de edición al modelo
            modelo.addAttribute("contentFragment", "fragments/usuarios_edit");

            return "base1";  // Redirige a la vista principal, pero con el fragmento de edición
        } else {
            modelo.addAttribute("error", "Usuario no encontrado");
            return "redirect:/usuarios";  // Redirige si no encuentra al usuario
        }
    }

    // Actualizar usuario
    @PostMapping("/usuarios/{id}")
    public String actualizarUsuario(@PathVariable int id, @ModelAttribute("usuario") Usuario usuario, Model modelo) {
        Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id);

        if (usuarioExistente != null) {
            // Delegamos toda la lógica de actualización al servicio
            usuarioService.editarUsuario(id, usuario);
            modelo.addAttribute("mensaje", "Usuario actualizado correctamente");
        } else {
            modelo.addAttribute("error", "No se pudo encontrar el usuario para actualizar");
        }

        return "redirect:/usuarios";  // Redirige de vuelta a la lista de usuarios
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);  // Llama al servicio para eliminar el usuario
        return "redirect:/usuarios";  // Redirige a la página principal de usuarios
    }

}
