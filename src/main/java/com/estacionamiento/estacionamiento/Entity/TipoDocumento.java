package com.estacionamiento.estacionamiento.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tipodocumento")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private int id_documento;

    @Column(name = "nombreDocumento")
    private String nombreDocumento;

    @OneToMany(mappedBy = "tipoDocumento", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Usuario> usuarios;

    // Constructor por defecto (necesario para JPA)
    public TipoDocumento() {}

    // Constructor con parámetros opcional
    public TipoDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    // Getters y setters
    public long getId_documento() {
        return id_documento;
    }

    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}

