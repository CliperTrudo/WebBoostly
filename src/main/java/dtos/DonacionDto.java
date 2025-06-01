package dtos;

import java.time.LocalDateTime;

/**
 * DonacionDto
 *
 * DTO (Data Transfer Object) para la entidad Donaciones.
 * Contiene los campos que necesitamos exponer/recibir en la capa REST
 * sin exponer directamente la entidad JPA.
 */
public class DonacionDto {

    private Long idDonacion;
    private Long usuarioId;
    private Long proyectoId;
    private String authorizationId;
    private String orderId;
    private Double amount;
    private String estado;
    private LocalDateTime fechaCreacion;

    public DonacionDto() {
        // Constructor vacío
    }

    /**
     * Constructor con todos los campos.
     */
    public DonacionDto(Long idDonacion, Long usuarioId, Long proyectoId, String authorizationId,
                       String orderId, Double amount, String estado, LocalDateTime fechaCreacion) {
        this.idDonacion = idDonacion;
        this.usuarioId = usuarioId;
        this.proyectoId = proyectoId;
        this.authorizationId = authorizationId;
        this.orderId = orderId;
        this.amount = amount;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters

    public Long getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(Long idDonacion) {
        this.idDonacion = idDonacion;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "DonacionDto{" +
                "idDonacion=" + idDonacion +
                ", usuarioId=" + usuarioId +
                ", proyectoId=" + proyectoId +
                ", authorizationId='" + authorizationId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
