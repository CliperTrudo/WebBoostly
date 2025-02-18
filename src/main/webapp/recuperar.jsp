<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="author" content="Sergio Alfonseca Vallet">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <meta name="description" content="This is a login page template based on Bootstrap 5">
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <meta name="google-signin-client_id" content="797777584256-rv70sv8lpr6pvl9bbki0b029p0fse5se.apps.googleusercontent.com">
  <title>Bootstrap 5 Login Page</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
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
    <section class="d-flex align-items-start justify-content-center" style="min-height: calc(100vh - 60px); padding-top: 20vh;">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
                    <div class="card shadow-lg">
                        <div class="card-body px-5 mt-0 mb-3">
                            <div class="text-center mb-0">
                                <img src="./assets/img/candado.png" alt="candado" width="80%">
                            </div>
                            <h2 class="h5 text-center mt-0">¿Tienes problemas para entrar?</h2>
                            <p class="text-center mb-2">
                                Introduce tu correo electrónico y te enviaremos un enlace para restablecer tu contraseña.
                            </p>
                            <form action="recuperar-contrasena" method="post">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Mail:</label>
                                    <input id="email" type="email" class="form-control" name="email" required>
                                </div>
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary">Enviar</button>
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
