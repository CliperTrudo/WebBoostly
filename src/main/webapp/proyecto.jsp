<%@page import="dtos.UsuarioDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>Agency - Start Bootstrap Theme</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Font Awesome icons (free version)-->
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
	crossorigin="anonymous"></script>
<!-- Google fonts-->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700"
	rel="stylesheet" type="text/css" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/styles.css" rel="stylesheet" />
<style>
.project-header {
	background-color: #f8f9fa;
	padding: 20px;
	border-radius: 5px;
}

.project-card {
	background-color: #ffffff;
	padding: 15px;
	border-radius: 10px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
}

.project-image {
	max-height: 200px;
	object-fit: cover;
	border-radius: 10px;
}

.meta-info {
	background-color: #f0f0f0;
	padding: 15px;
	border-radius: 5px;
}
</style>
</head>
<body>

	<!-- Navigation -->
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

							<!-- Verificamos si el usuario es administrador (rolUsuario = 2) -->
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
	<br>
	<br>
	<br>
	<br>
	<script>
		// Crear un objeto de JavaScript con los datos de 'proyecto'
		var proyecto = {
			nombreProyecto : "${proyecto.nombreProyecto}",
			descripcionProyecto : "${proyecto.descripcionProyecto}",
			fechaInicioProyecto : "${proyecto.fechaInicioProyecto}",
			fechaFinalizacionProyecto : "${proyecto.fechaFinalizacionProyecto}",
			idCategoria : "${proyecto.idCategoria}",
			idUsuario : "${proyecto.idUsuario}",
			metaRecaudacionProyecto : "${proyecto.metaRecaudacionProyecto}",
			imagen1Proyecto : "${proyecto.imagen1Proyecto}",
			imagen2Proyecto : "${proyecto.imagen2Proyecto}",
			imagen3Proyecto : "${proyecto.imagen3Proyecto}"
		};

		// Mostrar en la consola del navegador
		console.log("Objeto Proyecto desde el backend:", proyecto);
	</script>

	<c:if
		test="${not empty sessionScope.datos and (sessionScope.datos.id eq proyecto.idUsuario || sessionScope.datos.rolUsuario == 3)}">
		<button
			class="btn position-fixed top-50 end-0 translate-middle-y m-3 bg-success text-white"
			onclick="window.location.href='/webboostly/editarProyecto?id=${proyecto.idProyecto}'">
			Editar Proyecto</button>

		<button
			class="btn position-fixed bottom-0 start-0 m-3 bg-danger text-white"
			onclick="confirmarEliminacion('${proyecto.idProyecto}')">
			Eliminar Proyecto</button>
	</c:if>


	<div class="modal fade" id="confirmacionModal" tabindex="-1"
		aria-labelledby="confirmacionModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="confirmacionModalLabel">Confirmar
						Eliminación</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">¿Estás seguro de que deseas eliminar
					este proyecto? Esta acción no se puede deshacer.</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancelar</button>
					<form id="eliminarProyectoForm" method="POST"
						action="/webboostly/eliminarProyecto">
						<input type="hidden" name="idProyecto" id="idProyectoEliminar">
						<button type="submit" class="btn btn-danger">Confirmar</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script>
		function confirmarEliminacion(idProyecto) {
			// Establece el ID del proyecto en el formulario de eliminación
			document.getElementById('idProyectoEliminar').value = idProyecto;
			// Muestra el modal de confirmación
			var confirmacionModal = new bootstrap.Modal(document
					.getElementById('confirmacionModal'));
			confirmacionModal.show();
		}
	</script>

	<div class="container mt-5">
		<div class="project-header text-center">
			<h1 class="display-4">Proyecto: ${proyecto.nombreProyecto}</h1>

		</div>
		<div class="row">
			<div class="col-md-8">
				<div class="project-card">
					<h3>Descripción del Proyecto</h3>
					<p>${proyecto.descripcionProyecto}</p>
				</div>
			</div>
			<div class="col-md-4">
				<div class="project-card">
					<h3>Información del Proyecto</h3>
					<ul class="list-unstyled">
						<li><strong>Fecha de Inicio:</strong>
							${proyecto.fechaInicioProyecto}</li>
						<li><strong>Fecha de Finalización:</strong>
							${proyecto.fechaFinalizacionProyecto}</li>
						<li><strong>Categoría:</strong> ${proyecto.idCategoria}</li>
						<li><strong>Usuario:</strong> ${proyecto.idUsuario}</li>
						<li><strong>Meta de Recaudación:</strong>
							${proyecto.metaRecaudacionProyecto}</li>

					</ul>
					<!-- Contenedor centrado -->
					<div class="text-center mt-3">
						<button type="button" class="btn btn-primary"
							data-bs-toggle="modal" data-bs-target="#donationModal">
							Realizar una donacion</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<c:if test="${not empty imagen1Base64}">
				<div class="col-md-4">
					<div class="project-card">
						<h3>Imagen 1</h3>
						<img src="data:image/png;base64,${imagen1Base64}"
							class="project-image" alt="Imagen 1 del proyecto" />
					</div>
				</div>
			</c:if>

			<c:if test="${not empty imagen2Base64}">
				<div class="col-md-4">
					<div class="project-card">
						<h3>Imagen 2</h3>
						<img src="data:image/png;base64,${imagen2Base64}"
							class="project-image" alt="Imagen 2 del proyecto" />
					</div>
				</div>
			</c:if>

			<c:if test="${not empty imagen3Base64}">
				<div class="col-md-4">
					<div class="project-card">
						<h3>Imagen 3</h3>
						<img src="data:image/png;base64,${imagen3Base64}"
							class="project-image" alt="Imagen 3 del proyecto" />
					</div>
				</div>
			</c:if>
		</div>
	</div>

	<footer class="footer py-4">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-4 text-lg-start">Copyright &copy; Your
					Website 2023</div>
				<div class="col-lg-4 my-3 my-lg-0 text-center">
					<a class="btn btn-dark btn-social mx-2" href="#!"
						aria-label="Twitter"> <i class="fab fa-twitter"></i>
					</a> <a class="btn btn-dark btn-social mx-2" href="#!"
						aria-label="Facebook"> <i class="fab fa-facebook-f"></i>
					</a> <a class="btn btn-dark btn-social mx-2" href="#!"
						aria-label="LinkedIn"> <i class="fab fa-linkedin-in"></i>
					</a>
				</div>
				<div class="col-lg-4 text-lg-end">
					<a class="link-dark text-decoration-none me-3" href="#!">Privacy
						Policy</a> <a class="link-dark text-decoration-none" href="#!">Terms
						of Use</a>
				</div>
			</div>
		</div>
	</footer>

	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
	<!-- Modal de Donación -->
	<div class="modal fade" id="donationModal" tabindex="-1"
		aria-labelledby="donationModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="donationModalLabel">Donar al
						proyecto</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Cerrar"></button>
				</div>
				<div class="modal-body">
					<!-- Explicación All or Nothing -->
					<p class="small text-muted mb-4">
						Esta campaña funciona bajo el modelo <strong>“todo o
							nada”</strong>: tu donación solo se cobrará si el proyecto alcanza su meta
						de recaudación. Hasta entonces, no se efectuará ningún cargo.
					</p>
					<form action="${pageContext.request.contextPath}/create-order"
						method="post">
						<div class="mb-3">
							<label for="amount" class="form-label"> Importe (EUR): </label> <input
								type="text" class="form-control" id="amount" name="amount"
								value="10.00" />
						</div>
						<button type="submit" class="btn btn-primary d-block mx-auto mt-3" 
						style="background-color:#003087; border-color:#003087; color:#fff;">
							<i class="fab fa-paypal me-2"></i>Donar con PayPal
						</button>
					</form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
