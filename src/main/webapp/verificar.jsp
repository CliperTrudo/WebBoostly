<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verificación de Código</title>
</head>
<body>
    <h2>Introduce el código de verificación que te hemos enviado por correo</h2>

    <form action="verificarCodigo" method="post">
        <label for="codigo_verificacion">Código de verificación:</label>
        <input type="text" id="codigo_verificacion" name="codigo_verificacion" required>
        <button type="submit">Verificar</button>
    </form>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
</body>
</html>
