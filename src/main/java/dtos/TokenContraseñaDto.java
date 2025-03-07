package dtos;

import java.time.Instant;

public class TokenContraseñaDto {
    private String email;  // Correo electrónico del usuario para el que se está restableciendo la contraseña
    private String tokenRecuperacion;  // Token único generado para la recuperación de la contraseña
    private String nuevaContrasena;  // Nueva contraseña que el usuario desea establecer
    private Instant fechaExpiracion;  // Fecha de expiración del token

    /**
     * Método toString():
     * - Devuelve una representación en formato String del objeto TokenContraseñaDto, incluyendo el email, el token de recuperación, 
     *   la nueva contraseña y la fecha de expiración.
     */
    @Override
    public String toString() {
        return "TokenContraseñaDto [email=" + email + ", tokenRecuperacion=" + tokenRecuperacion + ", nuevaContrasena="
                + nuevaContrasena + ", fechaExpiracion=" + fechaExpiracion + "]";
    }

    /**
     * Métodos Getters y Setters:
     * - getEmail() y setEmail(): Obtienen y establecen el correo electrónico del usuario.
     * - getTokenRecuperacion() y setTokenRecuperacion(): Obtienen y establecen el token de recuperación.
     * - getNuevaContrasena() y setNuevaContrasena(): Obtienen y establecen la nueva contraseña.
     * - getFechaExpiracion() y setFechaExpiracion(): Obtienen y establecen la fecha de expiración del token.
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public Instant getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Instant fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
