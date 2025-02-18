package dtos;

public class MailContrasenyaRequestDto {
    private String mail;
    private String contrasenya;
    
	public MailContrasenyaRequestDto(String mail, String contrasenya) {
		super();
		this.mail = mail;
		this.contrasenya = contrasenya;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

    
	
    
    
}

