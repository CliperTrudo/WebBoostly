<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="author" content="Sergio Alfonseca Vallet">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta name="description"
	content="This is a login page template based on Bootstrap 5">
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id"
	content="797777584256-rv70sv8lpr6pvl9bbki0b029p0fse5se.apps.googleusercontent.com">
<title>Bootstrap 5 Login Page</title>
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

/* Estilos adicionales para el botón */
.google-login-btn {
	width: 100%;
	background-color: white;
	border: 2px solid #4285F4; /* Borde azul de Google */
	color: #4285F4; /* Texto azul */
	font-size: 16px;
	font-weight: 600;
	display: flex;
	align-items: center;
	justify-content: center;
}

.google-login-btn:hover {
	background-color: #f1f1f1; /* Color de fondo al hacer hover */
	border-color: #357AE8; /* Borde azul más oscuro al hacer hover */
}

.google-login-btn img {
	width: 20px;
	margin-right: 10px; /* Espacio entre el logo y el texto */
}
</style>
</head>

<body class="d-flex flex-column min-vh-100">

	<main class="flex-grow-1">
		<br> <br> <br>
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

								<form action="login" method="POST" class="needs-validation"
									autocomplete="off">

									<div class="mb-3">
										<label class="mb-2 text-muted" for="email">Mail:</label> <input
											id="email" type="email" class="form-control" name="email"
											value="" required>
									</div>

									<div class="mb-3">
										<label class="mb-2 text-muted" for="password">Contraseña:</label>
										<input id="password" type="password" class="form-control"
											name="password" required>
									</div>

									<p class="form-text text-muted mb-3">Para iniciar sesión
										todos los campos son obligatorios.</p>

									<div class="text-center">
										<button type="submit" class="btn btn-primary">Login</button>
									</div>

									<!-- Separador -->
									<div class="separator">
										<hr>
										<span>o</span>
										<hr>
									</div>

								</form>

								<a href="http://localhost:8080/webboostly/login/google"
									class="btn google-login-btn w-100 d-flex align-items-center justify-content-center">
									<img
									src="https://fonts.gstatic.com/s/i/productlogos/googleg/v6/24px.svg"
									alt="Google logo" style="width: 20px; margin-right: 10px;">
									Iniciar sesión con Google
								</a> <br>

								<div class="text-center">
									<a href="recuperar-contrasena"
										class="text-dark text-decoration-none">¿Has olvidado la
										contraseña?</a>
								</div>

							</div>

						</div>

					</div>

				</div>

			</div>
		</section>

		<!-- Enlace para registro -->
		<div class="container h-100">
			<div class="row justify-content-sm-center h-100">
				<div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
					<div class="card shadow-lg">
						<div class="card-body p-3">
							<div class="text-center">
								¿No tienes una cuenta? <a href="registro" class="text-dark">Regístrate</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</main>

	<footer
		class="d-flex flex-wrap justify-content-between align-items-center py-3 border-top bg-light">
		<div class="container d-flex justify-content-between">
			<div class="col-md-4 d-flex align-items-center">
				<a href="/"
					class="mb-3 me-2 mb-md-0 text-body-secondary text-decoration-none lh-1">
					<svg class="bi" width="30" height="24">
						<use xlink:href="#bootstrap"></use>
					</svg>
				</a> <span class="mb-3 mb-md-0 text-body-secondary">© 2024
					Company, Inc</span>
			</div>
		</div>
	</footer>
</body>
</html>
