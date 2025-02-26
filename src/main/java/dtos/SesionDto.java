package dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SesionDto {

	Long id;
	String mailUsuario = "aaaaa";
	long rolUsuario = 99999;

	public SesionDto() {
		super();
	}

	public SesionDto(Long id, String mailUsuario, long rolUsuario) {
		super();
		this.id = id;
		this.mailUsuario = mailUsuario;
		this.rolUsuario = rolUsuario;
	}

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

	@Override
	public String toString() {
		return "sesionDto [id=" + id + ", mailUsuario=" + mailUsuario + ", rolUsuario=" + rolUsuario + "]";
	}

}
