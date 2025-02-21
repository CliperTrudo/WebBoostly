<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            background-image: url("https://images.unsplash.com/photo-1588345921523-c2dcdb7f1dcd?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background-attachment: fixed;
        }
        .container {
            background: rgba(255, 255, 255, 0.7);
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            width: 50%;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center"><i class="fas fa-folder-plus"></i> Crear Nuevo Proyecto</h2>
        <form action="procesarProyecto.jsp" method="POST" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="nombreProyecto" class="form-label"><i class="fas fa-file-signature"></i> Nombre del Proyecto</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-pen"></i></span>
                    <input type="text" class="form-control" id="nombreProyecto" name="nombreProyecto" required>
                </div>
            </div>
            <div class="mb-3">
                <label for="descripcionProyecto" class="form-label"><i class="fas fa-align-left"></i> Descripción</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-file-alt"></i></span>
                    <textarea class="form-control" id="descripcionProyecto" name="descripcionProyecto" rows="3" required></textarea>
                </div>
            </div>
            <div class="mb-3">
                <label for="imagen1Proyecto" class="form-label"><i class="fas fa-image"></i> Imagen 1</label>
                <input type="file" class="form-control" id="imagen1Proyecto" name="imagen1Proyecto">
            </div>
            <div class="mb-3">
                <label for="imagen2Proyecto" class="form-label"><i class="fas fa-image"></i> Imagen 2</label>
                <input type="file" class="form-control" id="imagen2Proyecto" name="imagen2Proyecto">
            </div>
            <div class="mb-3">
                <label for="imagen3Proyecto" class="form-label"><i class="fas fa-image"></i> Imagen 3</label>
                <input type="file" class="form-control" id="imagen3Proyecto" name="imagen3Proyecto">
            </div>
            <div class="mb-3">
                <label for="fechaFinalizacionProyecto" class="form-label"><i class="fas fa-calendar-alt"></i> Fecha de Finalización</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-calendar"></i></span>
                    <input type="date" class="form-control" id="fechaFinalizacionProyecto" name="fechaFinalizacionProyecto" required>
                </div>
            </div>
            <div class="mb-3">
                <label for="metaRecaudacionProyecto" class="form-label"><i class="fas fa-dollar-sign"></i> Meta de Recaudación</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-money-bill-wave"></i></span>
                    <input type="number" class="form-control" id="metaRecaudacionProyecto" name="metaRecaudacionProyecto" required>
                </div>
            </div>
            <div class="mb-3">
                <label for="categoriaProyecto" class="form-label"><i class="fas fa-tags"></i> Categoría</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-list"></i></span>
                    <select class="form-select" id="categoriaProyecto" name="categoriaProyecto" required>
                        <option value="Educación">Educación</option>
                        <option value="Salud">Salud</option>
                        <option value="Medio Ambiente">Medio Ambiente</option>
                        <option value="Tecnología">Tecnología</option>
                        <option value="Arte">Arte</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Crear Proyecto</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
