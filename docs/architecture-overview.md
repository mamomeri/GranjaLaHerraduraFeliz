# Architecture Overview – Sistema de Gestión de Alquiler de Animales  
**Granja La Herradura Feliz**

Este documento resume la arquitectura general del sistema, los principios utilizados y la relación entre sus componentes. Su objetivo es ofrecer una visión clara y profesional de cómo está construido el software y cómo puede evolucionar en versiones futuras.

---

# 1. Visión General de la Arquitectura

El sistema utiliza una arquitectura **por capas**, un patrón común en aplicaciones Java porque separa responsabilidades, facilita el mantenimiento y permite escalar el código sin romper otras capas.

Las capas principales son:

```
controller/
service/
repository/
model/
exception/
view/  (para JavaFX en futura versión)
```

Cada una cumple un rol definido que se explica más adelante.

La arquitectura sigue principios importantes como:

- **Separation of Concerns (SoC)**  
- **Dependency Injection (manualmente sin framework)**  
- **Single Responsibility Principle (SRP)**  
- **Programar contra interfaces y no implementaciones**

---

# 2. Flujo General del Sistema

El flujo típico de una operación es:

```
[Usuario] → [Controller] → [Service] → [Repository] → [Model]
                                   ↳ [Rules / Exceptions]
```

### 1. El usuario  
Interactúa mediante consola (v1) o GUI JavaFX (v2).

### 2. Controller  
Coordina la acción del usuario, recoge datos y delega.

### 3. Service  
Ejecuta reglas del negocio, validaciones y estados.

### 4. Repository  
Obtiene o persiste datos (en memoria en v1).

### 5. Model  
Entidades simples que representan el dominio.

---

# 3. Arquitectura por Capas (Descripción Detallada)

## 3.1. **Controller Layer**
Responsabilidades:

- Recibir la acción del usuario.
- Solicitar datos necesarios.
- Llamar al servicio adecuado.
- Mostrar resultados u errores.

No debe contener lógica de negocio.

---

## 3.2. **Service Layer**
Es el corazón del sistema.

Responsabilidades:

- Aplicar reglas del negocio.
- Validar datos.
- Manejar estados (`AVAILABLE`, `RENTED`).
- Interactuar con múltiples repositorios.
- Lanzar excepciones controladas.

---

## 3.3. **Repository Layer**
Responsabilidades:

- Manejar acceso a datos.
- Implementar almacenamiento en memoria (`ArrayList`) en v1.
- Más adelante: SQL, HashMap, archivos o API externas.

Los servicios dependen de **interfaces**, no implementaciones.

---

## 3.4. **Model Layer**
Incluye:

- Entidades (Animal, Customer, Rental)
- Enumeraciones (AnimalType, AnimalStatus, RentalType)

No contienen lógica compleja.

---

## 3.5. **Exception Layer**
Responsabilidades:

- Representar errores del negocio.
- Evitar que la aplicación entre en estados inválidos.

Ejemplos:

- `AnimalNotAvailableException`
- `RentalNotFoundException`

---

## 3.6. **View Layer (JavaFX en v2)**
Responsabilidades:

- Mostrar información visualmente.
- Recibir acciones del usuario mediante eventos.

Importante:
> La vista nunca debe contener reglas de negocio; todas viven en `service/`.

---

# 4. Justificación de las Decisiones Arquitectónicas

- Uso de interfaces para repositorios → desacoplamiento total.  
- Enums para valores de dominio.  
- Datos en memoria para simplicidad.  
- Controller separado de Service.  
- Preparación para JavaFX.

---

# 5. Mapa de Dependencias

```
Controller → Service → Repository → Model
                     ↳ Exception
```

---

# 6. Arquitectura en el Tiempo (Evolución)

## v1
- Consola.
- Repositorios en memoria.
- Validaciones básicas.

## v2
- JavaFX.
- Migración de enums a entidades configurables.

## v3
- Persistencia SQL.
- Reportes.

## v4
- Roles de usuario.
- Integración con APIs externas.

---

# 7. Beneficios

- Código limpio y mantenible.  
- Fácil expansión.  
- Testeable.  
- Escalable sin reescritura.  

---

# 8. Conclusión

Esta arquitectura permite que un sistema pequeño evolucione como un sistema profesional, manteniendo orden, claridad y capacidad de crecimiento sin romper su núcleo.
