package com.unlam.tpi.core.servicio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioDTO;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class AutenticacionServiceImpl implements AutenticacionService{
    //Fd/sUclIKu1QgBgPkrMdeuoBppm+3tK8cSH97SI0rp0=
    private static String SECRET_KEY = "Fd/sUclIKu1QgBgPkrMdeuoBppm+3tK8cSH97SI0rp0=";
    @Override
    public String GenerarTokenValidacionCuenta(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String jwt = Jwts.builder()
                .setSubject("usuario")
                .claim("accion", "tokenValidacionCuenta")
                .claim("mail", usuarioRestDTO.getEmail())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return jwt;
    }
    @Override
    public String GenerarTokenLoginUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String jwt = Jwts.builder()
                .setSubject("usuario")
                .claim("nombreUsuario", usuario.getNombreUsuario())
                .claim("nombre", usuario.getNombre())
                .claim("apellido", usuario.getEmail())
                .claim("premium", usuario.getPremium())
                .claim("email", usuario.getEmail())
                .claim("oid", usuario.getOid())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return jwt;
    }

    //Lo dejo hecho para cuando se quiera generar una nueva secret para jwt
    private String GenerarSecretJWT() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String secretKeyString = "tpi_registro";
        String salt = "salt";
        int iterations = 65536;
        int keyLength = 256;
        char[] passwordChars = secretKeyString.toCharArray();
        byte[] saltBytes = salt.getBytes();
        KeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] secretKeyBytes = factory.generateSecret(spec).getEncoded();

        String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKeyBytes);
        return secretKeyBase64;
    }

    @Override
    public JWTRestDTO ObtenerClaimsToken(String token) throws JsonProcessingException {
        JWTRestDTO jwtRestDTO = new JWTRestDTO();
        String tokenDeserializado = ObtenerBodyToken(token);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(tokenDeserializado)
                    .getBody();

            String accion = claims.get("accion", String.class);
            String mail = claims.get("mail", String.class);

            jwtRestDTO.setAccion(accion);
            jwtRestDTO.setEmailUsuario(mail);
            return jwtRestDTO;
        } catch (Exception e) {
            return null;
        }
    }

    private String ObtenerBodyToken(String token) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(token);
        return jsonNode.get("token").asText();
    }
	@Override
	public UsuarioDTO obtenerDatosUsuarioByToken(String token) throws JsonProcessingException {
		UsuarioDTO usuario = new UsuarioDTO();
        
        try {
            Claims claims = Jwts.parser()
            		.setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            usuario.setOid(claims.get("oid", Long.class));
            usuario.setEmail(claims.get("email", String.class));
            usuario.setNombreUsuario(claims.get("nombreUsuario", String.class));
            usuario.setNombre(claims.get("nombre", String.class));
            usuario.setApellido(claims.get("apellido", String.class));
            usuario.setPremium(claims.get("premium", Boolean.class));
           
            return usuario;
        } catch (Exception e) {
            return null;
        }
	}


}
