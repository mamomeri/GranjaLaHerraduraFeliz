# Componentes de Software y System Design  
## Guía aplicada al proyecto *GranjaLaHerraduraFeliz*

Este documento explica **todos los componentes clásicos** de una arquitectura de software  
(servicios, repositorios, controladores, modelos, DTOs, factories, mappers, event buses, etc.).  
También explica **cuándo y por qué se usan** y **cómo aparecen en tu proyecto actual**.

---

# 1. Modelo General de Arquitectura por Capas

Una aplicación típica (desde pequeña hasta empresarial) se compone de:

```
[ UI / Presentation ]
        ↓
[ Controllers ]
        ↓
[ Services (Business Logic) ]
        ↓
[ Repositories (Data Access) ]
        ↓
[ Data Source (Memoria, BD, API) ]
```

Además pueden existir capas auxiliares:

- DTOs
- Mappers
- Factories
- Event Systems
- Capa de dominio con entidades y reglas propias
- Infraestructura
- Utilidades / Helpers  
etc.

---

# 2. Capa de Dominio (Domain Model)

## ¿Qué es?
Contiene las **entidades y reglas del dominio**.  
Representa el “vocabulario” del sistema.

## En tu proyecto:
- `Animal`
- `Customer`
- `Rental`
- Enums: `AnimalStatus`, `AnimalType`, `RentalType`

## ¿Cuándo usar?
Siempre.  
El dominio existe en cualquier proyecto, desde un CRUD simple hasta una plataforma robusta.

## Señales de buen diseño:
- No depende de frameworks.
- No tiene lógica ajena al negocio (no enviar emails, no conectarse a BD).
- Es puro Java y reglas del problema.

---

# 3. Controladores (Controllers)

## ¿Qué es?
Interfaz entre el usuario y el sistema.  
Puede ser:
- consola,
- GUI JavaFX,
- API REST,
- app móvil.

## En tu proyecto:
- `AppController` (consola)
- `Main` como punto de entrada.

## Responsabilidades:
- Leer input.
- Llamar a servicios.
- Mostrar resultados.
- Manejar errores de manera simple.

## NO debe hacer:
- Validaciones de negocio complejas.
- Acceder directo a repositorios.
- Crear reglas nuevas.

---

# 4. Servicios (Service Layer)

## ¿Qué es?
La capa donde vive la **lógica de negocio real**.  
“Lo que la empresa hace”.

## En tu proyecto:
- `AnimalService`
- `CustomerService`
- `RentalService` (tu servicio más “inteligente”).

## Responsabilidades:
- Validaciones del dominio.
- Coordinación entre repositorios.
- Cambios de estado (`AVAILABLE → RENTED`).
- Creación de entidades.
- Lanzar excepciones personalizadas.

## Paralelismo con System Design:
Si tu sistema creciera, cada servicio podría convertirse en un microservicio separado:
- `AnimalService` → *Animal Management Service*
- `RentalService` → *Rental Orchestrator*
- `CustomerService` → *Customer Registry Service*

---

# 5. Repositorios (Repository Pattern)

## ¿Qué es?
Una abstracción para **acceder a los datos**.

## En tu proyecto:
- Interfaces:
  - `AnimalRepository`
  - `CustomerRepository`
  - `RentalRepository`

- Implementaciones:
  - `InMemoryAnimalRepository`
  - `InMemoryCustomerRepository`
  - `InMemoryRentalRepository`

## Responsabilidades:
- CRUD.
- Búsquedas.
- Persistencia (memoria por ahora).

## No deben hacer:
- Reglas de negocio.
- Cambiar estados de entidades.
- Validar procesos complejos.

---

# 6. Excepciones personalizadas (Exception Layer)

## ¿Qué es?
Manejo de errores semánticos del negocio.

## En tu proyecto:
- `AnimalNotAvailableException`
- `RentalNotFoundException`

Estas excepciones **modelan situaciones reales** del dominio.

## Buena práctica:
Las excepciones van en una carpeta `exception/`,  
son parte del dominio, pero no de servicios ni repositorios directamente.

---

# 7. DTOs (Data Transfer Objects)

## ¿Qué es?
Clases simples usadas para transportar datos **entre capas** o **hacia interfaces externas**.

No tienes DTOs todavía, pero los necesitarás cuando:

- Hagas una API REST.
- Hagas pantallas JavaFX.
- Tengas objetos complejos que NO quieres exponer directamente.

## Ejemplo aplicado a tu proyecto:
```java
public class AnimalDTO {
    public int id;
    public String name;
    public String type;
    public String status;
}
```

Sirve para mostrar datos sin exponer toda la entidad.

---

# 8. Mappers (Convertidores entre DTO ↔ Entidad)

Los DTOs necesitan mapeo a entidades.

Ejemplo aplicado:
```java
public class AnimalMapper {
    public static AnimalDTO toDTO(Animal animal) { ... }
    public static Animal fromDTO(AnimalDTO dto) { ... }
}
```

**En tu proyecto todavía no es necesario**,  
pero será clave cuando agregues vistas o API.

---

# 9. Factories (Constructores especializados)

Se usan cuando la creación de un objeto se vuelve compleja.

Ejemplo aplicado a tu dominio:
```java
public class RentalFactory {
    public static Rental createNewRental(Animal a, Customer c, RentalType type) {
        Rental r = new Rental();
        r.setAnimal(a);
        r.setCustomer(c);
        r.setRentalType(type);
        return r;
    }
}
```

Beneficios:
- Encapsula la lógica de construcción.
- Permite futuras variaciones sin modificar el servicio.

---

# 10. Event Bus / Domain Events

En sistemas grandes, cuando algo pasa en el dominio, se emiten **eventos**.

Ejemplo aplicado a tu dominio:

- Evento: *AnimalRented*
- Evento: *RentalFinished*

En tu proyecto actualmente NO es necesario, pero entenderlo te prepara para system design.

Ventajas:
- Desacopla el flujo del sistema.
- Permite agregar listeners (notificaciones, auditorías, logs).

---

# 11. Capa de Infraestructura

Incluye:
- Archivos
- Base de datos
- Adaptadores de API
- Conexiones externas

En tu proyecto esta capa es mínima porque solo usas **listas en memoria**.

Cuando agregues una BD:
- Crearás adaptadores JDBC o JPA.
- Mantendrás el mismo contrato en el repositorio.

---

# 12. Componentes utilitarios (Utils / Helpers)

Clases que no pertenecen al dominio pero ayudan:

- Fechas (`DateUtils`)
- Validadores (`InputValidator`)
- Loggers
- Conversores

Evitan que el service se llene de código repetitivo.

---

# 13. Cómo se relaciona todo esto con System Design

System Design escala estas mismas ideas a sistemas distribuidos.

## En tu proyecto (pequeño)
- Un service orquesta entidades.
- Los repositorios guardan datos en memoria.
- No hay múltiples procesos.

## En un sistema mediano
- Podrías dividir en módulos independientes.
- Cada módulo tiene sus repos, servicios, controladores.

## En un sistema grande / microservicios
- Cada servicio de negocio (Rental, Customer, Animal) sería un **microservicio separado**.
- El “repositorio” sería una **base de datos separada**.
- El “service” sería una API REST.
- El “controller” sería el endpoint HTTP.
- Los eventos (RentalCreatedEvent) permitirían comunicación entre servicios.

---

# 14. Checklist práctico al diseñar software

Guárdalo como referencia:

### ¿Dónde va la lógica?
- ¿Es una regla de negocio? → **Service**
- ¿Es solo guardar o buscar? → **Repository**
- ¿Es interacción con el usuario? → **Controller**
- ¿Es visualización o intercambio externo? → **DTO**
- ¿Es creación complicada de objetos? → **Factory**
- ¿Es conversión de datos? → **Mapper**
- ¿Es una reacción a un evento del sistema? → **Event Handler**
- ¿Es parte del lenguaje del dominio? → **Model / Entity**
- ¿Es infraestructura? → **Infra layer**

---

# 15. Paralelismo directo con tu proyecto actual

| Componente | Ejemplo en tu sistema | ¿Deberías expandirlo? |
|-----------|-----------------------|------------------------|

