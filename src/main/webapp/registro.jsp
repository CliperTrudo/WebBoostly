<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="author" content="Tu Nombre">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta name="description" content="Formulario para registrar usuario">
<title>Registro de Usuario</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">

<style type="text/css">
.separator {
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 20px 0;
}

.separator hr {
	border: 0;
	border-top: 1px solid #000;
	width: 100px;
	margin: 0 10px;
}

.separator span {
	margin: 0 10px;
	font-size: 20px;
}

.google-login-btn {
	width: 100%;
	background-color: white;
	border: 2px solid #4285F4;
	color: #4285F4;
	font-size: 16px;
	font-weight: 600;
	display: flex;
	align-items: center;
	justify-content: center;
}

.google-login-btn:hover {
	background-color: #f1f1f1;
	border-color: #357AE8;
}

.google-login-btn img {
	width: 20px;
	margin-right: 10px;
}

.invalid-feedback {
	display: none;
}

.was-validated .invalid-feedback {
	display: block;
}
</style>
</head>

<body class="d-flex flex-column min-vh-100">
	<main class="flex-grow-1">
		<br>
		<section class="h-100">
			<div class="container h-100">
				<div class="row justify-content-sm-center h-100">
					<div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
						<div class="card shadow-lg">
							<div class="card-body p-5">
								<div class="text-center">
									<img src="./assets/img/logoNegro.png" alt="logo" width="80%">
								</div>
								<br>
								<div class="text-center">
									<h3 class="h5 mb-3">
										Regístrate para acceder a todas las funciones
									</h3>
								</div>

								<a href="http://localhost:8080/webboostly/login/google"
									class="btn google-login-btn w-100 d-flex align-items-center justify-content-center">
									<img
									src="https://fonts.gstatic.com/s/i/productlogos/googleg/v6/24px.svg"
									alt="Google logo" style="width: 20px; margin-right: 10px;">
									Iniciar sesión con Google
								</a>

								<div class="separator">
									<hr>
									<span>o</span>
									<hr>
								</div>

								<form action="registro" method="POST" class="needs-validation" id="registroForm"
									autocomplete="off" novalidate>

									<div class="mb-3">
										<label for="nombre_usuario" class="form-label">Nombre:</label>
										<input type="text" id="nombre_usuario" name="nombre_usuario"
											class="form-control" required pattern="[A-Za-záéíóúÁÉÍÓÚñÑ\s]{2,50}"
											title="El nombre debe contener solo letras y tener entre 2 y 50 caracteres">
										<div class="invalid-feedback">
											Por favor, introduce un nombre válido (2-50 letras).
										</div>
									</div>

									<div class="mb-3">
										<label for="apellidos_usuarios" class="form-label">Apellidos:</label>
										<input type="text" id="apellidos_usuarios" name="apellidos_usuarios"
											class="form-control" required pattern="[A-Za-záéíóúÁÉÍÓÚñÑ\s]{2,100}"
											title="Los apellidos deben contener solo letras y tener entre 2 y 100 caracteres">
										<div class="invalid-feedback">
											Por favor, introduce apellidos válidos (2-100 letras).
										</div>
									</div>

									<div class="mb-3">
										<label for="mail_usuario" class="form-label">Email:</label>
										<input type="email" id="mail_usuario" name="mail_usuario"
											class="form-control" required
											pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
											title="Por favor, introduce un correo electrónico válido">
										<div class="invalid-feedback">
											Por favor, introduce un correo electrónico válido.
										</div>
									</div>

									<div class="mb-3">
										<label for="fecha_nacimiento_usuario" class="form-label">Fecha de Nacimiento:</label>
										<input type="date" id="fecha_nacimiento_usuario" name="fecha_nacimiento_usuario"
											class="form-control" required>
										<div class="invalid-feedback">
											Por favor, selecciona una fecha de nacimiento válida.
										</div>
									</div>

									<div class="mb-3">
										<label for="nickname_usuario" class="form-label">Nickname:</label>
										<input type="text" id="nickname_usuario" name="nickname_usuario"
											class="form-control" required pattern="[A-Za-z0-9_]{3,20}"
											title="El nickname debe tener entre 3 y 20 caracteres alfanuméricos o guiones bajos">
										<div class="invalid-feedback">
											El nickname debe tener 3-20 caracteres alfanuméricos o guiones bajos.
										</div>
									</div>

									<div class="mb-3">
										<label for="contrasenya_usuario" class="form-label">Contraseña:</label>
										<input type="password" id="contrasenya_usuario" name="contrasenya_usuario"
											class="form-control" required pattern=".{8,}"
											title="La contraseña debe tener al menos 8 caracteres">
										<div class="invalid-feedback">
											La contraseña debe tener al menos 8 caracteres.
										</div>
									</div>

									<div class="mb-3">
										<label for="confirmar_contrasenya_usuario" class="form-label">Confirmar Contraseña:</label>
										<input type="password" id="confirmar_contrasenya_usuario"
											name="confirmar_contrasenya_usuario" class="form-control" required>
										<div class="invalid-feedback" id="confirmar_contrasenya_feedback">
											Las contraseñas no coinciden.
										</div>
									</div>

									<div class="mb-3">
										<label for="dni_usuario" class="form-label">DNI:</label>
										<input type="text" id="dni_usuario" name="dni_usuario"
											class="form-control" required pattern="\d{8}[A-Za-z]"
											title="El DNI debe tener 8 dígitos seguidos de una letra">
										<div class="invalid-feedback">
											El DNI debe tener 8 dígitos seguidos de una letra.
										</div>
									</div>

									<div class="mb-3">
										<label for="telefono_usuario" class="form-label">Teléfono:</label>
										<input type="text" id="telefono_usuario" name="telefono_usuario"
											class="form-control" required pattern="\d{9}"
											title="El teléfono debe tener 9 dígitos">
										<div class="invalid-feedback">
											El teléfono debe tener 9 dígitos.
										</div>
									</div>

									<div class="text-center">
										<button type="submit" class="btn btn-primary">Registrar</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<div class="container h-100">
			<div class="row justify-content-sm-center h-100">
				<div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
					<div class="card shadow-lg">
						<div class="card-body p-3">
							<div class="text-center">
								¿Ya tienes una cuenta? <a href="login" class="text-dark">Inicia sesión</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
	<br>

	<footer
		class="d-flex flex-wrap justify-content-between align-items-center py-3 border-top bg-light">
		<div class="container d-flex justify-content-between">
			<div class="col-md-4 d-flex align-items-center">
				<span class="mb-3 mb-md-0 text-body-secondary">© 2025 BOOSTLY</span>
			</div>
		</div>
	</footer>

	<script>
		(function () {
			'use strict'

			var form = document.getElementById('registroForm');

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

			function validatePasswordMatch() {
				var password = document.getElementById('contrasenya_usuario').value;
				var confirmPassword = document.getElementById('confirmar_contrasenya_usuario').value;
				var confirmInput = document.getElementById('confirmar_contrasenya_usuario');

				if (password !== confirmPassword) {
					confirmInput.setCustomValidity('Las contraseñas no coinciden.');
					return false;
				} else {
					confirmInput.setCustomValidity('');
					return true;
				}
			}

			// Validate date of birth on change
			var dateInput = document.getElementById('fecha_nacimiento_usuario');
			dateInput.addEventListener('change', function () {
				validateDateOfBirth(dateInput);
			});

			// Validate password match on input
			var passwordInputs = [
				document.getElementById('contrasenya_usuario'),
				document.getElementById('confirmar_contrasenya_usuario')
			];
			passwordInputs.forEach(function (input) {
				input.addEventListener('input', validatePasswordMatch);
			});

			form.addEventListener('submit', function (event) {
				event.preventDefault();
				event.stopPropagation();

				var isValid = form.checkValidity();
				var isDateValid = validateDateOfBirth(dateInput);
				var isPasswordValid = validatePasswordMatch();

				if (isValid && isDateValid && isPasswordValid) {
					form.submit();
				} else {
					form.classList.add('was-validated');
				}
			}, false);
		})();
	</script>
</body>
</html>