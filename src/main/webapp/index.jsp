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
<style type="text/css">
.carousel-item:nth-child(1) .masthead {
	background-image: url('assets/img/mesa-idea.png');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}

.carousel-item:nth-child(2) .masthead {
	background-image: url('assets/img/carrusel2.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
}

.carousel-item:nth-child(3) .masthead {
	background-image: url('assets/img/carrusel3.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
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

	<!-- Carrusel de Bootstrap -->
	<div id="carouselExampleIndicators" class="carousel slide"
		data-bs-ride="carousel">
		<div class="carousel-indicators">
			<button type="button" data-bs-target="#carouselExampleIndicators"
				data-bs-slide-to="0" class="active" aria-current="true"
				aria-label="Slide 1"></button>
			<button type="button" data-bs-target="#carouselExampleIndicators"
				data-bs-slide-to="1" aria-label="Slide 2"></button>
			<button type="button" data-bs-target="#carouselExampleIndicators"
				data-bs-slide-to="2" aria-label="Slide 3"></button>
		</div>
		<div class="carousel-inner">
			<!-- Slide 1: Masthead (Header) -->
			<div class="carousel-item active">
				<header class="masthead">
					<div class="container text-center">
						<div class="masthead-subheading">¡Obtén Ayuda con Donaciones
							Solidarias!</div>
						<div class="masthead-heading text-uppercase">Comparte Tu
							Proyecto</div>
						<a class="btn btn-primary btn-xl text-uppercase"
							href="/webboostly/login">Saber más</a>
					</div>
				</header>
			</div>
			<!-- Slide 2: Información adicional -->
			<div class="carousel-item">
				<header class="masthead">
					<div class="container text-center">
						<div class="masthead-subheading">Únete a Nuestra Comunidad</div>
						<div class="masthead-heading text-uppercase">Crea un Impacto
							Positivo</div>
						<a class="btn btn-primary btn-xl text-uppercase"
							href="/webboostly/login">Descubre más</a>
					</div>
				</header>
			</div>
			<!-- Slide 3: Información adicional -->
			<div class="carousel-item">
				<header class="masthead">
					<div class="container text-center">
						<div class="masthead-subheading">Conéctate con Donantes</div>
						<div class="masthead-heading text-uppercase">Haz Crecer Tu
							Proyecto</div>
						<a class="btn btn-primary btn-xl text-uppercase"
							href="/webboostly/login">Ver más</a>
					</div>
				</header>
			</div>
		</div>
		<button class="carousel-control-prev" type="button"
			data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button"
			data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Next</span>
		</button>
	</div>

	<!-- Footer-->
	<footer class="footer py-4">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-lg-4 text-lg-start">Copyright &copy; BOOSTLY 2025</div>
				
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


