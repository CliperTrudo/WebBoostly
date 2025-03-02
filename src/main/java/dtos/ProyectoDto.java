package dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProyectoDto {

    private Long idProyecto;  
    private Long idUsuario;  // ID del usuario asignado al proyecto
    private String nombreProyecto;
    private String descripcionProyecto;
    private byte[] imagen1Proyecto;
    private byte[] imagen2Proyecto;
    private byte[] imagen3Proyecto;
    private LocalDateTime fechaInicioProyecto;
    private LocalDate fechaFinalizacionProyecto;
    private Double metaRecaudacionProyecto;
    private Boolean estadoProyecto;
    private Long idCategoria;  // ID de la categoría, no la entidad completa

    // Constructor vacío
    public ProyectoDto() {}

    // Constructor con parámetros
    public ProyectoDto(Long idProyecto, Long idUsuario, String nombreProyecto, String descripcionProyecto,
                       byte[] imagen1Proyecto, byte[] imagen2Proyecto, byte[] imagen3Proyecto,
                       LocalDateTime fechaInicioProyecto, LocalDate fechaFinalizacionProyecto,
                       Double metaRecaudacionProyecto, Boolean estadoProyecto, Long idCategoria) {
        this.idProyecto = idProyecto;
        this.idUsuario = idUsuario;
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.imagen1Proyecto = imagen1Proyecto;
        this.imagen2Proyecto = imagen2Proyecto;
        this.imagen3Proyecto = imagen3Proyecto;
        this.fechaInicioProyecto = fechaInicioProyecto;
        this.fechaFinalizacionProyecto = fechaFinalizacionProyecto;
        this.metaRecaudacionProyecto = metaRecaudacionProyecto;
        this.estadoProyecto = estadoProyecto;
        this.idCategoria = idCategoria; // Ahora es el ID de la categoría
    }

    // Getters y Setters

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public byte[] getImagen1Proyecto() {
        return imagen1Proyecto;
    }

    public void setImagen1Proyecto(byte[] imagen1Proyecto) {
        this.imagen1Proyecto = imagen1Proyecto;
    }

    public byte[] getImagen2Proyecto() {
        return imagen2Proyecto;
    }

    public void setImagen2Proyecto(byte[] imagen2Proyecto) {
        this.imagen2Proyecto = imagen2Proyecto;
    }

    public byte[] getImagen3Proyecto() {
        return imagen3Proyecto;
    }

    public void setImagen3Proyecto(byte[] imagen3Proyecto) {
        this.imagen3Proyecto = imagen3Proyecto;
    }

    public LocalDateTime getFechaInicioProyecto() {
        return fechaInicioProyecto;
    }

    public void setFechaInicioProyecto(LocalDateTime fechaInicioProyecto) {
        this.fechaInicioProyecto = fechaInicioProyecto;
    }

    public LocalDate getFechaFinalizacionProyecto() {
        return fechaFinalizacionProyecto;
    }

    public void setFechaFinalizacionProyecto(LocalDate fechaFinalizacionProyecto) {
        this.fechaFinalizacionProyecto = fechaFinalizacionProyecto;
    }

    public Double getMetaRecaudacionProyecto() {
        return metaRecaudacionProyecto;
    }

    public void setMetaRecaudacionProyecto(Double metaRecaudacionProyecto) {
        this.metaRecaudacionProyecto = metaRecaudacionProyecto;
    }

    public Boolean getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(Boolean estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "ProyectoDto{" +
                "idProyecto=" + idProyecto +
                ", idUsuario=" + idUsuario +
                ", nombreProyecto='" + nombreProyecto + '\'' +
                ", descripcionProyecto='" + descripcionProyecto + '\'' +
                ", fechaInicioProyecto=" + fechaInicioProyecto +
                ", fechaFinalizacionProyecto=" + fechaFinalizacionProyecto +
                ", metaRecaudacionProyecto=" + metaRecaudacionProyecto +
                ", estadoProyecto=" + estadoProyecto +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
