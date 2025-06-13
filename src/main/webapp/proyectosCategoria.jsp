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
        background-image: url('./assets/img/fondoLisoEstiloPapel.jpg');
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
            <a class="navbar-brand" href="#page-top">
                <img src="assets/img/logoo.png" alt="Logo"
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

    <br><br><br><br>
    <div class="container mt-5">
        <h2 class="text-center">Proyectos por Categoría</h2>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <div class="d-flex align-items-center">
                <label for="categoriaSelect" class="form-label me-2 mb-0">
                    Selecciona una categoría:
                </label>
                <select id="categoriaSelect" class="form-select w-30">
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat.idCategoria}">
                            ${cat.nombreCategoria}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button class="btn bg-success text-white"
                onclick="window.location.href='/webboostly/proyecto'">
                Crear Proyecto
            </button>
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
                    if (!Array.isArray(proyectos)) {
                        container.innerHTML = "<p class='text-center'>Error en la respuesta del servidor.</p>";
                        return;
                    }

                    container.innerHTML = "";
                    if (proyectos.length === 0) {
                        container.innerHTML = "<p class='text-center'>No hay proyectos en esta categoría.</p>";
                    } else {
                        proyectos.forEach(proyecto => {
                            const cardContainer = document.createElement("div");
                            cardContainer.classList.add("col-md-4", "mb-4");

                            const card = document.createElement("div");
                            card.classList.add("card");

                            const cardImg = document.createElement("img");
                            cardImg.classList.add("card-img-top");
                            if (proyecto.imagen1Proyecto) {
                                cardImg.src = "data:image/png;base64," + proyecto.imagen1Proyecto;
                            } else {
                                cardImg.src = "./assets/img/candado.png";
                            }
                            cardImg.alt = "Imagen del proyecto";

                            const cardBody = document.createElement("div");
                            cardBody.classList.add("card-body");

                            const cardTitle = document.createElement("h5");
                            cardTitle.classList.add("card-title");
                            cardTitle.textContent = proyecto.nombreProyecto;

                            const cardText = document.createElement("p");
                            cardText.classList.add("card-text");
                            cardText.textContent = proyecto.descripcionProyecto;

                            const cardLink = document.createElement("a");
                            cardLink.classList.add("btn", "btn-primary");
                            cardLink.href = "proyectoMostrar?id=" + proyecto.idProyecto;
                            cardLink.textContent = "Ver más";

                            cardBody.append(cardTitle, cardText, cardLink);
                            card.append(cardImg, cardBody);
                            cardContainer.appendChild(card);

                            setTimeout(() => {
                                container.appendChild(cardContainer);
                            }, 100);
                        });
                    }
                })
                .catch(error => console.error("Error al obtener proyectos:", error));
            }

            // Cargar proyectos de la primera categoría al inicio
            cargarProyectos(selectCategoria.value);

            // Volver a cargar al cambiar selección
            selectCategoria.addEventListener("change", () => {
                cargarProyectos(selectCategoria.value);
            });
        });
    </script>

    <!-- Footer-->
    <footer class="footer py-4">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-4 text-lg-start">
                    Copyright &copy; BOOSTLY 2025
                </div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap core JS-->
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js">
    </script>
    <!-- Core theme JS-->
    <script src="js/scripts.js"></script>
</body>
</html>
