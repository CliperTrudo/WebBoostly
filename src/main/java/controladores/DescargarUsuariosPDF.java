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

import dtos.UsuarioDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.ApiService;

/**
 * Servlet que genera un PDF con todos los usuarios y lo envía como descarga.
 */
@WebServlet("/descargarUsuariosPDF")
public class DescargarUsuariosPDF extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ApiService apiService = new ApiService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1) Obtener lista de usuarios desde la API
        List<UsuarioDto> usuarios = apiService.obtenerUsuarios();

        // 2) Configurar la respuesta para descargar un PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"usuarios.pdf\"");

        // 3) Crear el documento iText
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // 4) Título
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("Listado de Usuarios", tituloFont));
            document.add(new Paragraph(" ")); // línea en blanco

            // 5) Crear tabla con tantas columnas como campos quieras mostrar
            PdfPTable table = new PdfPTable(5); // Ejemplo: columnas: ID, Nombre, Apellidos, Email, Rol
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 1, 3, 3, 4, 2 });

            // 6) Cabeceras de la tabla
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            String[] headers = { "ID", "Nombre", "Apellidos", "Email", "Rol" };
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Paragraph(h, headerFont));
                cell.setBackgroundColor(com.itextpdf.text.BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // 7) Rellenar filas con los datos de cada UsuarioDto
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            for (UsuarioDto u : usuarios) {
                // ID
                table.addCell(new PdfPCell(new Paragraph(
                    u.getId() != null ? u.getId().toString() : "-", cellFont)));

                // Nombre
                table.addCell(new PdfPCell(new Paragraph(
                    u.getNombreUsuario() != null ? u.getNombreUsuario() : "-", cellFont)));

                // Apellidos
                table.addCell(new PdfPCell(new Paragraph(
                    u.getApellidosUsuario() != null ? u.getApellidosUsuario() : "-", cellFont)));

                // Email
                table.addCell(new PdfPCell(new Paragraph(
                    u.getMailUsuario() != null ? u.getMailUsuario() : "-", cellFont)));

                // Rol (suponemos que u.getRol() contiene el ID de rol; podrías traducir a nombre si tu DTO lo incluye)
                table.addCell(new PdfPCell(new Paragraph(
                    u.getRol() != null ? u.getRol().toString() : "-", cellFont)));
            }

            // 8) Añadir la tabla al documento
            document.add(table);

        } catch (DocumentException de) {
            throw new IOException("Error generando el PDF: " + de.getMessage(), de);
        } finally {
            document.close();
        }
    }
}
