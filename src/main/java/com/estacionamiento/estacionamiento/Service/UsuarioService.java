/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service;

import com.estacionamiento.estacionamiento.Entity.Usuario;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josias
 */
@Service
public interface UsuarioService {
    
    public Usuario registrarUsuario(Usuario usuario);
    public List<Usuario> listarUsuarios();
    public Usuario editarUsuario(int id, Usuario usuario);
    public void eliminarUsuario(int id);
    public Usuario buscarUsuarioPorId(int id);
    
}
