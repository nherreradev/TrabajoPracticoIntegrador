package com.unlam.tpi.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unlam.tpi.core.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    Boolean existsByEmail(String email);
    Boolean existsByNombreUsuario(String nombreUsuario);
    Usuario getUsuarioByEmail(String email);
    public Usuario findByNombreUsuario(String nombreUsuario);
    public Usuario findByEmailAndPass(String email, String password);

}
