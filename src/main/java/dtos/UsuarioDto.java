package dtos;

import java.sql.Date;
import java.sql.Timestamp;

public class UsuarioDto {

    private Long id;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String mailUsuario;
    private Date fechaNacimientoUsuario;
    private String nicknameUsuario;
    private String contrasenyaUsuario;
    private Date fechaAltaUsuario;
    private String descripcionUsuario;
    private String dniUsuario;
    private String telefonoUsuario;
    private byte[] imgUsuario;  
    private Long rol;  
    private Boolean googleUsuario;
    private String tokenRecuperacion;
    private Timestamp tokenExpiracion;  // Cambiar a Timestamp

    // Constructor vacío
    public UsuarioDto() {}

    // Constructor con parámetros
    public UsuarioDto(Long id, String nombreUsuario, String apellidosUsuario, String mailUsuario,
                      Date fechaNacimientoUsuario, String nicknameUsuario, String contrasenyaUsuario,
                      Date fechaAltaUsuario, String descripcionUsuario, String dniUsuario, String telefonoUsuario,
                      byte[] imgUsuario, Long rol, Boolean googleUsuario, String tokenRecuperacion,
                      Timestamp tokenExpiracion) {  // Cambiar a Timestamp
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
