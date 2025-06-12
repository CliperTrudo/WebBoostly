<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
// El servlet ya dejó en requestAttribute "usuario" (UsuarioDto)
dtos.UsuarioDto usuario = (dtos.UsuarioDto) request.getAttribute("usuario");
@SuppressWarnings("unchecked")
java.util.List<dtos.RolDto> roles = (java.util.List<dtos.RolDto>) request.getAttribute("roles");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Editar Usuario</title>

<!-- Font Awesome -->
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
	crossorigin="anonymous"></script>

<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700"
	rel="stylesheet" />

<!-- Tu CSS principal -->
<link href="css/styles.css" rel="stylesheet" />

<style>
/* Ajustes puntuales para el formulario: */
body {
	padding-top: 80px; /* espacio para navbar */
	background-color: #f5f5f5;
}

.fmUsuario {
	max-width: 700px;
	margin: 40px auto;
	background-color: #fff;
	padding: 20px 30px;
	border-radius: 8px;
	box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
}
</style>
</head>
<body id="page-top">
	<!-- NAVBAR -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"
		id="mainNav">
		<div class="container">
			<a class="navbar-brand" href="#page-top"> <img
				src="assets/img/logoo.png" alt="Logo"
				style="height: 30%; width: 30%;" />
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
				aria-controls="navbarResponsive" aria-expanded="false"
				aria-label="Toggle navigation">
				Menu <i class="fas fa-bars ms-1"></i>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
					<li class="nav-item"><a class="nav-link" href="/webboostly/">Inicio</a></li>
					<c:choose>
						<c:when test="${not empty sessionScope.datos}">
							<li class="nav-item"><a class="nav-link"
								href="/webboostly/cuenta">Cuenta</a></li>
							<li class="nav-item"><a class="nav-link"
								href="/webboostly/proyectosCategoria">Proyecto</a></li>
							<c:if test="${sessionScope.datos.rolUsuario == 3}">
								<li class="nav-item"><a class="nav-link"
									href="/webboostly/admin">Admin</a></li>
							</c:if>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link"
								href="/webboostly/login">Login</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

	<div class="fmUsuario">
		<h2 class="text-center mb-4">
			<i class="fas fa-user-edit"></i> Editar Usuario
		</h2>

		<c:if test="${param.error != null}">
			<div class="alert alert-danger">No se pudo actualizar el
				usuario. Intenta de nuevo.</div>
		</c:if>

		<form id="editarForm" action="${pageContext.request.contextPath}/editarUsuario"
			method="POST" class="needs-validation" novalidate>

			<input type="hidden" name="idUsuario" value="${usuario.id}" />

			<!-- Nombre -->
			<div class="mb-3">
				<label for="nombreUsuario" class="form-label">Nombre</label>
				<input type="text" id="nombreUsuario" name="nombreUsuario"
					class="form-control"
					value="${usuario.nombreUsuario}"
					required pattern="[A-Za-záéíóúÁÉÍÓÚñÑ\s]{2,50}"
					title="El nombre debe contener solo letras y tener entre 2 y 50 caracteres">
				<div class="invalid-feedback">
					Por favor, introduce un nombre válido (2-50 letras).
				</div>
			</div>

			<!-- Apellidos -->
			<div class="mb-3">
				<label for="apellidosUsuario" class="form-label">Apellidos</label>
				<input type="text" id="apellidosUsuario" name="apellidosUsuario"
					class="form-control"
					value="${usuario.apellidosUsuario}"
					required pattern="[A-Za-záéíóúÁÉÍÓÚñÑ\s]{2,100}"
					title="Los apellidos deben contener solo letras y tener entre 2 y 100 caracteres">
				<div class="invalid-feedback">
					Por favor, introduce apellidos válidos (2-100 letras).
				</div>
			</div>

			<!-- Email -->
			<div class="mb-3">
				<label for="mailUsuario" class="form-label">Email</label>
				<div class="input-group">
					<span class="input-group-text"><i class="fas fa-envelope"></i></span>
					<input type="email" id="mailUsuario" name="mailUsuario"
						class="form-control"
						value="${usuario.mailUsuario}"
						required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
						title="Por favor, introduce un correo electrónico válido">
				</div>
				<div class="invalid-feedback">
					Por favor, introduce un correo electrónico válido.
				</div>
			</div>

			<!-- Fecha de nacimiento -->
			<div class="mb-3">
				<label for="fechaNacimientoUsuario" class="form-label">Fecha
					de Nacimiento</label>
				<div class="input-group">
					<span class="input-group-text"><i class="fas fa-calendar"></i></span>
					<input type="date" id="fechaNacimientoUsuario"
						name="fechaNacimientoUsuario" class="form-control"
						value="${usuario.fechaNacimientoUsuario}" required>
				</div>
				<div class="invalid-feedback">
					Por favor, selecciona una fecha de nacimiento válida
					(y al menos 16 años).
				</div>
			</div>

			<!-- Nickname -->
			<div class="mb-3">
				<label for="nicknameUsuario" class="form-label">Nickname</label>
				<input type="text" id="nicknameUsuario" name="nicknameUsuario"
					class="form-control"
					value="${usuario.nicknameUsuario}"
					required pattern="[A-Za-z0-9_]{3,20}"
					title="El nickname debe tener entre 3 y 20 caracteres alfanuméricos o guiones bajos">
				<div class="invalid-feedback">
					El nickname debe tener 3-20 caracteres alfanuméricos o guiones bajos.
				</div>
			</div>

			<!-- Descripción -->
			<div class="mb-3">
				<label for="descripcionUsuario" class="form-label">Descripción</label>
				<textarea id="descripcionUsuario" name="descripcionUsuario"
					class="form-control" rows="3"
					placeholder="Breve descripción (opcional)">" + "${usuario.descripcionUsuario}" + "</textarea>
			</div>

			<!-- DNI -->
			<div class="mb-3">
				<label for="dniUsuario" class="form-label">DNI</label>
				<input type="text" id="dniUsuario" name="dniUsuario"
					class="form-control"
					value="${usuario.dniUsuario}"
					required pattern="\\d{8}[A-Za-z]"
					title="El DNI debe tener 8 dígitos seguidos de una letra">
				<div class="invalid-feedback">
					El DNI debe tener 8 dígitos seguidos de una letra.
				</div>
			</div>

			<!-- Teléfono -->
			<div class="mb-3">
				<label for="telefonoUsuario" class="form-label">Teléfono</label>
				<input type="tel" id="telefonoUsuario" name="telefonoUsuario"
					class="form-control"
					value="${usuario.telefonoUsuario}"
					required pattern="\\d{9}"
					title="El teléfono debe tener 9 dígitos">
				<div class="invalid-feedback">
					El teléfono debe tener 9 dígitos.
				</div>
			</div>

			<!-- Rol (sólo visible para admin) -->
			<c:if test="${sessionScope.datos.rolUsuario == 3}">
				<div class="mb-3">
					<label for="rol" class="form-label">Rol</label>
					<div class="input-group">
						<span class="input-group-text"><i
							class="fas fa-user-shield"></i></span>
						<select id="rol" name="rol"
							class="form-select" required>
							<option value="">-- Selecciona rol --</option>
							<c:forEach var="r" items="${roles}">
								<option value="${r.idRol}"
									${r.idRol == usuario.rol ? 'selected' : ''}>
									${r.nombreRol}</option>
							</c:forEach>
						</select>
					</div>
					<div class="invalid-feedback">
						Por favor, selecciona un rol.
					</div>
				</div>
			</c:if>
			<c:if test="${sessionScope.datos.rolUsuario != 3}">
				<input type="hidden" name="rol" value="${usuario.rol}" />
			</c:if>

			<div class="d-grid">
				<button type="submit" class="btn btn-primary">
					<i class="fas fa-save"></i> Guardar cambios
				</button>
			</div>
		</form>
	</div>

	<footer class="footer py-4">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-4 text-lg-start">Copyright &copy; BOOSTLY
					2025</div>

			</div>
		</div>
	</footer>

	<script>
		(function () {
			'use strict'

			var form = document.getElementById('editarForm');

			function validateDateOfBirth(dateInput) {
				var selectedDate = new Date(dateInput.value);
				var today = new Date();
				var minAgeDate = new Date(today.getFullYear() - 16, today.getMonth(), today.getDate());

				if (selectedDate > minAgeDate) {
					dateInput.setCustomValidity('Debes tener al menos 16 años.');
					return false;
				} else {
					dateInput.setCustomValidity('');
					return true;
				}
			}

			var dateInput = document.getElementById('fechaNacimientoUsuario');
			dateInput.addEventListener('change', function () {
				validateDateOfBirth(dateInput);
			});

			form.addEventListener('submit', function (event) {
				event.preventDefault();
				event.stopPropagation();

				var isValid = form.checkValidity();
				var isDateValid = validateDateOfBirth(dateInput);

				if (isValid && isDateValid) {
					form.submit();
				} else {
					form.classList.add('was-validated');
				}
			}, false);
		})();
	</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
