<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Restablecer Contraseña</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
        crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
  <main class="flex-grow-1">
    <section class="d-flex align-items-start justify-content-center" style="min-height: calc(100vh - 60px); padding-top: 20vh;">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
            <div class="card shadow-lg">
              <div class="card-body px-5 mt-0 mb-3">
                <div class="text-center mb-0">
                  <img src="./assets/img/candado.png" alt="candado" width="80%">
                </div>
                <h2 class="h5 text-center mt-0 mb-3">Restablecer Contraseña</h2>
                <form action="reset-password" method="post">
                  <!-- Campo oculto para el token -->
                  <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
                  <!-- Nuevo campo de contraseña -->
                  <div class="mb-3">
                    <label for="nuevaContrasena" class="form-label">Nueva Contraseña:</label>
                    <input id="nuevaContrasena" type="password" class="form-control" name="nuevaContrasena" required>
                  </div>
                  <!-- Campo para confirmar la nueva contraseña -->
                  <div class="mb-3">
                    <label for="confirmarContrasena" class="form-label">Confirmar Contraseña:</label>
                    <input id="confirmarContrasena" type="password" class="form-control" name="confirmarContrasena" required>
                  </div>
                  <div class="text-center">
                    <button type="submit" class="btn btn-primary">Restablecer</button>
                  </div>
                </form>
                <% if (request.getAttribute("mensaje") != null) { %>
                  <p class="text-center" style="color: green;"><%= request.getAttribute("mensaje") %></p>
                <% } %>
                <% if (request.getAttribute("error") != null) { %>
                  <p class="text-center" style="color: red;"><%= request.getAttribute("error") %></p>
                <% } %>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
  
  <footer class="d-flex flex-wrap justify-content-between align-items-center py-3 border-top bg-light">
    <div class="container d-flex justify-content-between">
      <div class="col-md-4 d-flex align-items-center">
        <a href="/" class="mb-3 me-2 mb-md-0 text-body-secondary text-decoration-none lh-1">
          <svg class="bi" width="30" height="24">
            <use xlink:href="#bootstrap"></use>
          </svg>
        </a>
        <span class="mb-3 mb-md-0 text-body-secondary">© 2024 Company, Inc</span>
      </div>
    </div>
  </footer>
  
  <script src="js/script_login.js"></script>
</body>
</html>
