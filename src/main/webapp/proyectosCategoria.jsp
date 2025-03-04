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
							<li class="nav-item"><a class="nav-link" href="cuenta.jsp">Cuenta</a></li>
							<li class="nav-item"><a class="nav-link" href="/webboostly/proyectosCategoria">Proyecto</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

	<br><br><br><br>
	<div class="container mt-5">
		<h2 class="text-center">Proyectos por Categoría</h2>

		<!-- Selector de Categoría -->
		<div class="mb-3">
			<label for="categoriaSelect" class="form-label">Selecciona
				una categoría:</label> <select id="categoriaSelect" class="form-select">
				<option value="1">Tecnología</option>
				<option value="4">Arte</option>
				<option value="5">Medio Ambiente</option>
			</select>
		</div>

		<div class="row" id="proyectosContainer">
			<!-- Aquí se cargarán los proyectos dinámicamente -->
		</div>
	</div>

	<script>
    document.addEventListener("DOMContentLoaded", function() {
        const selectCategoria = document.getElementById("categoriaSelect");
        const container = document.getElementById("proyectosContainer");

        function cargarProyectos(idCategoria) {
            fetch("/webboostly/proyectosCategoria?id=" + idCategoria)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error en la respuesta de la API");
                }
                return response.json();
            })
            .then(proyectos => {
                console.log("JSON recibido del backend:", proyectos);

                // Verificar si la respuesta es un array
                if (!Array.isArray(proyectos)) {
                    console.error("La respuesta de la API no es un array:", proyectos);
                    container.innerHTML = "<p class='text-center'>Error en la respuesta del servidor.</p>";
                    return;
                }

                container.innerHTML = "";

                if (proyectos.length === 0) {
                    container.innerHTML = "<p class='text-center'>No hay proyectos en esta categoría.</p>";
                } else {
                    proyectos.forEach(proyecto => {
                        console.log("Procesando proyecto:", proyecto);

                        // Crear elementos de la tarjeta
                        const cardContainer = document.createElement("div");
                        cardContainer.classList.add("col-md-4", "mb-4");

                        const card = document.createElement("div");
                        card.classList.add("card");
                        
                        const cardImg = document.createElement("img");
                        cardImg.classList.add("card-img-top");

                        if (proyecto.imagen1Proyecto) {
                            cardImg.src = "data:image/png;base64," + proyecto.imagen1Proyecto;
                            cardImg.alt = "Imagen del proyecto";
                        } else {
                        	cardImg.src = "./assets/img/candado.png"; // Imagen de relleno si no hay imagen
                        }

                        const cardBody = document.createElement("div");
                        cardBody.classList.add("card-body");

                        const cardTitle = document.createElement("h5");
                        cardTitle.classList.add("card-title");
                        console.log("Tipo de nombreProyecto:", typeof proyecto.nombreProyecto, "Valor:", proyecto.nombreProyecto);
                        cardTitle.textContent = "Nombre: " + proyecto.nombreProyecto;
                        console.log("Nombre asignado:", cardTitle.textContent);
                        
                        const cardText = document.createElement("p");
                        cardText.classList.add("card-text");
                        console.log("Tipo de descripcionProyecto:", typeof proyecto.descripcionProyecto, "Valor:", proyecto.descripcionProyecto);
                        cardText.textContent = "Descripcion: " + proyecto.descripcionProyecto;
                        console.log("Descripción asignada:", cardText.textContent);

                        const cardLink = document.createElement("a");
                        cardLink.classList.add("btn", "btn-primary");
                        cardLink.href = `proyecto.jsp?id=${proyecto.idProyecto}`;
                        cardLink.textContent = "Ver más";

                        // Ensamblar la tarjeta
                        cardBody.appendChild(cardTitle);
                        cardBody.appendChild(cardText);
                        cardBody.appendChild(cardLink);
                        card.appendChild(cardImg);
                        card.appendChild(cardBody);
                        
                        cardContainer.appendChild(card);

                        // Verificar antes de añadir al contenedor
                        console.log("Tarjeta generada:", cardContainer.innerHTML);

                        // Insertar con un pequeño retraso para asegurar renderizado
                        setTimeout(() => {
                            container.appendChild(cardContainer);
                            console.log("Contenido del contenedor actualizado:", container.innerHTML);
                        }, 100);
                    });
                }
            })
            .catch(error => console.error("Error al obtener proyectos:", error));
        }

        // Cargar la primera categoría por defecto
        cargarProyectos(selectCategoria.value);

        // Escuchar cambios en el selector de categorías
        selectCategoria.addEventListener("change", function() {
            cargarProyectos(this.value);
        });
    });
</script>




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
