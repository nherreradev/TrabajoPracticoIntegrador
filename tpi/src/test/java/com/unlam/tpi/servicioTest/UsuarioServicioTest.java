package com.unlam.tpi.servicioTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.interfaces.UsuarioRepositorio;
import com.unlam.tpi.core.modelo.ResponseAPI;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.core.servicio.UsuarioServicioImpl;
import com.unlam.tpi.delivery.dto.UsuarioRest;
import com.unlam.tpi.infraestructura.api.MailServicioImpl;

@ExtendWith(MockitoExtension.class)
public class UsuarioServicioTest {
 
	//Usuario existente
    Usuario USUARIO_EXISTENTE = new Usuario("Usuario_Prueba", "Mercado", "Junior","Test@Test.com", "1234", Boolean.TRUE, Boolean.TRUE, "", Boolean.FALSE);
    //Usuario no existente
    Usuario USUARIO_EXISTENTE_NO_CONFIRMADO = new Usuario("Usuario_Prueba-2", "Mercado", "Junior","Test2@Test.com", "1234", Boolean.FALSE, Boolean.FALSE, "", Boolean.FALSE);
    
    UsuarioRest USUARIO_REST = new UsuarioRest("Usuario_Prueba-2", "Mercado", "Junior","Test2@Test.com", "1234");
    
    String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvIiwiYWNjaW9uIjoidG9rZW5WYWxpZGFjaW9uQ3VlbnRhIiwibWFpbCI6InRvbWFzaW5pYXJuYWxkb0BnbWFpbC5jb20ifQ.F2zZjDlJx_rygnBOWXhDvdhWm-cluaR1wxmjALRmFvc";
  
    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private AutenticacionService autenticacionService;

    @Mock
    private MailServicioImpl mailServicio;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;
    
    @Test
    public void quieroRegistrarUnNuevoUsuarioEnMiSistemaPeroElMismoYaExiste(){
        when(usuarioRepositorio.existsByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(Boolean.TRUE);
        Boolean existe = this.usuarioServicio.existeEmail(USUARIO_EXISTENTE.getEmail());
        assertEquals(Boolean.TRUE, existe);
    }

    @Test
    public void deberiaGuardarUsuarioExitosamente() throws NoSuchAlgorithmException, InvalidKeySpecException {
        when(autenticacionService.generarTokenValidacionCuenta(USUARIO_EXISTENTE_NO_CONFIRMADO.getEmail())).thenReturn(TOKEN);
        usuarioServicio.guardarUsuario(USUARIO_EXISTENTE_NO_CONFIRMADO);
        verify(usuarioRepositorio, times(1)).save(USUARIO_EXISTENTE_NO_CONFIRMADO);
        verify(mailServicio, times(1)).prepararMailYEnviar(USUARIO_EXISTENTE_NO_CONFIRMADO, TOKEN);
    }

    @Test
    public void quieroBuscarUnUsuarioPorSuEmailYLoEncuentro(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(USUARIO_EXISTENTE);
        Usuario usuario = this.usuarioServicio.obtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        assertNotNull(usuario);
    }

    @Test
    public void quieroBuscarUnUsuarioPorSuEmailPeroElUsuarioNoExistePorLoTantoObtendreUnResultadoNulo(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(null);
        Usuario usuario = this.usuarioServicio.obtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        assertNull(usuario);
    }

    @Test
    public void quieroActualizarUnUsuarioExistente(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(USUARIO_EXISTENTE);
        Usuario usuario = this.usuarioServicio.obtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        usuario.setNombreUsuario("Nombre_modificado");
        usuario.setPass("Contraseña_modificada");
        this.usuarioServicio.modificarUsuario(usuario);
        assertNotNull(usuario);
        assertEquals("Nombre_modificado", usuario.getNombreUsuario());
        assertEquals("Contraseña_modificada", usuario.getPass());
    }

    @Test
    public void quieroActualizarUnUsuarioPeroNoExiste(){
        Usuario modificado = USUARIO_EXISTENTE_NO_CONFIRMADO;
        modificado.setNombre("NombreUpdate");
        modificado.setApellido("ApellidoUpdate");
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE_NO_CONFIRMADO.getEmail())).thenReturn(null);
        ResponseAPI r = this.usuarioServicio.modificarUsuario(modificado);
        assertEquals(HttpStatus.NOT_FOUND, r.getStatus());
    }

    @Test
    public void quieroDarDeBajaUnUsuarioExistente(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(USUARIO_EXISTENTE);
        when(usuarioRepositorio.existsByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(Boolean.TRUE);
        Usuario modificado = this.usuarioServicio.obtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        modificado.setActivo(Boolean.FALSE);
        modificado.setCuentaConfirmada(Boolean.FALSE);
        ResponseAPI res =  this.usuarioServicio.DarDeBajaUsuario(modificado);
        assertNotNull(modificado);
        assertEquals(res.getStatus(), HttpStatus.OK);
    }

    @Test
    public void quieroDarDeBajaUnUsuarioPeroNoExiste(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(null);
        Usuario modificado = this.usuarioServicio.obtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        assertNull(modificado);
    }

    @Test
    public void unaVezQueRegistroUsuarioConfirmoCuenta(){
        when(usuarioRepositorio.getUsuarioByEmail(USUARIO_EXISTENTE.getEmail())).thenReturn(USUARIO_EXISTENTE);
        Usuario buscado = this.usuarioServicio.obtenerUsuarioPorEmail(USUARIO_EXISTENTE.getEmail());
        buscado.setCuentaConfirmada(Boolean.TRUE);
        this.usuarioServicio.confirmarCuenta(buscado);
        this.usuarioServicio.obtenerUsuarioPorEmail(buscado.getEmail());
        assertEquals(buscado.getCuentaConfirmada(), Boolean.TRUE);
    }

}
