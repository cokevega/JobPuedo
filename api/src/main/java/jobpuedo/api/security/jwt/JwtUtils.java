package jobpuedo.api.security.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jobpuedo.api.entity.User;
import jobpuedo.api.exception.NoAuthenticatedException;
import jobpuedo.api.security.user.UserDetailsImpl;
import jobpuedo.api.service.UserService;

@Component
public class JwtUtils {

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jobPuedo.app.jwtSecret}")
	private String jwtSecret;

	@Value("${jobPuedo.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public int getIdFromRequest(HttpServletRequest req) {
		if (req.getHeader("Authorization") != null && !req.getHeader("Authorization").isBlank()) {
			return getIdFromJwtToken(getTokenFromRequest(req));
		} else
			throw new NoAuthenticatedException("Petición recibida sin autenticación");
	}

	public int getIdFromJwtToken(String token) {
		return userService.findByEmail(this.getUserNameFromJwtToken(token)).getId();
	}

	public User getUserFromtoken(HttpServletRequest req) {
		if (req.getHeader("Authorization") != null && !req.getHeader("Authorization").isBlank()) {
			int id = getIdFromJwtToken(getTokenFromRequest(req));
			User user = userService.findById(id);
			return user;
		} else {
			throw new NoAuthenticatedException("No se ha recibido autenticación del usuario");
		}
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Firma del token inválida: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Token inválido: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("El token ha expirado: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Token no soportado: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Contenido del token incorrecto: {}", e.getMessage());
		}

		return false;
	}

	public String getTokenFromRequest(HttpServletRequest request) {
		if (request.getHeader("Authorization") != null && !request.getHeader("Authorization").isBlank()) {
			return request.getHeader("Authorization").substring(7);
		} else
			throw new NoAuthenticatedException("No se ha recibido autenticación del usuario");
	}
}
