# 🚗 AutoPulse

AutoPulse es una aplicación web desarrollada en **Spring Boot** para la gestión de vehículos, incluyendo mantenimientos, repostajes, neumáticos e ITV. Su objetivo es permitir a los usuarios llevar un control centralizado de sus coches y todas las operaciones asociadas.

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3+
  - Spring Web
  - Spring Security
  - Spring Data JPA
- Thymeleaf
- Bootstrap 4
- PostegreSQL
- JPA + Hibernate

## 🔐 Autenticación

AutoPulse usa un sistema de login personalizado mediante una entidad `UsuarioEntity` y un `CustomUserDetailsService`. Se ha desactivado temporalmente el cifrado de contraseñas para facilitar el desarrollo local.

## ✨ Funcionalidades principales

- Registro e inicio de sesión de usuario
- Gestión de coches personales
- Registro de:
  - Mantenimientos
  - Repostajes
  - Neumáticos
  - ITV
- Sistema de alertas visuales
- Panel responsive para usuario (en desarrollo)
- Interfaz intuitiva basada en Bootstrap 4

## 🧪 Cómo ejecutar el proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tuusuario/autopulse.git
   cd autopulse
   ```

2. Ejecuta la aplicación desde tu IDE o con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Accede en tu navegador a:
   ```
   http://localhost:8080
   ```

4. Base de datos PostgreSQL:

## 🧪 Credenciales de prueba

> ⚠️ Solo para entorno de desarrollo:

- Usuario: `admin`
- Contraseña: `1234`

## 📁 Estructura del proyecto

```
src/
 └─ main/
     ├─ java/
     │   └─ com.mantenimiento.springItv/
     │       ├─ controller/
     │       ├─ services/
     │       ├─ repository/
     │       ├─ security/
     │       └─ models/
     │       └─ config/
     │       └─ entities/
     │       └─ transformadores/
     └─ resources/
         ├─ templates/
         ├─ static/
         └─ application.properties
```

## ✅ Pendiente de implementar

- Cifrado de contraseñas
- Adaptación completa a móviles
- Historial gráfico de gastos por coche
- Notificaciones por mantenimiento próximo
- Gestión de documentos (PDFs, fotos)

## 🤝 Contribuciones

¿Tienes ideas o mejoras? ¡Estás invitado a contribuir! Abre un issue o haz un pull request.

## 📄 Licencia

Este proyecto está bajo licencia MIT.

---

### ✨ Autor

Desarrollado con ❤️ por Rubén Rivas Hernández
