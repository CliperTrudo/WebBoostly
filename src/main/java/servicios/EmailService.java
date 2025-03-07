package servicios;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

/**
 * Clase encargada de enviar correos electrónicos a través de un servidor SMTP (Gmail en este caso).
 * La clase proporciona métodos para enviar correos de recuperación de contraseña y correos generales.
 */
public class EmailService {

    // Configuración del servidor SMTP (Gmail)
    private String host = "smtp.gmail.com";  // Servidor SMTP de Gmail (puede cambiar dependiendo del proveedor)
    private String port = "587";             // Puerto para el envío de correos con STARTTLS
    private String username = "boostly.crowdfunder@gmail.com";  // Dirección de correo electrónico (remitente)
    private String password = "poiq kgdq sglu zyri";  // Contraseña de la aplicación generada en Gmail

    /**
     * Envía un correo de recuperación de contraseña al usuario.
     * @param destinatario El correo electrónico del destinatario.
     * @param token El token de recuperación de contraseña.
     */
    public void enviarCorreoRecuperacion(String destinatario, String token) {
        String asunto = "Recuperación de Contraseña";
        String resetUrl = "http://localhost:8080/webboostly/reset-password?token=" + token;  // URL para resetear la contraseña
        String mensajeTexto = "Hola,\n\nPara restablecer tu contraseña, haz clic en el siguiente enlace: \n" + resetUrl + 
                              "\n\nEste enlace expira en 5 minutos.\n\nSi no solicitaste esta recuperación, ignora este mensaje.";

        // Llamada al método enviarCorreo para enviar el correo con los detalles especificados
        enviarCorreo(destinatario, asunto, mensajeTexto);
    }
    
    /**
     * Envía un correo electrónico genérico.
     * @param destinatario El correo electrónico del destinatario.
     * @param asunto El asunto del correo.
     * @param mensajeTexto El cuerpo del mensaje.
     */
    public void enviarCorreo(String destinatario, String asunto, String mensajeTexto) {
        // Configuración de las propiedades del servidor SMTP
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.port", port);
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        // Autenticación con el servidor SMTP usando el correo y la contraseña de la aplicación
        Session session = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);  // Autenticación con las credenciales
            }
        });

        try {
            // Crear el mensaje de correo
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(username));  // Establecer el remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));  // Establecer el destinatario
            mensaje.setSubject(asunto);  // Establecer el asunto
            mensaje.setText(mensajeTexto);  // Establecer el cuerpo del mensaje

            // Enviar el correo
            Transport.send(mensaje);
            System.out.println("Correo enviado correctamente a: " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();  // Imprimir detalles del error en caso de que ocurra un problema
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
