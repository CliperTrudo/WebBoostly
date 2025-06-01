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

body {
	background-image: url('./assets/img/fodoSolapado.jpg');
	background-size: cover;
	/* Ajusta la imagen para cubrir toda la pantalla */
	background-position: center; /* Centra la imagen */
	background-repeat: no-repeat; /* Evita que la imagen se repita */
}

.fmProyecto {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column; /* Asegura que los elementos estén en una columna */
}

.containerform {
    background: #ffffff; /* Fondo completamente blanco (opaco) */
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    width: 50%;
    margin-bottom: 20px; /* Asegura que haya espacio entre el formulario y el footer */
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
	<br>
	<br>
	<br>
	<br>
	<div class="fmProyecto">
		<div class="containerform mt-5">
			<h2 class="text-center">
				<i class="fas fa-folder-plus"></i> Crear Nuevo Proyecto
			</h2>
			<form action="proyecto" method="POST" enctype="multipart/form-data"
				class="needs-validation">
				<div class="mb-3">
					<label for="nombreProyecto" class="form-label">Nombre del
						Proyecto</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-pen"></i></span> <input
							type="text" class="form-control" id="nombreProyecto"
							name="nombreProyecto" required>
					</div>
				</div>
				<div class="mb-3">
					<label for="descripcionProyecto" class="form-label">Descripción</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-file-alt"></i></span>
						<textarea class="form-control" id="descripcionProyecto"
							name="descripcionProyecto" rows="3" required></textarea>
					</div>
				</div>
				<div class="mb-3">
					<label for="imagen1Proyecto" class="form-label">Imagen 1</label> <input
						type="file" class="form-control" id="imagen1Proyecto"
						name="imagen1Proyecto">
				</div>
				<div class="mb-3">
					<label for="imagen2Proyecto" class="form-label">Imagen 2</label> <input
						type="file" class="form-control" id="imagen2Proyecto"
						name="imagen2Proyecto">
				</div>
				<div class="mb-3">
					<label for="imagen3Proyecto" class="form-label">Imagen 3</label> <input
						type="file" class="form-control" id="imagen3Proyecto"
						name="imagen3Proyecto">
				</div>
				<div class="mb-3">
					<label for="fechaFinalizacionProyecto" class="form-label">Fecha
						de Finalización</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-calendar"></i></span>
						<input type="date" class="form-control"
							id="fechaFinalizacionProyecto" name="fechaFinalizacionProyecto"
							required>
					</div>
				</div>
				<div class="mb-3">
					<label for="metaRecaudacionProyecto" class="form-label">Meta
						de Recaudación</label>
					<div class="input-group">
						<span class="input-group-text"><i
							class="fas fa-money-bill-wave"></i></span> <input type="number"
							class="form-control" id="metaRecaudacionProyecto"
							name="metaRecaudacionProyecto" required>
					</div>
				</div>
				<div class="mb-3">
					<label for="categoriaProyecto" class="form-label">Categoría</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-list"></i></span>
						<select class="form-select" id="categoriaProyecto"
							name="categoriaProyecto" required>
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

	

	<!-- Footer-->
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
