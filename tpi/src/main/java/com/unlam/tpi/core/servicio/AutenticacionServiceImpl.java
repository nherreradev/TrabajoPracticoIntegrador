package com.unlam.tpi.core.servicio;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlam.tpi.core.interfaces.AutenticacionService;
import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.JWTRestDTO;
import com.unlam.tpi.delivery.dto.UsuarioDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

	private static final int MAXIMO_SOPORTADO = 255;
	// Fd/sUclIKu1QgBgPkrMdeuoBppm+3tK8cSH97SI0rp0=
	private static String SECRET_KEY = "Fd/sUclIKu1QgBgPkrMdeuoBppm+3tK8cSH97SI0rp0=";

	@Override
	public String GenerarTokenValidacionCuenta(String email)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String jwt = Jwts.builder().setSubject("usuario").claim("accion", "tokenValidacionCuenta")
				.claim("mail", email).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
		return jwt;
	}

	@Override
	public String generarTokenLoginUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String jwt = Jwts.builder().setSubject("usuario").claim("nombreUsuario", usuario.getNombreUsuario())
				.claim("nombre", usuario.getNombre()).claim("apellido", usuario.getEmail())
				.claim("premium", usuario.getPremium()).claim("email", usuario.getEmail())
				.claim("oid", usuario.getOid()).claim("pass", usuario.getPass())
				.claim("esAdministrador", usuario.getEsAdministrador()).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
		return jwt;
	}

	
	@Override
	public String GenerarSecretJWT() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String secretKeyString = "tpi_registro";
		String salt = UUID.randomUUID().toString();
		int iterations = 65536;
		int keyLength = 255;
		char[] passwordChars = secretKeyString.toCharArray();
		byte[] saltBytes = salt.getBytes();
		KeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] secretKeyBytes = factory.generateSecret(spec).getEncoded();
		String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKeyBytes);
		if (secretKeyBase64.length() > MAXIMO_SOPORTADO) {
			return secretKeyBase64.substring(0, 255);
		}
		return secretKeyBase64;
	}

	@Override
	public JWTRestDTO obtenerClaimsToken(String token) throws JsonProcessingException {
		JWTRestDTO jwtRestDTO = new JWTRestDTO();
		String tokenDeserializado = ObtenerBodyToken(token);
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(tokenDeserializado).getBody();

		String accion = claims.get("accion", String.class);
		String mail = claims.get("mail", String.class);
		Boolean estaValidado = claims.get("validado", Boolean.class);
		String password = claims.get("pass", String.class);
		jwtRestDTO.setAccion(accion);
		jwtRestDTO.setEmailUsuario(mail);
		jwtRestDTO.setEstaValidado(estaValidado);
		jwtRestDTO.setPass(password);
		return jwtRestDTO;
	}

	private String ObtenerBodyToken(String token) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(token);
		return jsonNode.get("token").asText();
	}

	@Override
	public UsuarioDTO obtenerDatosUsuarioByToken(String token) throws JsonProcessingException {
		UsuarioDTO usuario = new UsuarioDTO();
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

		usuario.setOid(claims.get("oid", Long.class));
		usuario.setEmail(claims.get("email", String.class));
		usuario.setNombreUsuario(claims.get("nombreUsuario", String.class));
		usuario.setNombre(claims.get("nombre", String.class));
		usuario.setApellido(claims.get("apellido", String.class));
		usuario.setPremium(claims.get("premium", Boolean.class));
		usuario.setEsAdministrador(claims.get("esAdministrador", Boolean.class));
		return usuario;
	}

}