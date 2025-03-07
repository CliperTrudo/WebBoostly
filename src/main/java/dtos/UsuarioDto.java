package dtos;

import java.sql.Date;
import java.sql.Timestamp;

public class UsuarioDto {

    private Long id;  // ID único del usuario
    private String nombreUsuario;  // Nombre del usuario
    private String apellidosUsuario;  // Apellidos del usuario
    private String mailUsuario;  // Correo electrónico del usuario
    private Date fechaNacimientoUsuario;  // Fecha de nacimiento del usuario
    private String nicknameUsuario;  // Apodo del usuario
    private String contrasenyaUsuario;  // Contraseña encriptada del usuario
    private Date fechaAltaUsuario;  // Fecha de alta del usuario
    private String descripcionUsuario;  // Descripción del usuario
    private String dniUsuario;  // DNI del usuario
    private String telefonoUsuario;  // Teléfono del usuario
    private byte[] imgUsuario;  // Imagen del usuario (en byte[] para almacenarlo en base de datos)
    private Long rol = (long) 1;  // Rol del usuario, por defecto 1
    private Boolean googleUsuario;  // Indica si el usuario se registró con Google
    private String tokenRecuperacion;  // Token de recuperación de la contraseña
    private Timestamp tokenExpiracion;  // Fecha de expiración del token de recuperación

    // Constructor vacío
    public UsuarioDto() {}

    // Constructor con parámetros
    public UsuarioDto(Long id, String nombreUsuario, String apellidosUsuario, String mailUsuario,
                      Date fechaNacimientoUsuario, String nicknameUsuario, String contrasenyaUsuario,
                      Date fechaAltaUsuario, String descripcionUsuario, String dniUsuario, String telefonoUsuario,
                      byte[] imgUsuario, Long rol, Boolean googleUsuario, String tokenRecuperacion,
                      Timestamp tokenExpiracion) {  
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.mailUsuario = mailUsuario;
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
        this.nicknameUsuario = nicknameUsuario;
        this.contrasenyaUsuario = contrasenyaUsuario;
        this.fechaAltaUsuario = fechaAltaUsuario;
        this.descripcionUsuario = descripcionUsuario;
        this.dniUsuario = dniUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.imgUsuario = imgUsuario;
        this.rol = rol;
        this.googleUsuario = googleUsuario;
        this.tokenRecuperacion = tokenRecuperacion;
        this.tokenExpiracion = tokenExpiracion;
    }

    /**
     * Métodos Getters y Setters:
     * - getId() y setId(): Obtienen y establecen el ID del usuario.
     * - getNombreUsuario() y setNombreUsuario(): Obtienen y establecen el nombre del usuario.
     * - getApellidosUsuario() y setApellidosUsuario(): Obtienen y establecen los apellidos del usuario.
     * - getMailUsuario() y setMailUsuario(): Obtienen y establecen el correo electrónico del usuario.
     * - getFechaNacimientoUsuario() y setFechaNacimientoUsuario(): Obtienen y establecen la fecha de nacimiento del usuario.
     * - getNicknameUsuario() y setNicknameUsuario(): Obtienen y establecen el apodo del usuario.
     * - getContrasenyaUsuario() y setContrasenyaUsuario(): Obtienen y establecen la contraseña encriptada del usuario.
     * - getFechaAltaUsuario() y setFechaAltaUsuario(): Obtienen y establecen la fecha de alta del usuario.
     * - getDescripcionUsuario() y setDescripcionUsuario(): Obtienen y establecen la descripción del usuario.
     * - getDniUsuario() y setDniUsuario(): Obtienen y establecen el DNI del usuario.
     * - getTelefonoUsuario() y setTelefonoUsuario(): Obtienen y establecen el teléfono del usuario.
     * - getImgUsuario() y setImgUsuario(): Obtienen y establecen la imagen del usuario.
     * - getRol() y setRol(): Obtienen y establecen el rol del usuario.
     * - getGoogleUsuario() y setGoogleUsuario(): Obtienen y establecen si el usuario es de Google.
     * - getTokenRecuperacion() y setTokenRecuperacion(): Obtienen y establecen el token de recuperación.
     * - getTokenExpiracion() y setTokenExpiracion(): Obtienen y establecen la fecha de expiración del token de recuperación.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public String getMailUsuario() {
        return mailUsuario;
    }

    public void setMailUsuario(String mailUsuario) {
        this.mailUsuario = mailUsuario;
    }

    public Date getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(Date fechaNacimientoUsuario) {
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
    }

    public String getNicknameUsuario() {
        return nicknameUsuario;
    }

    public void setNicknameUsuario(String nicknameUsuario) {
        this.nicknameUsuario = nicknameUsuario;
    }

    public String getContrasenyaUsuario() {
        return contrasenyaUsuario;
    }

    public void setContrasenyaUsuario(String contrasenyaUsuario) {
        this.contrasenyaUsuario = contrasenyaUsuario;
    }

    public Date getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(Date fechaAltaUsuario) {
        this.fechaAltaUsuario = fechaAltaUsuario;
    }

    public String getDescripcionUsuario() {
        return descripcionUsuario;
    }

    public void setDescripcionUsuario(String descripcionUsuario) {
        this.descripcionUsuario = descripcionUsuario;
    }

    public String getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(String dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public byte[] getImgUsuario() {
        return imgUsuario;
    }

    public void setImgUsuario(byte[] imgUsuario) {
        this.imgUsuario = imgUsuario;
    }

    public Long getRol() {
        return rol;
    }

    public void setRol(Long rol) {
        this.rol = rol;
    }

    public Boolean getGoogleUsuario() {
        return googleUsuario;
    }

    public void setGoogleUsuario(Boolean googleUsuario) {
        this.googleUsuario = googleUsuario;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public Timestamp getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(Timestamp tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    /**
     * Método toString():
     * - Devuelve una representación en formato String del objeto UsuarioDto, incluyendo el ID, nombre, apellidos, correo, 
     *   fecha de nacimiento, apodo, contraseña, fecha de alta, descripción, DNI, teléfono, imagen, rol, si es usuario de Google, 
     *   el token de recuperación y su expiración.
     */
    @Override
    public String toString() {
        return "UsuariosDto{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", mailUsuario='" + mailUsuario + '\'' +
                ", fechaNacimientoUsuario=" + fechaNacimientoUsuario +
                ", nicknameUsuario='" + nicknameUsuario + '\'' +
                ", contrasenyaUsuario='" + contrasenyaUsuario + '\'' +
                ", fechaAltaUsuario=" + fechaAltaUsuario +
                ", descripcionUsuario='" + descripcionUsuario + '\'' +
                ", dniUsuario='" + dniUsuario + '\'' +
                ", telefonoUsuario='" + telefonoUsuario + '\'' +
                ", imgUsuario=" + (imgUsuario != null ? "Image in byte array" : "null") +
                ", rol=" + rol +
                ", googleUsuario=" + googleUsuario +
                ", tokenRecuperacion='" + tokenRecuperacion + '\'' +
                ", tokenExpiracion='" + tokenExpiracion + '\'' +
                '}';
    }
}
