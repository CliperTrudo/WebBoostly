<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Verificación de Código</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <style>
        .card {
            max-width: 400px;
            margin: auto;
            margin-top: 10vh;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <main class="flex-grow-1">
    <div class="container mt-3 d-flex justify-content-end">
      <a href="/webboostly" class="btn btn-link">Volver</a>
    </div>
        <section class="d-flex align-items-center justify-content-center" style="min-height: calc(100vh - 60px);">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 col-lg-5">
                        <div class="card shadow-lg">
                            <div class="card-body p-4">
                                <div class="text-center mb-3">
                                    <img src="./assets/img/candado.png" alt="candado" width="50%">
                                </div>
                                <h2 class="h5 text-center">Verificación de Código</h2>
                                <p class="text-center">Introduce el código de verificación que te hemos enviado por correo.</p>
                                <form action="verificarCodigo" method="post">
                                    <div class="mb-3">
                                        <label for="codigo_verificacion" class="form-label">Código de verificación:</label>
                                        <input type="text" id="codigo_verificacion" name="codigo_verificacion" class="form-control" required>
                                    </div>
                                    <div class="text-center">
                                        <button type="submit" class="btn btn-primary">Verificar</button>
                                    </div>
                                </form>
                                <c:if test="${not empty error}">
                                    <p class="text-center text-danger mt-2">${error}</p>
                                </c:if>
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
                <span class="mb-3 mb-md-0 text-body-secondary">© 2025 BOOSTLY</span>
            </div>
        </div>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
