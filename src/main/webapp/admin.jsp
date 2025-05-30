<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="dtos.UsuarioDto, java.util.List"%>
<%
// En tu controlador:
// request.setAttribute("usuarios", listaDeUsuarios);
List<UsuarioDto> usuarios = (List<UsuarioDto>) request.getAttribute("usuarios");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>Panel de Administración</title>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous" />

<style>
/* Ambos cards tienen la misma altura */
.fixed-card {
	height: 700px; /* Ajusta según la altura real de tu formulario */
}
/* Scroll interno para el listado */
.fixed-card .scroll-body {
	height: calc(100% - 56px);
	/* Restar altura del header de la card (~56px) */
	overflow-y: auto;
	padding: 0;
}
</style>
</head>
<body class="bg-light">

	<!-- Navbar -->
	<nav
		class="navbar navbar-expand-lg navbar-light bg-white shadow-sm mb-4">
		<div class="container">
			<a class="navbar-brand" href="#">Mi Panel</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navMenu"
				aria-controls="navMenu" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navMenu">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="#">Preguntas</a></li>
					<li class="nav-item"><a class="nav-link active" href="#">Administración</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Cerrar
							sesión</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Contenedor principal -->
	<div class="container my-5">
		<h2 class="mb-4 text-center">Panel de Administración</h2>

		<div class="row gx-4 gy-4">
			<!-- Card 1: Dar de alta usuario -->
			<div class="col-12 col-lg-6">
				<div class="card shadow-sm fixed-card d-flex flex-column">
					<div class="card-header">
						<h5 class="mb-0">Dar de alta usuario</h5>
					</div>
					<div class="card-body overflow-auto">
						<form
							action="${pageContext.request.contextPath}/admin/crearUsuario"
							method="post">
							<div class="row gx-3">
								<div class="col-md-6 mb-3">
									<label for="nombre_usuario" class="form-label">Nombre</label> <input
										type="text" id="nombre_usuario" name="nombre_usuario"
										class="form-control" placeholder="Nombre" required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="apellidos_usuario" class="form-label">Apellidos</label>
									<input type="text" id="apellidos_usuario"
										name="apellidos_usuario" class="form-control"
										placeholder="Apellidos" required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="mail_usuario" class="form-label">Email</label> <input
										type="email" id="mail_usuario" name="mail_usuario"
										class="form-control" placeholder="email@dominio.com" required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="fecha_nacimiento_usuario" class="form-label">Fecha
										de Nacimiento</label> <input type="date" id="fecha_nacimiento_usuario"
										name="fecha_nacimiento_usuario" class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="nickname_usuario" class="form-label">Nickname</label>
									<input type="text" id="nickname_usuario"
										name="nickname_usuario" class="form-control"
										placeholder="Alias de usuario" required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="contrasenya_usuario" class="form-label">Contraseña</label>
									<input type="password" id="contrasenya_usuario"
										name="contrasenya_usuario" class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="dni_usuario" class="form-label">DNI</label> <input
										type="text" id="dni_usuario" name="dni_usuario"
										class="form-control" placeholder="12345678A" required>
								</div>
								<div class="col-md-6 mb-3">
									<label for="telefono_usuario" class="form-label">Teléfono</label>
									<input type="tel" id="telefono_usuario" name="telefono_usuario"
										class="form-control" placeholder="612345678" required>
								</div>
								<div class="col-12 mb-3">
									<label for="descripcion_usuario" class="form-label">Descripción</label>
									<textarea id="descripcion_usuario" name="descripcion_usuario"
										class="form-control" rows="3" placeholder="Breve descripción"></textarea>
								</div>
								<div class="col-md-6 mb-3">
									<label for="rol" class="form-label">Rol</label> <select
										id="rol" name="rol" class="form-select" required>
										<option value="">-- Selecciona rol --</option>
										<c:choose>
											<c:when test="${not empty roles}">
												<c:forEach var="r" items="${roles}">
													<option value="${r.idRol}">${r.nombreRol}</option>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<li class="list-group-item text-center text-muted">No
													hay roles.</li>
											</c:otherwise>
										</c:choose>
									</select>
								</div>
								<div class="col-md-6 d-flex align-items-end mb-3">
									<button type="submit" class="btn btn-success w-100">
										Crear usuario</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- Card 2: Listado de usuarios -->
			<!-- Card de Listado -->
			<div class="col-12 col-lg-6">
				<div class="card shadow-sm fixed-card d-flex flex-column">
					<div class="card-header">
						<h5 class="mb-0">Listado de usuarios</h5>
					</div>
					<div class="scroll-body flex-fill">
						<ul class="list-group list-group-flush">
							<c:choose>
								<c:when test="${not empty usuarios}">
									<c:forEach var="u" items="${usuarios}">
										<li
											class="list-group-item d-flex justify-content-between align-items-center">
											<div>
												<!-- Mostrar nombreRol buscando en la lista de roles -->
												<span class="text-uppercase text-muted small"> 
													
												</span><br> <strong>${u.nombreUsuario}
													${u.apellidosUsuario}</strong><br> <small class="text-muted">${u.mailUsuario}</small>
											</div>
											<div class="d-flex align-items-center gap-2">
												<span class="badge bg-primary"> 
												<c:forEach var="r" items="${roles}">
														<c:if test="${r.idRol == u.rol}">
                             								 ${r.nombreRol}
                            							</c:if>
													</c:forEach>
												</span>
												<button class="btn btn-sm btn-light">
													<i class="fas fa-ellipsis-v"></i>
												</button>
											</div>
										</li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<li class="list-group-item text-center text-muted">No hay
										usuarios registrados.</li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
</body>
</html>
