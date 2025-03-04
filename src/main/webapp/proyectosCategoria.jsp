<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Proyectos por Categoría</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div class="container mt-5">
    <h2 class="text-center">Proyectos por Categoría</h2>

    <!-- Selector de Categoría -->
    <div class="mb-3">
        <label for="categoriaSelect" class="form-label">Selecciona una categoría:</label>
        <select id="categoriaSelect" class="form-select">
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
