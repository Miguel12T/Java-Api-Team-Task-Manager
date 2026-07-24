# Team Task Manager

API REST para gestión de tareas colaborativas: proyectos, tareas y asignación de
tareas a miembros de un equipo, con autenticación por JWT y control de acceso por rol.

Construida con **Java 21** y **Spring Boot**, siguiendo una arquitectura por capas
(controlador, servicio, repositorio) con DTO separados de las entidades de dominio.

## Stack

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 21 |
| Framework | Spring Boot |
| Persistencia | Spring Data JPA (Hibernate) |
| Base de datos | PostgreSQL |
| Seguridad | Spring Security + JWT (jjwt) |
| Validación | Jakarta Validation |
| Utilidades | Lombok |
| Build | Maven |

## Arquitectura

```
teamTaskManager/
├── config/       SecurityConfig: cadena de filtros y reglas de acceso
├── controller/   Endpoints REST (Auth, Project, Task, UserTasks)
├── service/      Lógica de negocio
├── repository/   Acceso a datos con Spring Data JPA
├── domain/       Entidades: User, Role, Project, Task, UserTask
├── dto/          Objetos de entrada y salida, desacoplados del dominio
├── enums/        RoleList
└── jwt/          JwtUtil, JwtAuthenticationFilter, JwtEntryPoint
```

Dos decisiones de diseño que vale la pena señalar:

- **Los DTO no exponen las entidades.** Cada recurso tiene su DTO de entrada y su
  `ResponseDTO` de salida, de modo que el modelo de persistencia puede cambiar sin
  romper el contrato de la API.
- **La capa JWT es propia,** no una librería de andamiaje: `JwtUtil` genera y valida
  el token, `JwtAuthenticationFilter` lo intercepta en cada petición y `JwtEntryPoint`
  centraliza la respuesta ante credenciales ausentes o inválidas.

## Endpoints

### Autenticación (`/auth`)
| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/auth/register` | Registro de usuario |
| POST | `/auth/login` | Devuelve el JWT |
| GET | `/auth/check-auth` | Valida el token actual |

### Proyectos (`/projects`)
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/projects` | Lista todos los proyectos |
| GET | `/projects/with-tasks` | Solo proyectos que tienen tareas |
| GET | `/projects/without-tasks` | Solo proyectos sin tareas |
| GET | `/projects/search` | Búsqueda de proyectos |
| POST | `/projects` | Crea un proyecto |
| PUT | `/projects/{id}` | Actualiza un proyecto |
| DELETE | `/projects/{id}` | Elimina un proyecto |

### Tareas (`/tasks`)
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/tasks` | Lista todas las tareas |
| POST | `/tasks` | Crea una tarea |
| PUT | `/tasks/{id}` | Actualiza una tarea |
| DELETE | `/tasks/{id}` | Elimina una tarea |

### Usuarios y asignaciones (`/users`)
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/users` | Lista usuarios |
| GET | `/users/with-tasks` | Usuarios con tareas asignadas |
| GET | `/users/without-tasks` | Usuarios sin tareas asignadas |
| POST | `/users` | Asigna una tarea a un usuario |
| PUT | `/users/{id}` | Actualiza una asignación |
| DELETE | `/users/{id}` | Elimina una asignación |

Todas las rutas salvo `/auth/login` y `/auth/register` requieren la cabecera
`Authorization: Bearer <token>`.

## Cómo ejecutarlo

Requisitos: **JDK 21** y una instancia de **PostgreSQL**.

1. Crear la base de datos:

```sql
CREATE DATABASE teamtaskmanager;
```

2. Configurar la conexión en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/teamtaskmanager
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CLAVE
spring.jpa.hibernate.ddl-auto=update
```

3. Levantar la aplicación:

```bash
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080`.

4. Probar el registro y el login:

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"miguel","email":"miguel@example.com","password":"secreto123"}'

curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"miguel","password":"secreto123"}'
```

Con el token devuelto:

```bash
curl http://localhost:8080/projects \
  -H "Authorization: Bearer <TOKEN>"
```

## Estado del proyecto

Proyecto personal para profundizar en Spring Boot y en seguridad con JWT. La API
está funcional; el siguiente paso son las pruebas de integración de los
controladores, que hoy solo cuentan con el test de arranque de contexto.

## Autor

Jose Miguel Trujillo — desarrollador backend
[LinkedIn](https://linkedin.com/in/josetrujillot) · [GitHub](https://github.com/Miguel12T)
