package dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

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

    /**
     * Métodos Getters y Setters:
     * - getIdProyecto() y setIdProyecto(): Obtienen y establecen el ID del proyecto.
     * - getIdUsuario() y setIdUsuario(): Obtienen y establecen el ID del usuario asignado al proyecto.
     * - getNombreProyecto() y setNombreProyecto(): Obtienen y establecen el nombre del proyecto.
     * - getDescripcionProyecto() y setDescripcionProyecto(): Obtienen y establecen la descripción del proyecto.
     * - getImagen1Proyecto(), getImagen2Proyecto(), getImagen3Proyecto(): Métodos para obtener y establecer las imágenes del proyecto.
     * - getFechaInicioProyecto() y setFechaInicioProyecto(): Obtienen y establecen la fecha de inicio del proyecto.
     * - getFechaFinalizacionProyecto() y setFechaFinalizacionProyecto(): Obtienen y establecen la fecha de finalización del proyecto.
     * - getMetaRecaudacionProyecto() y setMetaRecaudacionProyecto(): Obtienen y establecen la meta de recaudación.
     * - getEstadoProyecto() y setEstadoProyecto(): Obtienen y establecen el estado del proyecto (activo o inactivo).
     * - getIdCategoria() y setIdCategoria(): Obtienen y establecen el ID de la categoría del proyecto.
     */
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

    /**
     * Método toString():
     * - Devuelve una representación en formato String de todos los atributos del proyecto, incluyendo el ID, nombre, descripción, imágenes, fechas, etc.
     */
    @Override
    public String toString() {
        return "ProyectoDto [idProyecto=" + idProyecto + ", idUsuario=" + idUsuario + ", nombreProyecto=" 
                + nombreProyecto + ", descripcionProyecto=" + descripcionProyecto + ", imagen1Proyecto=" 
                + Arrays.toString(imagen1Proyecto) + ", imagen2Proyecto=" + Arrays.toString(imagen2Proyecto) 
                + ", imagen3Proyecto=" + Arrays.toString(imagen3Proyecto) + ", fechaInicioProyecto=" 
                + fechaInicioProyecto + ", fechaFinalizacionProyecto=" + fechaFinalizacionProyecto 
                + ", metaRecaudacionProyecto=" + metaRecaudacionProyecto + ", estadoProyecto=" + estadoProyecto 
                + ", idCategoria=" + idCategoria + "]";
    }
}
