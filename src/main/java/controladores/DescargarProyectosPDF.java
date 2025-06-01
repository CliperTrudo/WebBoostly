package controladores;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dtos.ProyectoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ApiService;

/**
 * Servlet que genera un PDF con todos los proyectos y lo envía como descarga.
 */
@WebServlet("/descargarProyectosPDF")
public class DescargarProyectosPDF extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ApiService apiService = new ApiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1) Traer lista completa de proyectos desde la API
        List<ProyectoDto> proyectos = apiService.obtenerProyectos();

        // 2) Configurar la respuesta HTTP como PDF descargable
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"proyectos.pdf\"");

        // 3) Iniciar el documento iText
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // 4) Añadir título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("Listado de Proyectos", tituloFont));
            document.add(new Paragraph(" ")); // línea en blanco para separación

            // 5) Crear tabla con tantas columnas como campos vayamos a mostrar
            //    Ejemplo: ID | Nombre | Descripción | Fecha Inicio | Fecha Fin | Meta | Estado | Categoría | Usuario
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            // Ajusta los anchos a tu conveniencia:
            table.setWidths(new float[] { 1, 3, 4, 2, 2, 2, 2, 2, 2 });

            // 6) Cabeceras de la tabla
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            String[] headers = {
                "ID", "Nombre", "Descripción", 
                "Fecha Inicio", "Fecha Fin", "Meta", 
                "Estado", "Categoría", "ID Usuario"
            };
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Paragraph(h, headerFont));
                cell.setBackgroundColor(com.itextpdf.text.BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // 7) Rellenar filas con datos de cada ProyectoDto
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            for (ProyectoDto p : proyectos) {
                // ID
                table.addCell(new PdfPCell(new Paragraph(
                        p.getIdProyecto() != null ? p.getIdProyecto().toString() : "-", cellFont)));

                // Nombre
                table.addCell(new PdfPCell(new Paragraph(
                        p.getNombreProyecto() != null ? p.getNombreProyecto() : "-", cellFont)));

                // Descripción (puede cortarse si es muy larga)
                String desc = p.getDescripcionProyecto() != null
                        ? p.getDescripcionProyecto()
                        : "-";
                table.addCell(new PdfPCell(new Paragraph(desc, cellFont)));

                // Fecha Inicio (LocalDateTime → String)
                table.addCell(new PdfPCell(new Paragraph(
                        p.getFechaInicioProyecto() != null 
                            ? p.getFechaInicioProyecto().toString() 
                            : "-", cellFont)));

                // Fecha Fin (LocalDate → String)
                table.addCell(new PdfPCell(new Paragraph(
                        p.getFechaFinalizacionProyecto() != null 
                            ? p.getFechaFinalizacionProyecto().toString() 
                            : "-", cellFont)));

                // Meta Recaudación
                table.addCell(new PdfPCell(new Paragraph(
                        p.getMetaRecaudacionProyecto() != null 
                            ? p.getMetaRecaudacionProyecto().toString() 
                            : "-", cellFont)));

                // Estado (muestra idEstado o nombreEstado si tu DTO lo tiene)
                // Suponemos que ProyectoDto tiene getIdEstado() e getNombreEstado().
                String estadoStr = "-";
                if (p.getIdEstado() != null) {
                	estadoStr = p.getIdEstado().toString();
                    
                }
                table.addCell(new PdfPCell(new Paragraph(estadoStr, cellFont)));

                // Categoría (ID o nombre, según ProyectoDto)
                String catStr = "-";
                if (p.getIdCategoria() != null) {
                    catStr = p.getIdCategoria().toString();
                    // Si tu DTO incluye nombre de categoría, úsalo en lugar de ID:
                    // catStr = p.getNombreCategoria();
                }
                table.addCell(new PdfPCell(new Paragraph(catStr, cellFont)));

                // ID Usuario
                table.addCell(new PdfPCell(new Paragraph(
                        p.getIdUsuario() != null ? p.getIdUsuario().toString() : "-", cellFont)));
            }

            // 8) Añadir la tabla al documento
            document.add(table);

        } catch (DocumentException de) {
            throw new IOException("Error generando PDF de proyectos: " + de.getMessage(), de);
        } finally {
            document.close();
        }
    }
}
