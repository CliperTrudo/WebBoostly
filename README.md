# WebBoostly

WebBoostly es la interfaz web de la aplicación Boostly, diseñada para gestionar proyectos y usuarios a través de una interfaz sencilla y moderna. Se utiliza en combinación con la API de **ApiBoostly** para realizar operaciones sobre la base de datos, como la creación, edición y eliminación de proyectos, así como la gestión de usuarios y sesiones.

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **JSP (JavaServer Pages)**
- **Maven**
- **Servlet API 5.x**
- **Tomcat 9.0+**
- **ApiBoostly (como backend)**

---

## 📋 Requisitos del Sistema

### Herramientas Necesarias:
- **Java 21 (JDK 21)**: Necesario para compilar y ejecutar la aplicación.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **IDE (Opcional)**: IntelliJ IDEA, Eclipse, Visual Studio Code, etc.

### Dependencias Principales:
- **Servlet API 5.x**: Para el manejo de JSPs y Servlets.
- **ApiBoostly**: API externa para manejar la base de datos y las operaciones CRUD.

---

## 📌 Instalación

### 🔹 Clonación del Repositorio
```bash
git clone https://github.com/usuario/webboostly.git
cd webboostly
```

### 🔹 Configuración del Proyecto
#### 📡 Configuración de la API
La aplicación se comunica con **ApiBoostly** para acceder a los datos. Verifica que la API esté corriendo en:

```plaintext
http://localhost:8081/apiBoostly/api
```

Si es necesario, puedes modificar la URL base dentro de los controladores de la aplicación en la clase `ApiService`.

#### 🌐 Configuración del Servidor de Aplicaciones
WebBoostly está diseñado para ejecutarse en **Tomcat 9.0+**. Asegúrate de que sea compatible con **Servlets 5.0**.

1. Importa el proyecto en tu IDE.
2. Agrega las dependencias necesarias con Maven.
3. Compila y despliega la aplicación en el servidor.

Para compilar con Maven, usa:
```bash
mvn clean install
```

### 🔹 Configuración de la Base de Datos
WebBoostly no se conecta directamente a la base de datos, sino a través de la API **ApiBoostly**. Asegúrate de que **ApiBoostly** esté configurada y en ejecución.

---

## 📂 Estructura del Proyecto

```
webboostly/
├── src/main/webapp/             # Archivos JSP de la interfaz de usuario
├── src/main/java/controladores/ # Controladores que manejan las solicitudes HTTP
├── src/main/java/dtos/          # Clases DTO para la comunicación con la API
├── src/main/java/servicios/     # Servicios para manejar la conecxión a la api, gmail, token, etc.
└── pom.xml                      # Archivo de configuración de Maven
```

---

## 🎯 Uso de la Aplicación

### 🔹 Registro de Usuario
Dirígete a [`/registro`](http://localhost:8080/registro) y completa el formulario con:
- **Nombre**
- **Apellidos**
- **Correo electrónico**
- **Fecha de nacimiento**
- **Nickname**
- **Contraseña**

Una vez registrado, recibirás un **código de verificación** en tu correo.

### 🔹 Inicio de Sesión
Accede en [`/login`](http://localhost:8080/login) con tus credenciales o utiliza **Google Login**.

### 🔹 Creación y Gestión de Proyectos

Los usuarios autenticados pueden crear proyectos en [`/proyecto`](http://localhost:8080/proyecto). Se deben ingresar:
- **Nombre del proyecto**
- **Descripción**
- **Fecha de finalización**
- **Meta de recaudación**
- **Categoría**

Los proyectos pueden ser **editados** y **eliminados** desde [`/editarProyecto`](http://localhost:8080/editarProyecto).

### 🔹 Recuperación de Contraseña
Si olvidaste tu contraseña, visita [`/recuperar-contrasena`](http://localhost:8080/recuperar-contrasena). Recibirás un **enlace de recuperación** en tu correo.

---

## 🛠️ Detalles Técnicos

### 📁 Estructura del Código
El proyecto está basado en **Java Servlets** y **JSP** para manejar la interfaz y las solicitudes HTTP.

### 📦 Dependencias Clave (Gestionadas por Maven)
- **Servlet API 5.x**
- **Jackson**: Conversión entre JSON y objetos Java.
- **BCrypt**: Encriptación de contraseñas.

### 🗄️ Base de Datos (Manejada por ApiBoostly)
- **Usuarios**: Información personal, contraseñas (encriptadas) y roles.
- **Proyectos**: Nombre, descripción, imágenes, meta de recaudación, etc.
- **Categorías**: Clasificación de proyectos.

---

