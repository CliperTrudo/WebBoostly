<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Crear Proyecto</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/js/all.min.js" crossorigin="anonymous"></script>
<style>
body {
    background-image: url("assets/img/fondoProyecto.png");
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-attachment: fixed;
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

/* Espaciado para asegurar que el formulario no quede debajo de la barra fija */
body {
    padding-top: 80px; /* Ajusta según el tamaño de tu barra de navegación */
}

footer {
    width: 100%;
    position: relative;
    bottom: 0;
    left: 0;
    padding: 20px;
}
.navbar-nav .nav-link:hover {
    color: gold !important; /* Aplica color dorado */
}

.navbar-nav .nav-link {
    transition: color 0.3s ease, text-decoration 0.3s ease;
}
</style>
</head>
<body>

<!-- Barra de Navegación -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top px-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="#page-top">
            <img src="assets/img/logoo.png" alt="Logo" style="height: 30%; width: 30%;" />
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link active" href="#">Inicio</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Servicios</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Explora</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Login</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Contenedor de Formulario -->
<div class="containerform mt-5">
    <h2 class="text-center">
        <i class="fas fa-folder-plus"></i> Crear Nuevo Proyecto
    </h2>
    <form action="/webboostly/proyecto" method="POST"  method="POST" class="needs-validation" autocomplete="off">
        <div class="mb-3">
            <label for="nombreProyecto" class="form-label">Nombre del Proyecto</label>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-pen"></i></span>
                <input type="text" class="form-control" id="nombreProyecto" name="nombreProyecto" required>
            </div>
        </div>
        <div class="mb-3">
            <label for="descripcionProyecto" class="form-label">Descripción</label>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-file-alt"></i></span>
                <textarea class="form-control" id="descripcionProyecto" name="descripcionProyecto" rows="3" required></textarea>
            </div>
        </div>
        <div class="mb-3">
            <label for="imagen1Proyecto" class="form-label">Imagen 1</label>
            <input type="file" class="form-control" id="imagen1Proyecto" name="imagen1Proyecto">
        </div>
        <div class="mb-3">
            <label for="imagen2Proyecto" class="form-label">Imagen 2</label>
            <input type="file" class="form-control" id="imagen2Proyecto" name="imagen2Proyecto">
        </div>
        <div class="mb-3">
            <label for="imagen3Proyecto" class="form-label">Imagen 3</label>
            <input type="file" class="form-control" id="imagen3Proyecto" name="imagen3Proyecto">
        </div>
        <div class="mb-3">
            <label for="fechaFinalizacionProyecto" class="form-label">Fecha de Finalización</label>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                <input type="date" class="form-control" id="fechaFinalizacionProyecto" name="fechaFinalizacionProyecto" required>
            </div>
        </div>
        <div class="mb-3">
            <label for="metaRecaudacionProyecto" class="form-label">Meta de Recaudación</label>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-money-bill-wave"></i></span>
                <input type="number" class="form-control" id="metaRecaudacionProyecto" name="metaRecaudacionProyecto" required>
            </div>
        </div>
        <div class="mb-3">
            <label for="categoriaProyecto" class="form-label">Categoría</label>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-list"></i></span>
                <select class="form-select" id="categoriaProyecto" name="categoriaProyecto" required>
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

<!-- Footer -->
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
