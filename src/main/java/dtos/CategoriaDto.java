package dtos;

public class CategoriaDto {

    private Long idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;

    // Constructor vacío
    public CategoriaDto() {}

    // Constructor con parámetros
    public CategoriaDto(Long idCategoria, String nombreCategoria, String descripcionCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    /**
     * Métodos Getters y Setters:
     * - getIdCategoria() y setIdCategoria(): Obtienen y establecen el ID de la categoría.
     * - getNombreCategoria() y setNombreCategoria(): Obtienen y establecen el nombre de la categoría.
     * - getDescripcionCategoria() y setDescripcionCategoria(): Obtienen y establecen la descripción de la categoría.
     */
    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    /**
     * Método toString():
     * - Devuelve una representación en formato String de la categoría DTO, incluyendo el ID, nombre y descripción de la categoría.
     */
    @Override
    public String toString() {
        return "CategoriaDto{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcionCategoria='" + descripcionCategoria + '\'' +
                '}';
    }
}
