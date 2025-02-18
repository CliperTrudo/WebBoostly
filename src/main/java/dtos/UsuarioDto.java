package dtos;

import java.time.LocalDate;
import java.util.Arrays;

public class UsuarioDto {

    private Long id;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String mailUsuario;
    private LocalDate fechaNacimientoUsuario;
    private String nicknameUsuario;
    private String contrasenyaUsuario;
    private LocalDate fechaAltaUsuario;
    private String descripcionUsuario;
    private String dniUsuario;
    private String telefonoUsuario;
    private byte[] imgUsuario;
    private String rolUsuario;
    private Boolean googleUsuario;
    private String tokenRecuperacion;
    private String tokenExpiracion;

    // Constructor vacío
    public UsuarioDto() {
    }

    // Constructor con parámetros
    public UsuarioDto(Long id, String nombreUsuario, String apellidosUsuario, String mailUsuario,
                      LocalDate fechaNacimientoUsuario, String nicknameUsuario, String contrasenyaUsuario,
                      LocalDate fechaAltaUsuario, String descripcionUsuario, String dniUsuario, String telefonoUsuario,
                      byte[] imgUsuario, String rolUsuario, Boolean googleUsuario, String tokenRecuperacion,
                      String tokenExpiracion) {
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
        this.rolUsuario = rolUsuario;
        this.googleUsuario = googleUsuario;
        this.tokenRecuperacion = tokenRecuperacion;
        this.tokenExpiracion = tokenExpiracion;
    }

    // Getters y Setters

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

    public LocalDate getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(LocalDate fechaNacimientoUsuario) {
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

    public LocalDate getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(LocalDate fechaAlta) {
        this.fechaAltaUsuario = fechaAlta;
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

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
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

    public String getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(String tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    @Override
    public String toString() {
        return "UsuarioDto{" +
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
                ", imgUsuario=" + (imgUsuario != null ? Arrays.toString(imgUsuario) : "null") +
                ", rolUsuario='" + rolUsuario + '\'' +
                ", googleUsuario=" + googleUsuario +
                ", tokenRecuperacion='" + tokenRecuperacion + '\'' +
                ", tokenExpiracion='" + tokenExpiracion + '\'' +
                '}';
    }
}
