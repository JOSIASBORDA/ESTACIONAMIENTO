/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estacionamiento.estacionamiento.Service.Impl;

import com.estacionamiento.estacionamiento.Entity.Usuario;
import com.estacionamiento.estacionamiento.Respository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import com.estacionamiento.estacionamiento.Service.UsuarioService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Josias
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Encriptar la clave antes de guardar
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario); // Guarda y devuelve el usuario guardado
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll(); // Devuelve todos los usuarios
    }

    @Override
    public Usuario editarUsuario(int id, Usuario usuario) {
        // Busca el usuario existente
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(id);

        if (usuarioExistenteOpt.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOpt.get();

            // Actualiza los campos necesarios
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setNumDocumento(usuario.getNumDocumento());
            usuarioExistente.setTipoDocumento(usuario.getTipoDocumento());

            // Si la contraseña ha cambiado, la encriptamos
            if (usuario.getClave() != null && !usuario.getClave().isEmpty()) {
                usuarioExistente.setClave(passwordEncoder.encode(usuario.getClave()));
            }

            return usuarioRepository.save(usuarioExistente); // Guarda los cambios
        } else {
            // Aquí puedes devolver null o manejarlo de otra manera, por ejemplo, registrando un mensaje
            return null; // O puedes devolver un objeto de usuario vacío si prefieres
        }
    }

    @Override
    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario buscarUsuarioPorId(int id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        return usuarioOpt.orElse(null); // Retorna el usuario si existe, de lo contrario null
    }
    
}
