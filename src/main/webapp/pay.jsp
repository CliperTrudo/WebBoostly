<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Pagar con PayPal</title>
  </head>
  <body>
    <h1>Realizar pago</h1>
    <form action="${pageContext.request.contextPath}/create-order" method="post">
      <label for="amount">Importe (EUR):</label>
      <input type="text" id="amount" name="amount" value="10.00"/>
      <button type="submit">Donar con PayPal</button>
    </form>
  </body>
</html>

