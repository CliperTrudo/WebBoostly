package dtos;

import java.io.Serializable;

/**
 * DTO para representar un Rol de usuario.
 */
public class RolDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idRol;
    private String nombreRol;
    private String descripcionRol;

    public RolDto() {
        // Constructor vacío
    }

    public RolDto(Long idRol, String nombreRol, String descripcionRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    @Override
    public String toString() {
        return "RolDto{" +
               "idRol=" + idRol +
               ", nombreRol='" + nombreRol + '\'' +
               ", descripcionRol='" + descripcionRol + '\'' +
               '}';
    }
}
