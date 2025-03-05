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
	<br><br><br><br>
	<div class="container mt-5">
		<!-- Project Header -->
		<div class="project-header text-center">
			<h1 class="display-4">Proyecto: Clipertropia</h1>
			<p class="lead">Descripción del proyecto y detalles importantes.</p>
		</div>

		<!-- Project Details -->
		<div class="row">
			<!-- Project Description -->
			<div class="col-md-8">
				<div class="project-card">
					<h3>Descripción del Proyecto</h3>
					<p>asd asd asd asd as d</p>
				</div>
			</div>

			<!-- Project Meta Information -->
			<div class="col-md-4">
				<div class="project-card">
					<h3>Información del Proyecto</h3>
					<ul class="list-unstyled">
						<li><strong>Fecha de Inicio:</strong> 02 de marzo de 2025</li>
						<li><strong>Fecha de Finalización:</strong> 12 de diciembre
							de 2025</li>
						<li><strong>Categoría:</strong> 1</li>
						<li><strong>Usuario:</strong> 4</li>
						<li><strong>Meta de Recaudación:</strong> 2</li>
					</ul>
				</div>
			</div>
		</div>

		<!-- Project Images -->
		<div class="row">
			<div class="col-md-4">
				<div class="project-card">
					<h3>Imagen 1</h3>
					<img src="" class="project-image" alt="Imagen del proyecto" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="project-card">
					<h3>Imagen 2</h3>
					<img src="" class="project-image" alt="Imagen del proyecto" />
				</div>
			</div>
			<div class="col-md-4">
				<div class="project-card">
					<h3>Imagen 3</h3>
					<img src="" class="project-image" alt="Imagen del proyecto" />
				</div>
			</div>
		</div>

		<!-- Meta and Extra Info -->
		<div class="meta-info mt-5">
			<h4>Detalles adicionales</h4>
			<p>Aquí puedes agregar cualquier información extra que desees
				mostrar sobre el proyecto, como avances, comentarios o
				características específicas.</p>
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
</body>
</html>
