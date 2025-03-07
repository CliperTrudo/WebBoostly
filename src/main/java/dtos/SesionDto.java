package dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  // Ignora propiedades desconocidas durante el proceso de deserialización
public class SesionDto {

    Long id;
    String mailUsuario = "aaaaa";  // Dirección de correo electrónico del usuario
    long rolUsuario = 99999;  // Rol del usuario (por defecto asignado a 99999)

    // Constructor vacío
    public SesionDto() {
        super();
    }

    // Constructor con parámetros
    public SesionDto(Long id, String mailUsuario, long rolUsuario) {
        super();
        this.id = id;
        this.mailUsuario = mailUsuario;
        this.rolUsuario = rolUsuario;
    }

    /**
     * Métodos Getters y Setters:
     * - getId() y setId(): Obtienen y establecen el ID del usuario en sesión.
     * - getMailUsuario() y setMailUsuario(): Obtienen y establecen el correo electrónico del usuario.
     * - getRolUsuario() y setRolUsuario(): Obtienen y establecen el rol del usuario en sesión.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailUsuario() {
        return mailUsuario;
    }

    public void setMailUsuario(String mailUsuario) {
        this.mailUsuario = mailUsuario;
    }

    public long getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(long rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    /**
     * Método toString():
     * - Devuelve una representación en formato String del objeto SesionDto, incluyendo el ID, correo y rol del usuario.
     */
    @Override
    public String toString() {
        return "sesionDto [id=" + id + ", mailUsuario=" + mailUsuario + ", rolUsuario=" + rolUsuario + "]";
    }

}
