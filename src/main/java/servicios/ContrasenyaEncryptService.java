package servicios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase encargada de la encriptación y verificación de contraseñas utilizando el algoritmo BCrypt.
 * Esta clase proporciona métodos para encriptar contraseñas en texto plano y para comparar contraseñas en texto plano con contraseñas encriptadas.
 */
public class ContrasenyaEncryptService {

    // Instancia del codificador BCrypt para la encriptación de contraseñas
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encripta una contraseña en texto plano utilizando el algoritmo BCrypt.
     * @param plainPassword La contraseña en texto plano a encriptar.
     * @return La contraseña encriptada en formato BCrypt.
     */
    public static String encryptPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    /**
     * Compara una contraseña en texto plano con una contraseña encriptada utilizando BCrypt.
     * @param plainPassword La contraseña en texto plano.
     * @param hashedPassword La contraseña encriptada.
     * @return true si las contraseñas coinciden, false en caso contrario.
     */
    public static boolean matches(String plainPassword, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(plainPassword, hashedPassword);
    }
}
