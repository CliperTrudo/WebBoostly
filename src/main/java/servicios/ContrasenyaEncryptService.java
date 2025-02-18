package servicios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ContrasenyaEncryptService {

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }
    
    

    public static boolean matches(String plainPassword, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(plainPassword, hashedPassword);
    }
}
