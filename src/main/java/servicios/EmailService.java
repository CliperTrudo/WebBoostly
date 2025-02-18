package servicios;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    private String host = "smtp.gmail.com";  // Servidor SMTP de Gmail (puedes cambiar esto dependiendo de tu proveedor)
    private String port = "587";             // Puerto para envío de correos con STARTTLS
    private String username = "boostly.crowdfunder@gmail.com";  // Tu dirección de correo electrónico
    private String password = "poiq kgdq sglu zyri";  // Tu contraseña de aplicación generada en Gmail

    public void enviarCorreoRecuperacion(String destinatario, String token) {
        String asunto = "Recuperación de Contraseña";
        String resetUrl = "http://localhost:8080/webboostly/reset-password?token=" + token;
        String mensajeTexto = "Hola,\n\nPara restablecer tu contraseña, haz clic en el siguiente enlace: \n" + resetUrl + "\n\nEste enlace expira en 5 minutos.\n\nSi no solicitaste esta recuperación, ignora este mensaje.";

        enviarCorreo(destinatario, asunto, mensajeTexto);
    }
    
    public void enviarCorreo(String destinatario, String asunto, String mensajeTexto) {
        // Configuración de las propiedades del servidor SMTP
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.port", port);
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        // Autenticación para enviar el correo
        Session session = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);  // Autenticación con la contraseña de la aplicación
            }
        });

        try {
            // Crear un mensaje de correo
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(username));  // Remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));  // Destinatario
            mensaje.setSubject(asunto);  // Asunto
            mensaje.setText(mensajeTexto);  // Cuerpo del mensaje

            // Enviar el correo
            Transport.send(mensaje);
            System.out.println("Correo enviado correctamente a: " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();  // Imprimir el error detallado
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
