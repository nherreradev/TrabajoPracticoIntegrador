package com.unlam.tpi;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.unlam.tpi.modelo.persistente.Usuario;
import com.unlam.tpi.modelo.rest.ResponseAPI;
import com.unlam.tpi.repositorio.UsuarioRepositorio;
import com.unlam.tpi.servicio.UsuarioServicioImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class UsuarioServicioTest {
    //Usuario existente
    Usuario USUARIO_EXISTENTE = new Usuario("Usuario_Prueba", "Mercado", "Junior","Test@Test.com", "1234", Boolean.TRUE, Boolean.TRUE);
    //Usuario no existente
    Usuario USUARIO_NO_EXISTENTE = new Usuario("Usuario_Prueba-2", "Mercado", "Junior","Test2@Test.com", "1234", Boolean.FALSE, Boolean.FALSE);
    @InjectMocks
    private UsuarioServicioImpl usuarioServicio = new UsuarioServicioImpl();
    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    public void QuieroRegistrarUnNuevoUsuarioEnMiSistemaPeroElMismoYaExiste(){
        when(usuarioRepositorio.existsByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(Boolean.TRUE);
        Boolean existe = this.usuarioServicio.ExisteUsuario(USUARIO_EXISTENTE);
        assertTrue(existe);
    }

    @Test
    public void QuieroRegistrarUnNuevoUsuarioEnMiSistemaYLoRegistroExitosamente(){
        when(usuarioRepositorio.existsByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(Boolean.FALSE);
        Boolean existe = this.usuarioServicio.ExisteUsuario(USUARIO_EXISTENTE);
        assertFalse(existe);
    }

    @Test
    public void QuieroBuscarUnUsuarioPorSuEmailYLoEncuentro(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(USUARIO_NO_EXISTENTE);
        Usuario usuario = this.usuarioServicio.ObtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        assertNotNull(usuario);
    }

    @Test
    public void QuieroBuscarUnUsuarioPorSuEmailPeroElUsuarioNoExistePorLoTantoObtendreUnResultadoNulo(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(null);
        Usuario usuario = this.usuarioServicio.ObtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        assertNull(usuario);
    }

    @Test
    public void QuieroActualizarUnUsuarioExistente(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(USUARIO_EXISTENTE);
        Usuario usuario = this.usuarioServicio.ObtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        usuario.setNombreUsuario("Nombre_modificado");
        usuario.setPass("Contraseña_modificada");
        this.usuarioServicio.ModificarUsuario(usuario);
        assertNotNull(usuario);
        assertEquals("Nombre_modificado", usuario.getNombreUsuario());
        assertEquals("Contraseña_modificada", usuario.getPass());
    }

    @Test
    public void QuieroActualizarUnUsuarioPeroNoExiste(){
        Usuario modificado = USUARIO_EXISTENTE;
        modificado.setNombre("NombreUpdate");
        modificado.setApellido("ApellidoUpdate");
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_NO_EXISTENTE.getEmail())).thenReturn(null);
        ResponseAPI r = this.usuarioServicio.ModificarUsuario(modificado);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatus());
    }


}
