<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Editar Proyecto - BOOSTLY</title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    <!-- Font Awesome icons -->
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <!-- Google fonts -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" />
    <!-- Core theme CSS (includes Bootstrap) -->
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
        .invalid-feedback { display: none; }
        .was-validated .invalid-feedback { display: block; }
    </style>
</head>
<body id="page-top">
    <!-- NAVBAR -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
        <div class="container">
            <a class="navbar-brand" href="#page-top">
                <img src="assets/img/logoo.png" alt="Logo" style="height: 30%; width: 30%;" />
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
                    aria-expanded="false" aria-label="Toggle navigation">
                Menu <i class="fas fa-bars ms-1"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                    <li class="nav-item"><a class="nav-link" href="/webboostly/">Inicio</a></li>
                    <c:choose>
                        <c:when test="${not empty sessionScope.datos}">
                            <li class="nav-item"><a class="nav-link" href="/webboostly/cuenta">Cuenta</a></li>
                            <li class="nav-item"><a class="nav-link" href="/webboostly/proyectosCategoria">Proyecto</a></li>
                            <c:if test="${sessionScope.datos.rolUsuario == 3}">
                                <li class="nav-item"><a class="nav-link" href="/webboostly/admin">Admin</a></li>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item"><a class="nav-link" href="/webboostly/login">Login</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <div class="fmProyecto">
        <div class="containerform mt-5">
            <h2 class="text-center"><i class="fas fa-folder-plus"></i> Editar Proyecto</h2>
            <form id="editarProyectoForm" action="editarProyecto" method="POST"
                  enctype="multipart/form-data" class="needs-validation" novalidate>
                <input type="hidden" name="idProyecto" value="${proyecto.idProyecto}" />

                <!-- Nombre del Proyecto -->
                <div class="mb-3">
                    <label for="nombreProyecto" class="form-label">Nombre del Proyecto</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-pen"></i></span>
                        <input type="text" id="nombreProyecto" name="nombreProyecto"
                               class="form-control"
                               value="${proyecto.nombreProyecto}"
                               required pattern=".{3,100}"
                               title="El nombre del proyecto debe tener entre 3 y 100 caracteres">
                        <div class="invalid-feedback">
                            El nombre del proyecto debe tener entre 3 y 100 caracteres.
                        </div>
                    </div>
                </div>

                <!-- Descripción -->
                <div class="mb-3">
                    <label for="descripcionProyecto" class="form-label">Descripción</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-file-alt"></i></span>
                        <textarea id="descripcionProyecto" name="descripcionProyecto"
                                  class="form-control" rows="3"
                                  required pattern=".{10,500}"
                                  title="La descripción debe tener entre 10 y 500 caracteres">${proyecto.descripcionProyecto}</textarea>
                        <div class="invalid-feedback">
                            La descripción debe tener entre 10 y 500 caracteres.
                        </div>
                    </div>
                </div>

                <!-- Imágenes opcionales -->
                <c:forEach var="i" begin="1" end="3">
                    <div class="mb-3">
                        <label for="imagen${i}Proyecto" class="form-label">Imagen ${i}</label>
                        <input type="file" id="imagen${i}Proyecto" name="imagen${i}Proyecto"
                               class="form-control"
                               accept="image/jpeg,image/png,image/gif"
                               data-max-size="5242880">
                        <div class="invalid-feedback" id="imagen${i}ProyectoFeedback">
                            Por favor, selecciona un archivo de imagen válido (JPEG, PNG, GIF, máx. 5MB).
                        </div>
                    </div>
                </c:forEach>

                <!-- Fecha de Finalización (opcional) -->
                <div class="mb-3">
                    <label for="fechaFinalizacionProyecto" class="form-label">Fecha de Finalización</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                        <input type="date" id="fechaFinalizacionProyecto" name="fechaFinalizacionProyecto"
                               class="form-control"
                               value="${proyecto.fechaFinalizacionProyecto}"
                               required>
                        <div class="invalid-feedback">
                            La fecha debe ser posterior al día actual.
                        </div>
                    </div>
                </div>

                <!-- Meta de Recaudación -->
                <div class="mb-3">
                    <label for="metaRecaudacionProyecto" class="form-label">Meta de Recaudación</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-money-bill-wave"></i></span>
                        <input type="number" id="metaRecaudacionProyecto" name="metaRecaudacionProyecto"
                               class="form-control"
                               value="${proyecto.metaRecaudacionProyecto}"
                               required min="1" step="0.01"
                               title="La meta de recaudación debe ser un número positivo">
                        <div class="invalid-feedback">
                            La meta de recaudación debe ser un número positivo.
                        </div>
                    </div>
                </div>

                <!-- Categoría -->
                <div class="mb-3">
                    <label for="categoriaProyecto" class="form-label">Categoría</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-list"></i></span>
                        <select id="categoriaProyecto" name="categoriaProyecto"
                                class="form-select" required>
                            <option value="" disabled>Selecciona una categoría</option>
                            <c:forEach var="cat" items="${categorias}">
                                <option value="${cat.idCategoria}"
                                    <c:if test="${cat.idCategoria == proyecto.idCategoria}">
                                        selected
                                    </c:if>>
                                    ${cat.nombreCategoria}
                                </option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback">
                            Por favor, selecciona una categoría.
                        </div>
                    </div>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Guardar Cambios
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer py-4">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-4 text-lg-start">© BOOSTLY 2025</div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Validaciones JS -->
    <script>
    (function(){
        'use strict';
        const form = document.getElementById('editarProyectoForm');

        function validateDate(input) {
            const selected = new Date(input.value);
            const today    = new Date();
            today.setHours(0,0,0,0);
            if (!input.value) { input.setCustomValidity(''); return true; }
            if (selected <= today) {
                input.setCustomValidity('La fecha debe ser posterior al día actual.');
                return false;
            }
            input.setCustomValidity('');
            return true;
        }

        function validateImage(input, feedbackId) {
            const file = input.files[0];
            if (!file) { input.setCustomValidity(''); return true; }
            const maxSize = +input.dataset.maxSize;
            const allowed = ['image/jpeg','image/png','image/gif'];
            if (!allowed.includes(file.type)) {
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

        const dateInput = document.getElementById('fechaFinalizacionProyecto');
        dateInput.addEventListener('change', () => validateDate(dateInput));

        const imageInputs = [1,2,3].map(i => ({
            input: document.getElementById(`imagen${i}Proyecto`),
            feedback: `imagen${i}ProyectoFeedback`
        }));
        imageInputs.forEach(({input, feedback}) =>
            input.addEventListener('change', () => validateImage(input, feedback))
        );

        form.addEventListener('submit', e => {
            e.preventDefault();
            e.stopPropagation();
            const validDate = validateDate(dateInput);
            const validImages = imageInputs.every(({input, feedback}) =>
                validateImage(input, feedback)
            );
            if (form.checkValidity() && validDate && validImages) {
                form.submit();
            } else {
                form.classList.add('was-validated');
            }
        });
    })();
    </script>
</body>
</html>
