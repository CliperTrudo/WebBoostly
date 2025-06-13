<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    .invalid-feedback { display: none; }
    .was-validated .invalid-feedback { display: block; }
  </style>
</head>
<body id="page-top">
  <!-- Navigation omitted for brevity… -->

  <br/><br/><br/><br/>
  <div class="fmProyecto">
    <div class="containerform mt-5">
      <h2 class="text-center">
        <i class="fas fa-folder-plus"></i>
        <c:choose>
          <c:when test="${not empty proyecto}">Editar Proyecto</c:when>
          <c:otherwise>Crear Nuevo Proyecto</c:otherwise>
        </c:choose>
      </h2>

      <form action="<c:choose>
                       <c:when test='${not empty proyecto}'>editarProyecto</c:when>
                       <c:otherwise>proyecto</c:otherwise>
                     </c:choose>"
            method="POST"
            enctype="multipart/form-data"
            class="needs-validation"
            id="proyectoForm"
            novalidate>

        <!-- En edición incluimos el ID oculto -->
        <c:if test="${not empty proyecto}">
          <input type="hidden" name="idProyecto"
                 value="${proyecto.idProyecto}" />
        </c:if>

        <!-- Nombre -->
        <div class="mb-3">
          <label for="nombreProyecto" class="form-label">
            Nombre del Proyecto
          </label>
          <div class="input-group">
            <span class="input-group-text">
              <i class="fas fa-pen"></i>
            </span>
            <input type="text"
                   class="form-control"
                   id="nombreProyecto"
                   name="nombreProyecto"
                   required
                   minlength="3"
                   maxlength="100"
                   pattern="^[\p{L}0-9 .,'-]+$"
                   title="El nombre debe tener entre 3 y 100 caracteres y no contener símbolos inválidos"
                   value="${proyecto.nombreProyecto}">
            <div class="invalid-feedback">
              El nombre del proyecto debe tener entre 3 y 100 caracteres.
            </div>
          </div>
        </div>

        <!-- Descripción -->
        <div class="mb-3">
          <label for="descripcionProyecto" class="form-label">
            Descripción
          </label>
          <div class="input-group">
            <span class="input-group-text">
              <i class="fas fa-file-alt"></i>
            </span>
            <textarea class="form-control"
                      id="descripcionProyecto"
                      name="descripcionProyecto"
                      rows="3"
                      required
                      minlength="10"
                      maxlength="500"
                      title="La descripción debe tener entre 10 y 500 caracteres">${proyecto.descripcionProyecto}</textarea>
            <div class="invalid-feedback">
              La descripción debe tener entre 10 y 500 caracteres.
            </div>
          </div>
        </div>

        <!-- Imágenes -->
        <c:forEach var="i" begin="1" end="3">
          <div class="mb-3">
            <label for="imagen${i}Proyecto" class="form-label">
              Imagen ${i}
            </label>
            <input type="file"
                   class="form-control"
                   id="imagen${i}Proyecto"
                   name="imagen${i}Proyecto"
                   ${empty proyecto ? "required" : ""}
                   accept="image/jpeg,image/png,image/gif"
                   data-max-size="5242880"
                   title="Archivos JPEG, PNG o GIF de máximo 5MB">
            <div class="invalid-feedback" id="imagen${i}ProyectoFeedback">
              Por favor, selecciona un archivo de imagen válido
              (JPEG, PNG, GIF, máx. 5MB).
            </div>
          </div>
        </c:forEach>

        <!-- Fecha de finalización -->
        <div class="mb-3">
          <label for="fechaFinalizacionProyecto" class="form-label">
            Fecha de Finalización
          </label>
          <div class="input-group">
            <span class="input-group-text">
              <i class="fas fa-calendar"></i>
            </span>
            <input type="date"
                   class="form-control"
                   id="fechaFinalizacionProyecto"
                   name="fechaFinalizacionProyecto"
                   required
                   value="${proyecto.fechaFinalizacionProyecto}"
                   min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
            <div class="invalid-feedback">
              Por favor, selecciona una fecha de finalización futura.
            </div>
          </div>
        </div>

        <!-- Meta de recaudación -->
        <div class="mb-3">
          <label for="metaRecaudacionProyecto" class="form-label">
            Meta de Recaudación
          </label>
          <div class="input-group">
            <span class="input-group-text">
              <i class="fas fa-money-bill-wave"></i>
            </span>
            <input type="number"
                   class="form-control"
                   id="metaRecaudacionProyecto"
                   name="metaRecaudacionProyecto"
                   required
                   min="0.01"
                   step="0.01"
                   title="La meta debe ser un número positivo mayor que 0.00"
                   value="${proyecto.metaRecaudacionProyecto}">
            <div class="invalid-feedback">
              La meta de recaudación debe ser un número positivo mayor que 0.
            </div>
          </div>
        </div>

        <!-- Categoría dinámica -->
        <div class="mb-3">
          <label for="categoriaProyecto" class="form-label">
            Categoría
          </label>
          <div class="input-group">
            <span class="input-group-text">
              <i class="fas fa-list"></i>
            </span>
            <select class="form-select"
                    id="categoriaProyecto"
                    name="categoriaProyecto"
                    required>
              <option value="" disabled>
                Selecciona una categoría
              </option>
              <c:forEach var="cat" items="${categorias}">
                <option value="${cat.idCategoria}"
                  <c:if test="${proyecto.idCategoria == cat.idCategoria}">
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

        <button type="submit" class="btn btn-primary">
          <i class="fas fa-save"></i>
          <c:choose>
            <c:when test="${not empty proyecto}">Actualizar Proyecto</c:when>
            <c:otherwise>Crear Proyecto</c:otherwise>
          </c:choose>
        </button>
      </form>
    </div>
  </div>

  <!-- Footer -->
  <!-- Validación JS personalizada -->
  <script>
    (function() {
      'use strict';
      const form = document.getElementById('proyectoForm');
      form.addEventListener('submit', function(event) {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        // Validar archivos
        const files = form.querySelectorAll('input[type=file]');
        files.forEach(input => {
          const file = input.files[0];
          const feedback = document.getElementById(input.id + 'Feedback');
          if (file) {
            const maxSize = parseInt(input.dataset.maxSize, 10);
            const validTypes = ['image/jpeg', 'image/png', 'image/gif'];
            if (!validTypes.includes(file.type) || file.size > maxSize) {
              input.classList.add('is-invalid');
              feedback.style.display = 'block';
              event.preventDefault();
              event.stopPropagation();
            } else {
              input.classList.remove('is-invalid');
            }
          }
        });
        form.classList.add('was-validated');
      }, false);
    })();
  </script>
</body>
</html>
