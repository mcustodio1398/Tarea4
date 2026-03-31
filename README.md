# 📋 Tarea 4 — Sistema de Login y Gestión de Usuarios

![Java](https://img.shields.io/badge/Java-JDK%2017+-orange?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue?style=flat-square)
![NetBeans](https://img.shields.io/badge/NetBeans-IDE%2029-green?style=flat-square)
![Swing](https://img.shields.io/badge/UI-Java%20Swing-lightgrey?style=flat-square)

Sistema de gestión de usuarios con interfaz gráfica desarrollado en Java con Swing y MySQL, aplicando los principios de la Programación Orientada a Objetos y el patrón de arquitectura MVC.

---

## 📌 Descripción

Este sistema permite a los usuarios registrarse e iniciar sesión de forma segura. Una vez dentro, se muestra un listado completo de todos los usuarios registrados con opciones para agregar, actualizar y eliminar registros en tiempo real.

---

## ✅ Funcionalidades

### 🔐 Login
- Inicio de sesión con nombre de usuario y contraseña
- La contraseña se oculta automáticamente al escribir
- Validación de campos vacíos con mensaje de error descriptivo
- Acceso directo a la pantalla de registro desde el login

### 📝 Registro
- Formulario completo con los siguientes campos obligatorios:
  - Nombre
  - Apellido
  - Nombre de Usuario
  - Teléfono
  - Correo Electrónico
  - Contraseña
  - Confirmar Contraseña
- Validación de cada campo con mensaje indicando cuál está vacío
- Verificación de que la contraseña y su confirmación coincidan
- Contraseñas ocultas con efecto placeholder visible

### 👥 Pantalla Principal
- Listado de todos los usuarios registrados en una tabla
- Columnas: Nombre, Apellido, Teléfono, Correo Electrónico, Usuario
- Botón **Nuevo** para agregar un nuevo usuario
- Botón **Actualizar** para modificar los datos del usuario seleccionado
- Botón **Eliminar** para eliminar el usuario seleccionado (con confirmación)
- Botón **Cerrar Sesión** para volver al login
- La tabla se actualiza automáticamente después de cada operación

---

## 🏗️ Arquitectura MVC

El proyecto sigue el patrón de diseño **Modelo - Vista - Controlador**:

```
src/
├── Modelo/
│   ├── Conexion.java         → Singleton de conexión a MySQL
│   ├── Usuario.java          → Clase entidad con atributos y métodos
│   └── UsuarioDAO.java       → Operaciones CRUD con la base de datos
│
├── Vista/
│   ├── LoginFrame.java       → Pantalla de inicio de sesión
│   ├── RegistroFrame.java    → Pantalla de registro de usuario
│   └── PrincipalFrame.java   → Pantalla principal con tabla de usuarios
│
├── Controlador/
│   ├── LoginControlador.java       → Lógica del login
│   ├── RegistroControlador.java    → Lógica del registro
│   └── PrincipalControlador.java   → Lógica de la pantalla principal
│
└── tarea4/
    └── Tarea4.java           → Punto de entrada de la aplicación
```

---

## 🧠 Pilares de la Programación Orientada a Objetos

| Pilar | Clase | Descripción |
|-------|-------|-------------|
| **Encapsulamiento** | `Usuario.java` | Todos los atributos son `private`, accesibles solo mediante getters y setters |
| **Abstracción** | `UsuarioDAO.java` | El resto del sistema llama métodos como `registrar()` o `login()` sin conocer el SQL interno |
| **Herencia** | `LoginFrame`, `RegistroFrame`, `PrincipalFrame` | Todas las vistas heredan de `javax.swing.JFrame` |
| **Polimorfismo** | `Usuario.java` | Sobrescribe el método `toString()` de la clase `Object` |

---

## 🎨 Patrones de Diseño

### Singleton — `Conexion.java`
Garantiza que solo exista **una única instancia** de la conexión a la base de datos durante toda la ejecución del programa.

```java
public static Connection getInstancia() {
    if (instancia == null || instancia.isClosed()) {
        instancia = DriverManager.getConnection(url, usuario, password);
    }
    return instancia;
}
```

### MVC — Arquitectura completa
Separación clara de responsabilidades:
- **Modelo**: maneja los datos y la lógica de negocio
- **Vista**: solo muestra la interfaz gráfica sin lógica
- **Controlador**: conecta la vista con el modelo y maneja los eventos

---

## 🗄️ Base de Datos

### Configuración
La conexión se configura en el archivo `config.properties` ubicado en `src/`:

```properties
db.url=jdbc:mysql://localhost:3306/Tarea4
db.usuario=root
db.password=tu_contraseña
```

> ⚠️ Este archivo **no se sube a GitHub** por seguridad. Usar `config.properties.example` como referencia.

### Estructura de la tabla

```sql
CREATE DATABASE Tarea4;
USE Tarea4;

CREATE TABLE Usuario (
    id_usuario           INT AUTO_INCREMENT PRIMARY KEY,
    nombre               VARCHAR(100) NOT NULL,
    apellido             VARCHAR(100) NOT NULL,
    numero_telefonico    VARCHAR(20),
    correo_electronico   VARCHAR(50),
    nombre_usuario       VARCHAR(50) NOT NULL UNIQUE,
    contrasena           VARCHAR(100) NOT NULL
);
```

---

## ⚙️ Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | JDK 17+ | Lenguaje principal |
| Java Swing | - | Interfaz gráfica |
| MySQL | 8.0+ | Base de datos |
| NetBeans IDE | 29 | Entorno de desarrollo |
| mysql-connector-j | 9.6.0 | Conector Java-MySQL |

---

## 🚀 Cómo Ejecutar el Proyecto

### Requisitos previos
- JDK 17 o superior instalado
- MySQL 8.0 o superior instalado
- NetBeans IDE instalado
- mysql-connector-j agregado a las librerías del proyecto

### Pasos

1. **Clona el repositorio:**
```bash
git clone https://github.com/tu_usuario/Tarea4.git
```

2. **Configura la base de datos** en MySQL Workbench:
```sql
CREATE DATABASE Tarea4;
USE Tarea4;

CREATE TABLE Usuario (
    id_usuario           INT AUTO_INCREMENT PRIMARY KEY,
    nombre               VARCHAR(100) NOT NULL,
    apellido             VARCHAR(100) NOT NULL,
    numero_telefonico    VARCHAR(20),
    correo_electronico   VARCHAR(50),
    nombre_usuario       VARCHAR(50) NOT NULL UNIQUE,
    contrasena           VARCHAR(100) NOT NULL
);
```

3. **Crea el archivo `config.properties`** en la carpeta `src/`:
```properties
db.url=jdbc:mysql://localhost:3306/Tarea4
db.usuario=root
db.password=tu_contraseña
```

4. **Abre el proyecto** en NetBeans y agrega `mysql-connector-j` a las librerías.

5. **Ejecuta el proyecto** con `F6` o el botón ▶.

---

## 📁 Archivos importantes

| Archivo | Descripción |
|---------|-------------|
| `config.properties` | Credenciales de la BD (no subir a GitHub) |
| `config.properties.example` | Plantilla de referencia para la configuración |
| `README.md` | Documentación del proyecto |

---

## 👤 Autor

**Michael Custodio**  
Curso: Programación I  
Fecha: 2026

---

## 📄 Licencia

Este proyecto es de uso académico.
