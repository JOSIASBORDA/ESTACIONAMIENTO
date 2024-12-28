package com.estacionamiento.estacionamiento.Entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "numDocumento"))
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id_usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    // Relación con TipoDocumento (clave foránea id_documento)
    @ManyToOne
    @JoinColumn(name = "id_documento")
    private TipoDocumento tipoDocumento;
    
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Roles roles;

    @Column(name = "numDocumento")
    private String numDocumento;

    @Column(name = "clave")
    private String clave;
    
    // Relación con los registros de vehículos (Un usuario puede tener múltiples registros)
    @OneToMany(mappedBy = "usuario")  // "usuario" es el nombre del campo en la clase RegistroVehiculos que referencia a Usuario
    private List<RegistroVehiculos> registroVehiculos;
    
    // Implementación de métodos de UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles.getRol()));
    }

    @Override
    public String getPassword() {
        return this.clave;  // Devuelve la contraseña
    }

    @Override
    public String getUsername() {
        return this.numDocumento;  // Devuelve el número de documento como el nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // No hay expiración de cuenta en este caso
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // No bloqueamos la cuenta
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // No expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return true; // La cuenta siempre está habilitada
    }

}