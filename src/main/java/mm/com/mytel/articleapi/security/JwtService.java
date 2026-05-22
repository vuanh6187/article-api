package mm.com.mytel.articleapi.security;

public interface JwtService {

    String generateToken(String email);

    String extractEmail(String token);

    boolean isTokenValid(String token);
}
