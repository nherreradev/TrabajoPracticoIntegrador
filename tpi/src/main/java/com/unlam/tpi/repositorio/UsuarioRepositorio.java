package com.unlam.tpi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.modelo.persistente.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    Boolean existsByEmail(String email);
    Usuario getUsuarioByEmail(String email);
    
    public Usuario findByNombreUsuario(String nombreUsuario);
}
