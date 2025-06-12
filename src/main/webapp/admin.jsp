<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="dtos.UsuarioDto, java.util.List"%>
<%
List<UsuarioDto> usuarios = (List<UsuarioDto>) request.getAttribute("usuarios");
List<dtos.ProyectoDto> proyectos = (List<dtos.ProyectoDto>) request.getAttribute("proyectos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>Agency - Panel de Administración</title>

<!-- Favicon -->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />

<!-- Font Awesome -->
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
	crossorigin="anonymous"></script>

<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700"
	rel="stylesheet" />

<!-- Core Theme CSS -->
<link href="css/styles.css" rel="stylesheet" />

<style>
body {
	padding-top: 80px; /* espacio para navbar fija */
	background-image: url('assets/img/fodoSolapado.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}

/* Ajustar altura de las cards */
.fixed-card {
	height: 700px;
	display: flex;
	flex-direction: column;
}

/* Scroll para listado */
.scroll-body {
	overflow-y: auto;
	flex: 1;
}

/* Asegurar diseño responsivo */
.card-body {
	overflow-y: auto;
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

	<!-- PANEL ADMINISTRACIÓN -->
	<div class="container my-5">
		<h2 class="mb-4 text-center">Panel de Administración</h2>
		<div class="row gx-4 gy-4">
			<!-- FORMULARIO USUARIO -->
			<div class="col-12 col-lg-6">
				<div class="card shadow-sm fixed-card">
					<div class="card-header">
						<h5 class="mb-0">Dar de alta usuario</h5>
					</div>
					<div class="card-body">
						<form
							action="${pageContext.request.contextPath}/admin/crearUsuario"
							method="post">
							<div class="row gx-3">
								<div class="col-md-6 mb-3">
									<label class="form-label">Nombre</label> <input type="text"
										name="nombre_usuario" class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">Apellidos</label> <input type="text"
										name="apellidos_usuario" class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">Email</label> <input type="email"
										name="mail_usuario" class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">Fecha de Nacimiento</label> <input
										type="date" name="fecha_nacimiento_usuario"
										class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">Nickname</label> <input type="text"
										name="nickname_usuario" class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">Contraseña</label> <input
										type="password" name="contrasenya_usuario"
										class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">DNI</label> <input type="text"
										name="dni_usuario" class="form-control" required>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">Teléfono</label> <input type="tel"
										name="telefono_usuario" class="form-control" required>
								</div>
								<div class="col-12 mb-3">
									<label class="form-label">Descripción</label>
									<textarea name="descripcion_usuario" class="form-control"
										rows="3"></textarea>
								</div>
								<div class="col-md-6 mb-3">
									<label class="form-label">Rol</label> <select name="rol"
										class="form-select" required>
										<option value="">-- Selecciona rol --</option>
										<c:forEach var="r" items="${roles}">
											<option value="${r.idRol}">${r.nombreRol}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-6 d-flex align-items-end mb-3">
									<button type="submit" class="btn btn-success w-100">Crear
										usuario</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- LISTADO USUARIOS -->
			<div class="col-12 col-lg-6">
				<div class="card shadow-sm fixed-card d-flex flex-column">
					<div class="card-header">
						<h5 class="mb-0">Listado de usuarios</h5>
					</div>
					<div class="scroll-body">
						<!-- BOTÓN PARA DESCARGAR PDF de usuarios -->
						<div class="mb-3">
							<a href="${pageContext.request.contextPath}/descargarUsuariosPDF"
								class="btn btn-primary"> <i class="fas fa-file-pdf"></i>
								Descargar PDF de Usuarios
							</a>
						</div>
						<ul class="list-group list-group-flush">
							<c:choose>
								<c:when test="${not empty usuarios}">
									<c:forEach var="u" items="${usuarios}">
										<li
											class="list-group-item d-flex justify-content-between align-items-center">
											<div>
												<strong>${u.nombreUsuario} ${u.apellidosUsuario}</strong><br>
												<small class="text-muted">${u.mailUsuario}</small>
											</div>
											<div class="d-flex align-items-center gap-2">
												<span class="badge bg-warning text-dark"> <c:forEach
														var="r" items="${roles}">
														<c:if test="${r.idRol == u.rol}">${r.nombreRol}</c:if>
													</c:forEach>
												</span>

												<!-- Botón de desplegable con opciones Editar/Borrar -->
												<div class="dropdown">
													<button class="btn btn-sm btn-light" type="button"
														id="dropdownUser${u.id}" data-bs-toggle="dropdown"
														aria-expanded="false">
														<i class="fas fa-ellipsis-v"></i>
													</button>
													<ul class="dropdown-menu dropdown-menu-end"
														aria-labelledby="dropdownUser${u.id}">
														<li><a class="dropdown-item"
															href="${pageContext.request.contextPath}/editarUsuario?id=${u.id}">
																Editar </a></li>
														<li><a class="dropdown-item text-danger"
															href="${pageContext.request.contextPath}/admin/borrarUsuario?id=${u.id}"
															onclick="return confirm('¿Seguro que quieres borrar a ${u.nombreUsuario}?');">
																Borrar </a></li>
													</ul>
												</div>
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

		<!-- Segunda fila: proyectos -->
		<div class="row gx-4 gy-4 mt-4">
			<!-- Crear Proyecto -->
			<div class="col-12 col-lg-6">
				<div class="card shadow-sm fixed-card">
					<div class="card-header">
						<h5 class="mb-0">Crear Nuevo Proyecto</h5>
					</div>
					<div class="card-body">
						<form action="proyecto" method="POST"
							enctype="multipart/form-data" class="needs-validation">
							<!-- Nombre Proyecto + Selección de Usuario -->
							<div class="mb-3">
								<label for="nombreProyecto" class="form-label">Nombre
									del Proyecto</label>
								<div class="input-group">
									<span class="input-group-text"><i class="fas fa-pen"></i></span>
									<input type="text" id="nombreProyecto" name="nombreProyecto"
										class="form-control" placeholder="Nombre del proyecto"
										required> <select id="usuarioProyecto"
										name="usuarioProyecto" class="form-select" required>
										<option value="">-- Asignar a Usuario --</option>
										<c:forEach var="u" items="${usuarios}">
											<option value="${u.id}">${u.nombreUsuario}
												${u.apellidosUsuario}</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<!-- Resto de campos iguales -->
							<div class="mb-3">
								<label for="descripcionProyecto" class="form-label">Descripción</label>
								<div class="input-group">
									<span class="input-group-text"><i
										class="fas fa-file-alt"></i></span>
									<textarea id="descripcionProyecto" name="descripcionProyecto"
										class="form-control" rows="3" required></textarea>
								</div>
							</div>
							<div class="mb-3">
								<label for="imagen1Proyecto" class="form-label">Imagen 1</label>
								<input type="file" id="imagen1Proyecto" name="imagen1Proyecto"
									class="form-control">
							</div>
							<div class="mb-3">
								<label for="imagen2Proyecto" class="form-label">Imagen 2</label>
								<input type="file" id="imagen2Proyecto" name="imagen2Proyecto"
									class="form-control">
							</div>
							<div class="mb-3">
								<label for="imagen3Proyecto" class="form-label">Imagen 3</label>
								<input type="file" id="imagen3Proyecto" name="imagen3Proyecto"
									class="form-control">
							</div>
							<div class="mb-3">
								<label for="fechaFinalizacionProyecto" class="form-label">Fecha
									de Finalización</label>
								<div class="input-group">
									<span class="input-group-text"><i
										class="fas fa-calendar"></i></span> <input type="date"
										id="fechaFinalizacionProyecto"
										name="fechaFinalizacionProyecto" class="form-control" required>
								</div>
							</div>
							<div class="mb-3">
								<label for="metaRecaudacionProyecto" class="form-label">Meta
									de Recaudación</label>
								<div class="input-group">
									<span class="input-group-text"><i
										class="fas fa-money-bill-wave"></i></span> <input type="number"
										id="metaRecaudacionProyecto" name="metaRecaudacionProyecto"
										class="form-control" required>
								</div>
							</div>
							<div class="mb-3">
								<label for="categoriaProyecto" class="form-label">Categoría</label>
								<div class="input-group">
									<span class="input-group-text"><i class="fas fa-list"></i></span>
									<select id="categoriaProyecto" name="categoriaProyecto"
										class="form-select" required>
										<option value="Educación">Educación</option>
										<option value="Salud">Salud</option>
										<option value="Medio Ambiente">Medio Ambiente</option>
										<option value="1">Tecnología</option>
										<option value="Arte">Arte</option>
									</select>
								</div>
							</div>

							<button type="submit" class="btn btn-primary">
								<i class="fas fa-save"></i> Crear Proyecto
							</button>
						</form>
					</div>
				</div>
			</div>

			<!-- Listado de proyectos -->
			<div class="col-12 col-lg-6">
				<div class="card shadow-sm fixed-card d-flex flex-column">
					<div class="card-header">
						<h5 class="mb-0">Listado de proyectos</h5>
					</div>
					<div class="scroll-body">
						<!-- BOTÓN PARA DESCARGAR PDF de proyectos -->
						<div class="mb-3">
							<a
								href="${pageContext.request.contextPath}/descargarProyectosPDF"
								class="btn btn-primary"> <i class="fas fa-file-pdf"></i>
								Descargar PDF de Proyectos
							</a>
						</div>
						<ul class="list-group list-group-flush">
							<c:choose>
								<c:when test="${not empty proyectos}">
									<c:forEach var="p" items="${proyectos}">
										<li
											class="list-group-item d-flex justify-content-between align-items-center">
											<div>
												<strong>${p.nombreProyecto}</strong><br> <small
													class="text-muted">${p.descripcionProyecto}</small>
											</div>
											<div class="d-flex align-items-center gap-2">
												<span class="badge bg-primary">${p.idCategoria}</span>

												<!-- Botón de desplegable con opciones Editar/Borrar -->
												<div class="dropdown">
													<button class="btn btn-sm btn-light" type="button"
														id="dropdownProj${p.idProyecto}" data-bs-toggle="dropdown"
														aria-expanded="false">
														<i class="fas fa-ellipsis-v"></i>
													</button>
													<ul class="dropdown-menu dropdown-menu-end"
														aria-labelledby="dropdownProj${p.idProyecto}">
														<li><a class="dropdown-item"
															href="${pageContext.request.contextPath}/editarProyecto?id=${p.idProyecto}">
																Editar </a></li>

														<!-- CAMBIAR ESTADO: dependiendo del estado actual mostramos la acción -->
														<li>
															<form method="POST"
																action="${pageContext.request.contextPath}/cambiarEstadoProyecto"
																style="display: inline;"
																onsubmit="return confirm('¿Seguro que quieres cambiar el estado de “${p.nombreProyecto}”?');">
																<input type="hidden" name="idProyecto"
																	value="${p.idProyecto}" />

																<!-- Si el proyecto está en revisión (1), permitimos “Activar” (2) -->
																<c:if test="${p.idEstado == 1}">
																	<input type="hidden" name="nuevoEstado" value="2" />
																	<button type="submit" class="dropdown-item">
																		Marcar Activo</button>
																</c:if>

																<!-- Si está activo (2), permitimos “Finalizar” (3) -->
																<c:if test="${p.idEstado == 2}">
																	<input type="hidden" name="nuevoEstado" value="3" />
																	<button type="submit" class="dropdown-item text-danger">
																		Marcar Finalizado</button>
																</c:if>

																<!-- Si está finalizado (3), opcionalmente permitir “Reabrir” (2) -->
																<c:if test="${p.idEstado == 3}">
																	<input type="hidden" name="nuevoEstado" value="2" />
																	<button type="submit"
																		class="dropdown-item text-warning">Reabrir
																		(Activo)</button>
																</c:if>
															</form>
														</li>
														<li>
															<!-- FORMULARIO para borrar el proyecto por POST -->
															<form
																action="${pageContext.request.contextPath}/eliminarProyecto"
																method="POST"
																onsubmit="return confirm('¿Seguro que quieres borrar el proyecto “${p.nombreProyecto}”?');"
																style="display: inline;">
																<!-- Campo oculto con el id del proyecto -->
																<input type="hidden" name="idProyecto"
																	value="${p.idProyecto}" />
																<button type="submit" class="dropdown-item text-danger">
																	Borrar</button>
															</form>
														</li>
													</ul>
												</div>
											</div>
										</li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<li class="list-group-item text-center text-muted">No hay
										proyectos registrados.</li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- FOOTER -->
	<footer class="footer py-4">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-4 text-lg-start">Copyright &copy; BOOSTLY 2025</div>
				
			</div>
		</div>
	</footer>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Custom JS -->
	<script src="js/scripts.js"></script>
</body>
</html>
