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
	background-position: center;
	background-repeat: no-repeat;
}

.fmProyecto {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.containerform {
    background: #ffffff;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    width: 50%;
    margin-bottom: 20px;
}

.invalid-feedback {
	display: none;
}

.was-validated .invalid-feedback {
	display: block;
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
				class="needs-validation" id="proyectoForm" novalidate>
				<div class="mb-3">
					<label for="nombreProyecto" class="form-label">Nombre del Proyecto</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-pen"></i></span>
						<input type="text" class="form-control" id="nombreProyecto"
							name="nombreProyecto" required pattern=".{3,100}"
							title="El nombre del proyecto debe tener entre 3 y 100 caracteres">
						<div class="invalid-feedback">
							El nombre del proyecto debe tener entre 3 y 100 caracteres.
						</div>
					</div>
				</div>
				<div class="mb-3">
					<label for="descripcionProyecto" class="form-label">Descripción</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-file-alt"></i></span>
						<textarea class="form-control" id="descripcionProyecto"
							name="descripcionProyecto" rows="3" required
							pattern=".{10,500}" title="La descripción debe tener entre 10 y 500 caracteres"></textarea>
						<div class="invalid-feedback">
							La descripción debe tener entre 10 y 500 caracteres.
						</div>
					</div>
				</div>
				<div class="mb-3">
					<label for="imagen1Proyecto" class="form-label">Imagen 1</label>
					<input type="file" class="form-control" id="imagen1Proyecto"
						name="imagen1Proyecto" required accept="image/jpeg,image/png,image/gif"
						data-max-size="5242880">
					<div class="invalid-feedback" id="imagen1ProyectoFeedback">
						Por favor, selecciona un archivo de imagen válido (JPEG, PNG, GIF, máx. 5MB).
					</div>
				</div>
				<div class="mb-3">
					<label for="imagen2Proyecto" class="form-label">Imagen 2</label>
					<input type="file" class="form-control" id="imagen2Proyecto"
						name="imagen2Proyecto" required accept="image/jpeg,image/png,image/gif"
						data-max-size="5242880">
					<div class="invalid-feedback" id="imagen2ProyectoFeedback">
						Por favor, selecciona un archivo de imagen válido (JPEG, PNG, GIF, máx. 5MB).
					</div>
				</div>
				<div class="mb-3">
					<label for="imagen3Proyecto" class="form-label">Imagen 3</label>
					<input type="file" class="form-control" id="imagen3Proyecto"
						name="imagen3Proyecto" required accept="image/jpeg,image/png,image/gif"
						data-max-size="5242880">
					<div class="invalid-feedback" id="imagen3ProyectoFeedback">
						Por favor, selecciona un archivo de imagen válido (JPEG, PNG, GIF, máx. 5MB).
					</div>
				</div>
				<div class="mb-3">
					<label for="fechaFinalizacionProyecto" class="form-label">Fecha de Finalización</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-calendar"></i></span>
						<input type="date" class="form-control" id="fechaFinalizacionProyecto"
							name="fechaFinalizacionProyecto" required>
						<div class="invalid-feedback">
							Por favor, selecciona una fecha de finalización futura.
						</div>
					</div>
				</div>
				<div class="mb-3">
					<label for="metaRecaudacionProyecto" class="form-label">Meta de Recaudación</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-money-bill-wave"></i></span>
						<input type="number" class="form-control" id="metaRecaudacionProyecto"
							name="metaRecaudacionProyecto" required min="1" step="0.01"
							title="La meta de recaudación debe ser un número positivo">
						<div class="invalid-feedback">
							La meta de recaudación debe ser un número positivo.
						</div>
					</div>
				</div>
				<div class="mb-3">
					<label for="categoriaProyecto" class="form-label">Categoría</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-list"></i></span>
						<select class="form-select" id="categoriaProyecto"
							name="categoriaProyecto" required>
							<option value="" disabled selected>Selecciona una categoría</option>
							<option value="Educación">Educación</option>
							<option value="Salud">Salud</option>
							<option value="Medio Ambiente">Medio Ambiente</option>
							<option value="1">Tecnología</option>
							<option value="Arte">Arte</option>
						</select>
						<div class="invalid-feedback">
							Por favor, selecciona una categoría.
						</div>
					</div>
				</div>
				<button type="submit" class="btn btn-primary">
					<i class="fas fa-save"></i> Crear Proyecto
				</button>
			</form>
		</div>
	</div>

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
	<script>
		(function () {
			'use strict'

			var form = document.getElementById('proyectoForm');

			function validateDate(input) {
				var selectedDate = new Date(input.value);
				var today = new Date();
				today.setHours(0, 0, 0, 0); // Reset time for comparison
				if (selectedDate <= today) {
					input.setCustomValidity('La fecha de finalización debe ser futura.');
					return false;
				} else {
					input.setCustomValidity('');
					return true;
				}
			}

			function validateImage(input, feedbackId) {
				var maxSize = parseInt(input.getAttribute('data-max-size')); // 5MB in bytes
				var allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
				var file = input.files[0];

				if (!file) {
					input.setCustomValidity('Por favor, selecciona una imagen.');
					return false;
				}

				if (!allowedTypes.includes(file.type)) {
					input.setCustomValidity('Solo se permiten imágenes JPEG, PNG o GIF.');
					document.getElementById(feedbackId).textContent = 'Solo se permiten imágenes JPEG, PNG o GIF.';
					return false;
				}

				if (file.size > maxSize) {
					input.setCustomValidity('La imagen no debe exceder los 5MB.');
					document.getElementById(feedbackId).textContent = 'La imagen no debe exceder los 5MB.';
					return false;
				}

				input.setCustomValidity('');
				document.getElementById(feedbackId).textContent = 'Por favor, selecciona un archivo de imagen válido (JPEG, PNG, GIF, máx. 5MB).';
				return true;
			}

			var dateInput = document.getElementById('fechaFinalizacionProyecto');
			dateInput.addEventListener('change', function () {
				validateDate(dateInput);
			});

			var imageInputs = [
				{ input: document.getElementById('imagen1Proyecto'), feedback: 'imagen1ProyectoFeedback' },
				{ input: document.getElementById('imagen2Proyecto'), feedback: 'imagen2ProyectoFeedback' },
				{ input: document.getElementById('imagen3Proyecto'), feedback: 'imagen3ProyectoFeedback' }
			];

			imageInputs.forEach(function (item) {
				item.input.addEventListener('change', function () {
					validateImage(item.input, item.feedback);
				});
			});

			form.addEventListener('submit', function (event) {
				event.preventDefault();
				event.stopPropagation();

				var isValid = form.checkValidity();
				var isDateValid = validateDate(dateInput);
				var areImagesValid = imageInputs.every(function (item) {
					return validateImage(item.input, item.feedback);
				});

				if (isValid && isDateValid && areImagesValid) {
					form.submit();
				} else {
					form.classList.add('was-validated');
				}
			}, false);
		})();
	</script>
</body>
</html>