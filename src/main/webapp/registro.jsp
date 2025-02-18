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
		<br>
		<section class="h-100">
			<div class="container h-100">
				<div class="row justify-content-sm-center h-100">
					<div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
						<div class="card shadow-lg">
							<div class="card-body p-5">
								<div class="text-center ">
									<img src="./assets/img/logoNegro.png" alt="logo" width="80%">
								</div>
								<br>

								<div class="text-center">
									<h3 class="h5 mb-3">
										Registrate para acceder a todas las funciones
										</h1>
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

								<form action="registro" method="POST" class="needs-validation"
									autocomplete="off">

									<div class="mb-3">
										<label for="nombre_usuario" class="form-label">Nombre:</label>
										<input type="text" id="nombre_usuario" name="nombre_usuario"
											class="form-control" required>
									</div>

									<div class="mb-3">
										<label for="apellidos_usuarios" class="form-label">Apellidos:</label>
										<input type="text" id="apellidos_usuarios"
											name="apellidos_usuarios" class="form-control" required>
									</div>

									<div class="mb-3">
										<label for="mail_usuario" class="form-label">Email:</label> <input
											type="email" id="mail_usuario" name="mail_usuario"
											class="form-control" required>
									</div>

									<div class="mb-3">
										<label for="fecha_nacimiento_usuario" class="form-label">Fecha
											de Nacimiento:</label> <input type="date"
											id="fecha_nacimiento_usuario" name="fecha_nacimiento_usuario"
											class="form-control">
									</div>

									<div class="mb-3">
										<label for="nickname_usuario" class="form-label">Nickname:</label>
										<input type="text" id="nickname_usuario"
											name="nickname_usuario" class="form-control" required>
									</div>

									<div class="mb-3">
										<label for="contrasenya_usuario" class="form-label">Contraseña:</label>
										<input type="password" id="contrasenya_usuario"
											name="contrasenya_usuario" class="form-control" required>
									</div>

									<div class="mb-3">
										<label for="confirmar_contrasenya_usuario" class="form-label">Confirmar
											Contraseña:</label> <input type="password"
											id="confirmar_contrasenya_usuario"
											name="confirmar_contrasenya_usuario" class="form-control"
											required pattern=".{6,}"
											title="La contraseña debe coincidir con la ingresada anteriormente.">
									</div>



									<div class="mb-3">
										<label for="dni_usuario" class="form-label">DNI:</label> <input
											type="text" id="dni_usuario" name="dni_usuario"
											class="form-control" required>
									</div>

									<div class="mb-3">
										<label for="telefono_usuario" class="form-label">Teléfono:</label>
										<input type="text" id="telefono_usuario"
											name="telefono_usuario" class="form-control" required>
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
								¿ya tienes una cuenta? <a href="login" class="text-dark">Inicia
									sesión</a>
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
				<span class="mb-3 mb-md-0 text-body-secondary">© 2025
					Company, Inc</span>
			</div>
		</div>
	</footer>


</body>
</html>
