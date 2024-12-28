package com.estacionamiento.estacionamiento.Config;

import com.estacionamiento.estacionamiento.Respository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByNumDocumento(username)
                .map(usuario -> org.springframework.security.core.userdetails.User
                        .withUsername(usuario.getNumDocumento())
                        .password(usuario.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF si no lo necesitas (por ejemplo, para APIs REST)
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/css/**", "/img/**", "/js/**", "/scss/**", "/vendor/**").permitAll()  // Archivos estáticos sin autenticación
                .requestMatchers("/login", "/logout","/registrarpago").permitAll()  // Login sin autenticación
                .requestMatchers("/index","/vehiculos_add","/usuarios","/usuarios/nuevo", "/usuarios_add", "/usuarios_edit", "/sitiosconfig", "/tarifas", "/reportesventa").authenticated()  // Rutas protegidas
                .anyRequest().authenticated()  // Rutas no especificadas requieren autenticación
            )
            .formLogin((form) -> form
                .loginPage("/login")  // Página personalizada de login
                .defaultSuccessUrl("/index", true)  // Redirigir después de un login exitoso
                .failureUrl("/login?error=true")  // Redirigir con mensaje de error
                .permitAll()  // Permitir acceso a todos a la página de login
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")  // Redirigir después de logout
                .permitAll()  // Permitir acceso a logout
            )
            .sessionManagement(session -> 
                session
                    .maximumSessions(1)  // Limitar a 1 sesión activa por usuario
                    .expiredUrl("/login?expired=true")  // Redirigir cuando la sesión haya expirado
                    .sessionRegistry(sessionRegistry())  // Usar el SessionRegistry para gestionar sesiones
                    
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para encriptar contraseñas
    }
}
