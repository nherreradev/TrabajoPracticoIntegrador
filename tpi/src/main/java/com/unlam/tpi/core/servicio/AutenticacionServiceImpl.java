package com.unlam.tpi.core.servicio;

import com.unlam.tpi.core.modelo.Usuario;
import com.unlam.tpi.delivery.dto.UsuarioRestDTO;
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
    @Override
    public String GenerarTokenValidacionCuenta(UsuarioRestDTO usuarioRestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String secret = GenerarSecretJWT();
        String jwt = Jwts.builder()
                .setSubject("usuario")
                .claim("accion", "tokenValidacionCuenta")
                .claim("mail", usuarioRestDTO.getEmail())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return jwt;
    }

    private String GenerarSecretJWT() throws NoSuchAlgorithmException, InvalidKeySpecException {
        /*String secretKeyString = "tpi_registro";
        byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyString);
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");

        return secretKeyString;*/
        String secretKeyString = "tpi_registro";
        String salt = "salt"; // Debe ser único por cada aplicación
        int iterations = 65536; // Número de iteraciones
        int keyLength = 256; // Tamaño de clave en bits

        char[] passwordChars = secretKeyString.toCharArray();
        byte[] saltBytes = salt.getBytes();

        KeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] secretKeyBytes = factory.generateSecret(spec).getEncoded();

        // Codificar la clave secreta en Base64
        String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKeyBytes);
        return secretKeyBase64;
    }

    @Override
    public String ObtenerClaimsToken(Usuario usuario) {
        return null;
    }
    
}
