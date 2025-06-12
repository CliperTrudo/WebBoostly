package controladores;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import dtos.RolDto;
import dtos.SesionDto;
import dtos.UsuarioDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.ApiService;

/**
 * Servlet que muestra el formulario de edición de un usuario y procesa el envío
 * de los datos modificados para actualizar el usuario vía API.
 */
@WebServlet("/editarUsuario")
public class UsuarioEditarController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Instancia de tu ApiService para hacer las llamadas HTTP a la API REST
	private final ApiService apiService = new ApiService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1️⃣ Verificar sesión
			HttpSession session = request.getSession(false);
			SesionDto sesionUsu = (session != null) ? (SesionDto) session.getAttribute("datos") : null;
			if (sesionUsu == null) {
				// Si no hay usuario en sesión → redirijo al login
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			// 2️⃣ Leer parámetro "id" de la URL, para saber qué usuario editar
			String idParam = request.getParameter("id");
			if (idParam == null) {
				response.sendRedirect(request.getContextPath() + "/cuenta");
				return;
			}

			Long idUsuario = Long.parseLong(idParam);

			// 3️⃣ Llamar al servicio para obtener UsuarioDto
			UsuarioDto usuario = apiService.obtenerUsuarioPorId(idUsuario);
			if (usuario == null) {
				// Si no existe ese usuario → redirigir a "cuenta"
				response.sendRedirect(request.getContextPath() + "/cuenta");
				return;
			}

			// 4️⃣ Verificar permisos: solo el propio user o rol 3 (Admin) pueden editar
			Long sesionId = sesionUsu.getId();
			Integer sesionRol = (int) sesionUsu.getRolUsuario();
			if (!usuario.getId().equals(sesionId) && (sesionRol == null || sesionRol != 3)) {
				// no soy yo mismo ni soy Admin
				response.sendRedirect(request.getContextPath() + "/cuenta");
				return;
			}

			// 5️⃣ Obtener también la lista de roles (para el <select> de rol)
			List<RolDto> roles = apiService.obtenerRoles();

			// 6️⃣ Pasar ambos al JSP
			request.setAttribute("usuario", usuario);
			request.setAttribute("roles", roles);

			// 7️⃣ Forward al JSP que mostrará el formulario rellenado
			RequestDispatcher rd = request.getRequestDispatcher("/editarUsuario.jsp");
			rd.forward(request, response);

		} catch (NumberFormatException e) {
			// Si id no es numérico
			response.sendRedirect(request.getContextPath() + "/cuenta");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1️⃣ Verificar sesión
			HttpSession session = request.getSession(false);
			SesionDto sesionUsu = (session != null) ? (SesionDto) session.getAttribute("datos") : null;
			if (sesionUsu == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			Long idUsuario = Long.parseLong(request.getParameter("idUsuario"));

			// 2️⃣ Obtener UsuarioDto actual para comprobar permisos
			UsuarioDto usuarioOriginal = apiService.obtenerUsuarioPorId(idUsuario);
			if (usuarioOriginal == null) {
				response.sendRedirect(request.getContextPath() + "/cuenta");
				return;
			}

			// 3️⃣ Verificar permisos: solo el propio user o rol 3 (Admin) pueden actualizar
			Long sesionId = sesionUsu.getId();
			Integer sesionRol = (int) sesionUsu.getRolUsuario();
			if (!usuarioOriginal.getId().equals(sesionId) && (sesionRol == null || sesionRol != 3)) {
				// no soy yo mismo ni soy Admin
				response.sendRedirect(request.getContextPath() + "/cuenta");
				return;
			}

			// 4️⃣ Construyo un UsuarioDto solo con los campos que quiero actualizar
			UsuarioDto usuarioActualizado = new UsuarioDto();
			usuarioActualizado.setId(idUsuario);

			// Nombre
			String nombre = request.getParameter("nombreUsuario");
			if (nombre != null && !nombre.isEmpty()) {
				usuarioActualizado.setNombreUsuario(nombre.trim());
			}

			// Apellidos
			String apellidos = request.getParameter("apellidosUsuario");
			if (apellidos != null && !apellidos.isEmpty()) {
				usuarioActualizado.setApellidosUsuario(apellidos.trim());
			}

			// Email
			String email = request.getParameter("mailUsuario");
			if (email != null && !email.isEmpty()) {
				usuarioActualizado.setMailUsuario(email.trim());
			}

			// Fecha de nacimiento
			String fechaNac = request.getParameter("fechaNacimientoUsuario");
			if (fechaNac != null && !fechaNac.isEmpty()) {
				usuarioActualizado.setFechaNacimientoUsuario(Date.valueOf(fechaNac));
			}

			// Nickname
			String nickname = request.getParameter("nicknameUsuario");
			if (nickname != null && !nickname.isEmpty()) {
				usuarioActualizado.setNicknameUsuario(nickname.trim());
			}

			// Contraseña (solo si pone algo nuevo)
			String contrasenia = request.getParameter("contrasenyaUsuario");
			if (contrasenia != null && !contrasenia.isEmpty()) {
				usuarioActualizado.setContrasenyaUsuario(contrasenia.trim());
			}

			// Descripción
			String descripcion = request.getParameter("descripcionUsuario");
			if (descripcion != null && !descripcion.isEmpty()) {
				usuarioActualizado.setDescripcionUsuario(descripcion.trim());
			}

			// DNI
			String dni = request.getParameter("dniUsuario");
			if (dni != null && !dni.isEmpty()) {
				usuarioActualizado.setDniUsuario(dni.trim());
			}

			// Teléfono
			String telefono = request.getParameter("telefonoUsuario");
			if (telefono != null && !telefono.isEmpty()) {
				usuarioActualizado.setTelefonoUsuario(telefono.trim());
			}

			// Rol (ID)
			String rolParam = request.getParameter("rol");
			if (rolParam != null && !rolParam.isEmpty()) {
				usuarioActualizado.setRol(Long.parseLong(rolParam));
			}

			// 5️⃣ Llamar al servicio para que invoque PUT /api/usuarios/actualizar/{id}
			boolean ok = apiService.actualizarUsuario(usuarioActualizado);

			// 6️⃣ Redirigir según resultado
			if (ok) {
				// por ejemplo, volver a “Cuenta” o “Lista de usuarios”
				response.sendRedirect(request.getContextPath() + "/cuenta");
			} else {
				// Si hubo fallo, redirijo de nuevo al mismo formulario con error=1
				response.sendRedirect(request.getContextPath() + "/editarUsuario?id=" + idUsuario + "&error=1");
			}

		} catch (Exception e) {
			e.printStackTrace();
			// Si hay error (id no numérico o problema), volver a “Cuenta”
			response.sendRedirect(request.getContextPath() + "/cuenta");
		}
	}
}
