package com.unlam.tpi.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.infraestructura.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    Boolean existsByEmail(String email);
    Usuario getUsuarioByEmail(String email);
    
    public Usuario findByNombreUsuario(String nombreUsuario);
}
