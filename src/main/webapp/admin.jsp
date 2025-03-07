<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="dtos.UsuarioDto"%>
<%@ page import="dtos.ProyectoDto"%>
<%@ page import="java.util.List"%>
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
body {
	background-image: url('./assets/img/fondoExagonos.jpg');
	background-size: cover;
	/* Ajusta la imagen para cubrir toda la pantalla */
	background-position: center; /* Centra la imagen */
	background-repeat: no-repeat; /* Evita que la imagen se repita */
}

.profile-container {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	height: 100vh;
	padding: 20px;
}

.profile-card {
	width: 100%;
	max-width: 500px;
	background: white;
	border-radius: 15px;
	padding: 20px;
	box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1);
	text-align: center;
}

.profile-img {
	width: 150px;
	height: 150px;
	object-fit: cover;
	border-radius: 50%;
	border: 4px solid #007bff;
}

.profile-card h3 {
	margin-top: 15px;
	font-size: 1.8rem;
	color: #007bff;
}

.profile-card p {
	margin: 5px 0;
	font-size: 1.1rem;
}

.btn-custom {
	margin-top: 15px;
	background: #007bff;
	border: none;
	padding: 10px 20px;
	font-size: 1rem;
	border-radius: 30px;
	color: white;
	font-weight: bold;
	transition: all 0.3s ease;
}

.btn-custom:hover {
	background: #0056b3;
}
</style>
</head>
<body id="page-top">
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


	<%
	UsuarioDto usuario = (UsuarioDto) request.getAttribute("usuario");
	%>
	<%
	List<ProyectoDto> proyectos = (List<ProyectoDto>) request.getAttribute("proyectos");
	%>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="container mt-5">
		<div class="row d-flex justify-content-between align-items-stretch">
			<!-- Card del usuario -->
			<div class="col-md-5">
				<div class="card h-100">
					<div class="card-body text-center">
						<img
							src="<%=(usuario != null && usuario.getImgUsuario() != null)
		? "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(usuario.getImgUsuario())
		: "https://img.freepik.com/vector-premium/icono-perfil-avatar-predeterminado-imagen-usuario-redes-sociales-icono-avatar-gris-silueta-perfil-blanco-ilustracion-vectorial_561158-3383.jpg?semt=ais_hybrid"%>"
							class="profile-img mb-3 img-fluid rounded-circle"
							alt="Imagen de Usuario">
						<h3><%=(usuario != null) ? usuario.getNombreUsuario() + " " + usuario.getApellidosUsuario()
		: "Usuario no identificado"%></h3>
						<p class="text-muted">
							@<%=(usuario != null) ? usuario.getNicknameUsuario() : "Sin nickname"%></p>
						<p>
							<strong>Correo:</strong>
							<%=(usuario != null) ? usuario.getMailUsuario() : "No disponible"%></p>
						<p>
							<strong>Fecha de Nacimiento:</strong>
							<%=(usuario != null) ? usuario.getFechaNacimientoUsuario() : "No disponible"%></p>
						<p>
							<strong>Teléfono:</strong>
							<%=(usuario != null) ? usuario.getTelefonoUsuario() : "No disponible"%></p>
						<p>
							<strong>DNI:</strong>
							<%=(usuario != null) ? usuario.getDniUsuario() : "No disponible"%></p>
						<p>
							<strong>Descripción:</strong>
							<%=(usuario != null) ? usuario.getDescripcionUsuario() : "No disponible"%></p>
						<p>
							<strong>Fecha de Alta:</strong>
							<%=(usuario != null) ? usuario.getFechaAltaUsuario() : "No disponible"%></p>

						<a href="#" class="btn btn-primary">Editar Perfil</a>
					</div>
				</div>
			</div>

			<!-- Card de Proyectos -->
			<div class="col-md-5">
				<div class="card h-100">
					<div class="card-body">
						<h3>Proyectos</h3>
						<div class="list-group" id="myList" role="tablist">
							<%
							if (proyectos != null && !proyectos.isEmpty()) {
							%>
							<%
							for (ProyectoDto proyecto : proyectos) {
							%>
							<a class="list-group-item list-group-item-action"
								data-toggle="list" href="proyectoMostrar?id=<%=proyecto.getIdProyecto()%>" role="tab"><%=proyecto.getNombreProyecto()%></a>
							
							<%
							}
							%>
							<%
							} else {
							%>
							<a>No hay proyectos asociados a
								este usuario.</a>
							<%
							}
							%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- Footer-->
	<footer class="footer py-4 mt-5">
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
</body>
</html>