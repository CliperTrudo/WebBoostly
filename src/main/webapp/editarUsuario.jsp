<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    // El servlet ya dejó en requestAttribute "usuario" (UsuarioDto)
    dtos.UsuarioDto usuario = (dtos.UsuarioDto) request.getAttribute("usuario");
    @SuppressWarnings("unchecked")
    java.util.List<dtos.RolDto> roles = 
        (java.util.List<dtos.RolDto>) request.getAttribute("roles");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Editar Usuario</title>

    <!-- Font Awesome -->
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet"/>

    <!-- Tu CSS principal -->
    <link href="css/styles.css" rel="stylesheet"/>

    <style>
        /* Ajustes puntuales para el formulario: */
        body {
            padding-top: 80px; /* espacio para navbar */
            background-color: #f5f5f5;
        }
        .fmUsuario {
            max-width: 700px;
            margin: 40px auto;
            background-color: #fff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body id="page-top">
    <!-- NAVBAR: Reutiliza el que tengas en tu layout -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="assets/img/logoo.png" alt="Logo" style="height:30px; width:auto;">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
                    aria-expanded="false" aria-label="Toggle navigation">
                Menu <i class="fas fa-bars ms-1"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Inicio</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/cuenta">Cuenta</a></li>
                    <c:if test="${sessionScope.datos.rolUsuario == 3}">
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin">Admin</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>

    <div class="fmUsuario">
        <h2 class="text-center mb-4">
            <i class="fas fa-user-edit"></i> Editar Usuario
        </h2>

        <!-- Si llegase error=1 en la URL, muestro aviso -->
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">No se pudo actualizar el usuario. Intenta de nuevo.</div>
        </c:if>

        <!-- Formulario: action="editarUsuario" method="POST" multipart si subes foto -->
        <form action="${pageContext.request.contextPath}/editarUsuario" 
              method="POST" 
           
              class="needs-validation">
            
            <!-- Hidden con el ID del usuario -->
            <input type="hidden" name="idUsuario" value="${usuario.id}" />

            <!-- Nombre -->
            <div class="mb-3">
                <label for="nombreUsuario" class="form-label">Nombre</label>
                <input type="text" id="nombreUsuario" name="nombreUsuario" 
                       class="form-control" 
                       value="${usuario.nombreUsuario}" 
                       required>
            </div>

            <!-- Apellidos -->
            <div class="mb-3">
                <label for="apellidosUsuario" class="form-label">Apellidos</label>
                <input type="text" id="apellidosUsuario" name="apellidosUsuario"
                       class="form-control" 
                       value="${usuario.apellidosUsuario}" 
                       required>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label for="mailUsuario" class="form-label">Email</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                    <input type="email" id="mailUsuario" name="mailUsuario" 
                           class="form-control" 
                           value="${usuario.mailUsuario}" 
                           required>
                </div>
            </div>

            <!-- Fecha de nacimiento -->
            <div class="mb-3">
                <label for="fechaNacimientoUsuario" class="form-label">Fecha de Nacimiento</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                    <input type="date" id="fechaNacimientoUsuario" name="fechaNacimientoUsuario"
                           class="form-control" 
                           value="${usuario.fechaNacimientoUsuario}" 
                           required>
                </div>
            </div>

            <!-- Nickname -->
            <div class="mb-3">
                <label for="nicknameUsuario" class="form-label">Nickname</label>
                <input type="text" id="nicknameUsuario" name="nicknameUsuario" 
                       class="form-control" 
                       value="${usuario.nicknameUsuario}" 
                       required>
            </div>

            <!-- Descripción -->
            <div class="mb-3">
                <label for="descripcionUsuario" class="form-label">Descripción</label>
                <textarea id="descripcionUsuario" name="descripcionUsuario" 
                          class="form-control" rows="3"
                          placeholder="Breve descripción (opcional)">${usuario.descripcionUsuario}</textarea>
            </div>

            <!-- DNI -->
            <div class="mb-3">
                <label for="dniUsuario" class="form-label">DNI</label>
                <input type="text" id="dniUsuario" name="dniUsuario" 
                       class="form-control" 
                       value="${usuario.dniUsuario}">
            </div>

            <!-- Teléfono -->
            <div class="mb-3">
                <label for="telefonoUsuario" class="form-label">Teléfono</label>
                <input type="tel" id="telefonoUsuario" name="telefonoUsuario" 
                       class="form-control" 
                       value="${usuario.telefonoUsuario}">
            </div>

            <!-- Rol (select) -->
            <div class="mb-3">
                <label for="rol" class="form-label">Rol</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-user-shield"></i></span>
                    <select id="rol" name="rol" class="form-select" required>
                        <option value="">-- Selecciona rol --</option>
                        <c:forEach var="r" items="${roles}">
                            <option value="${r.idRol}" 
                                ${r.idRol == usuario.rol ? "selected" : ""}>
                                ${r.nombreRol}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- FOTO (opcional): si quieres permitir cambiar avatar -->
            <!-- 
            <div class="mb-3">
                <label for="imgUsuario" class="form-label">Avatar (si quieres cambiarlo)</label>
                <input type="file" id="imgUsuario" name="imgUsuario" class="form-control" accept="image/*">
            </div>
            -->

            <div class="d-grid">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Guardar cambios
                </button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS (asegúrate de que esté cargado al final) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
