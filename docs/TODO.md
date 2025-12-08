# TODO – Sistema de Gestión de Alquiler de Animales  
**Granja La Herradura Feliz**

Este archivo lista las tareas a realizar para desarrollar el sistema paso a paso.  
Las tareas están agrupadas por etapas, en orden recomendado.

---

## Etapa 0 – Preparación del proyecto 

- [x] Crear proyecto Maven/Gradle o proyecto Java simple.
- [x] Configurar paquete base `com.GranjaLaHerraduraFeliz`.
- [x] Agregar archivos de documentación al repo:
  - [x] `README.md`
  - [x] `requirements.md`
  - [x] `TODO.md`
- [ ] Hacer primer commit inicial del proyecto.

---

## Etapa 1 – Modelo de dominio (model)

- [ ] Crear paquete `model`.
- [ ] Implementar enum `AnimalType` (HORSE, DONKEY, PIG).
- [ ] Implementar enum `AnimalStatus` (AVAILABLE, RENTED).
- [ ] Implementar enum `RentalType` (SHORT_RIDE, HOURLY).
- [ ] Implementar clase `Animal`:
  - [ ] Atributos: `id`, `name`, `AnimalType type`, `AnimalStatus status`.
  - [ ] Constructor(es).
  - [ ] Getters/Setters.
  - [ ] `toString()`.
- [ ] Implementar clase `Customer`:
  - [ ] Atributos: `id`, `fullName`.
  - [ ] Constructor(es).
  - [ ] Getters/Setters.
  - [ ] `toString()`.
- [ ] Implementar clase `Rental`:
  - [ ] Atributos: `id`, `Animal animal`, `Customer customer`, `startTime`, `endTime`, `RentalType rentalType`.
  - [ ] Constructor(es).
  - [ ] Getters/Setters.
  - [ ] `toString()`.

---

## Etapa 2 – Excepciones personalizadas (exception)

- [ ] Crear paquete `exception`.
- [ ] Implementar `AnimalNotAvailableException extends RuntimeException`.
- [ ] (Opcional) Implementar `RentalNotFoundException`.

---

## Etapa 3 – Repositorios en memoria (repository)

- [ ] Crear paquete `repository`.

### Interfaces

- [ ] Crear interfaz `AnimalRepository`:
  - [ ] `Animal save(Animal animal);`
  - [ ] `Animal findById(String id);`
  - [ ] `List<Animal> findAll();`
  - [ ] `List<Animal> findByStatus(AnimalStatus status);`
- [ ] Crear interfaz `CustomerRepository`:
  - [ ] `Customer save(Customer customer);`
  - [ ] `Customer findById(String id);`
  - [ ] `List<Customer> findAll();`
- [ ] Crear interfaz `RentalRepository`:
  - [ ] `Rental save(Rental rental);`
  - [ ] `Rental findById(String id);`
  - [ ] `List<Rental> findAll();`
  - [ ] `List<Rental> findActiveByAnimal(Animal animal);`

### Implementaciones en memoria

- [ ] Implementar `InMemoryAnimalRepository` usando `ArrayList`:
  - [ ] Lista interna de `Animal`.
  - [ ] Implementar métodos de la interfaz.
- [ ] Implementar `InMemoryCustomerRepository` usando `ArrayList`.
- [ ] Implementar `InMemoryRentalRepository` usando `ArrayList`.

*(Opcional Etapa 3.5)*  
- [ ] Crear una segunda implementación usando `HashMap` y comparar complejidad.

---

## Etapa 4 – Servicios de negocio (service)

- [ ] Crear paquete `service`.

### `AnimalService`
- [ ] Recibir `AnimalRepository` por constructor.
- [ ] Métodos:
  - [ ] `registerAnimal(...)` – crear animal con estado `AVAILABLE`.
  - [ ] `listAllAnimals()`.
  - [ ] `listAvailableAnimals()`.

### `CustomerService`
- [ ] Recibir `CustomerRepository` por constructor.
- [ ] Métodos:
  - [ ] `registerCustomer(...)`.
  - [ ] `listAllCustomers()`.

### `RentalService`
- [ ] Recibir `AnimalRepository` y `RentalRepository` por constructor.
- [ ] Métodos:
  - [ ] `startRental(animalId, customerId, rentalType)`:
    - [ ] Validar existencia de animal y cliente.
    - [ ] Validar `AnimalStatus.AVAILABLE`.
    - [ ] Crear `Rental` con `startTime = now`.
    - [ ] Cambiar estado de animal a `RENTED`.
    - [ ] Guardar cambios en repositorios.
  - [ ] `finishRental(rentalId)`:
    - [ ] Buscar rental.
    - [ ] Registrar `endTime = now`.
    - [ ] Cambiar estado del animal a `AVAILABLE`.
  - [ ] `listAllRentals()`.

---

## Etapa 5 – Controlador de consola (controller)

- [ ] Crear paquete `controller`.
- [ ] Implementar clase `AppController`:
  - [ ] Recibir servicios por constructor.
  - [ ] Implementar menú de texto:
    - [ ] Opción 1: Registrar animal.
    - [ ] Opción 2: Registrar cliente.
    - [ ] Opción 3: Crear alquiler.
    - [ ] Opción 4: Finalizar alquiler.
    - [ ] Opción 5: Listar animales disponibles.
    - [ ] Opción 0: Salir.
  - [ ] Manejar entradas de usuario con `Scanner`.

- [ ] Crear clase `Main` en paquete raíz:
  - [ ] Instanciar repositorios en memoria.
  - [ ] Instanciar servicios.
  - [ ] Instanciar `AppController`.
  - [ ] Llamar a `controller.run()`.

---

## Etapa 6 – Pruebas manuales básicas

- [ ] Probar registrar algunos animales y clientes.
- [ ] Crear un alquiler válido.
- [ ] Intentar alquilar el mismo animal dos veces (debe fallar con excepción).
- [ ] Finalizar alquiler y verificar que el animal vuelve a `AVAILABLE`.
- [ ] Confirmar que el listado de animales disponibles funciona.

---

## Etapa 7 – Preparar terreno para JavaFX (view)

- [ ] Crear paquete `view`.
- [ ] Definir idea general de pantallas futuras:
  - [ ] Pantalla de listado de animales.
  - [ ] Pantalla de registro de alquiler.
- [ ] Crear carpeta `resources/views/` para futuros FXML.

---

## Etapa 8 – Git y versiones

- [ ] Crear repositorio en GitHub.
- [ ] Hacer commit de la v1 (solo consola).
- [ ] Tag: `v1-enum-architecture`.
- [ ] Documentar en `README.md`:
  - [ ] Estado actual del proyecto.
  - [ ] Siguientes pasos (JavaFX, BD, etc.).

---

## Etapa 9 – Ideas para v2 (no obligatorio ahora)

- [ ] Persistencia con una base de datos sencilla (SQLite, por ejemplo).
- [ ] Migrar `RentalType` de enum a entidad `RentalPlan`.
- [ ] Integrar JavaFX como interfaz gráfica.
- [ ] Agregar precios y cálculos de facturación.
- [ ] Crear reportes por rango de fechas.
