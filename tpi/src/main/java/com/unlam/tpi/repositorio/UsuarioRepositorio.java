package com.unlam.tpi.repositorio;

import com.unlam.tpi.modelo.persistente.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    Boolean existsByEmail(String email);
    Usuario getUsuarioByEmail(String email);
}
