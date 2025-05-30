# 📝 NotasAPI - Aplicación de Gestión de Notas

Aplicación desarrollada para la UT6 de **POO Avanzada**, centrada en el uso de JPA, servicios REST, validaciones y arquitectura en capas. ✅

## 🎯 Objetivos

- 🔄 Modelar entidades con JPA y relaciones bidireccionales
- 🧬 Diseñar servicios usando interfaces, clases abstractas y herencia
- 🌐 Implementar controladores REST versionados
- ✅ Añadir validaciones y manejo de errores
- 🔄 Usar transacciones, `cascade`, y `orphanRemoval`
- 🧪 Probar la API con herramientas como Postman o cURL

---

## 🛠️ Requisitos Técnicos

### 🧍 Entidad `Usuario`
- `id` (Long, autogenerado)
- `nombre` (String)
- `email` (String, único)
- `passwordHash` (String)

📌 Relación:
- Un Usuario puede tener muchas Notas.
- Al guardar/borrar un Usuario, sus Notas se deben guardar/borrar automáticamente: `cascade = ALL`
- Si una Nota queda huérfana, se elimina: `orphanRemoval = true`

### 🗒️ Entidad `Nota`
- `id` (Long, autogenerado)
- `titulo` (String)
- `contenido` (texto largo)
- `fechaCreacion` (fecha y hora)
- Cada Nota pertenece a un único Usuario.

---

## 🧩 Servicios

Se sigue el patrón `CrudService / AbstractCrudService`.

### 📄 Interfaz Genérica `CrudService<T, ID>`

---

## ⚙️ Clase Abstracta `AbstractCrudService`

Implementa la lógica común para servicios CRUD reutilizables usando `JpaRepository<T, ID>`.  
Permite evitar duplicación de código en servicios concretos.

---

## 💼 Servicios Concretos

- `UsuarioService` y `NotaService` extienden `CrudService<T, ID>`
- Implementados como:
  - `UsuarioServiceImpl`
  - `NotaServiceImpl`

Ambos **extienden `AbstractCrudService`**, y pueden contener lógica adicional específica según necesidad.

---

## 🧬 Repositorios

- `UsuarioRepository` → `extends JpaRepository<Usuario, Long>`
- `NotaRepository` → `extends JpaRepository<Nota, Long>`

## 🌐 API REST

### 📁 Rutas disponibles

- `/api/v1/usuarios` → CRUD de usuarios
- `/api/v2/usuarios` → Registro con email y password
- `/api/v1/notas` → CRUD de notas

---

### 🔎 Filtros y Parámetros

Se permite el filtrado y ordenamiento de notas usando parámetros en la URL:

```http
GET /api/v1/notas?usuarioId=1&order=desc
```
## 🛡️ Validaciones

Anotaciones utilizadas:

- `@Valid`
- `@Email`
- `@Positive`
- `@NotBlank`
- `@Size`

✅ Aplicadas en peticiones `POST` y `PUT`  
🚫 Rechazo automático de peticiones con datos inválidos (`400 Bad Request`)

---

## ❌ Manejo de Errores

- Uso de `@ControllerAdvice` + `@ExceptionHandler` para interceptar y personalizar respuestas de error
- Lanzamiento de errores controlados con `ResponseStatusException`, por ejemplo:
  - `404 Not Found`
  - `400 Bad Request`

📦 Las respuestas de error se devuelven en formato **JSON** con mensajes claros y útiles para el cliente.

---

## Autores
Este proyecto ha sido desarrollado por Sara Alonso Perdomo y Juan Antonio "Toño" Tejera González.  

Github de Sara: [![Web](https://img.shields.io/badge/GitHub-juniuun-14a1f0?style=for-the-badge&logo=github&logoColor=white&labelColor=101010)](https://github.com/juniuun/)

Github de Toño: [![Web](https://img.shields.io/badge/GitHub-tonodevep-14a1f0?style=for-the-badge&logo=github&logoColor=white&labelColor=101010)](https://github.com/tonodevep/)

