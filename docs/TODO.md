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

- [x] Crear paquete `model`.
- [x] Implementar enum `AnimalType` (HORSE, DONKEY, PIG).
- [x] Implementar enum `AnimalStatus` (AVAILABLE, RENTED).
- [x] Implementar enum `RentalType` (SHORT_RIDE, HOURLY).
- [x] Implementar clase `Animal`:
  - [x] Atributos: `id`, `name`, `AnimalType type`, `AnimalStatus status`.
  - [x] Constructor(es).
  - [x] Getters/Setters.
  - [x] `toString()`.
- [x] Implementar clase `Customer`:
  - [x] Atributos: `id`, `fullName`.
  - [x] Constructor(es).
  - [x] Getters/Setters.
  - [x] `toString()`.
- [x] Implementar clase `Rental`:
  - [x] Atributos: `id`, `Animal animal`, `Customer customer`, `startTime`, `endTime`, `RentalType rentalType`.
  - [x] Constructor(es).
  - [x] Getters/Setters.
  - [x] `toString()`.

---

## Etapa 2 – Excepciones personalizadas (exception)

- [x] Crear paquete `exception`.
- [x] Implementar `AnimalNotAvailableException extends RuntimeException`.
- [x] (Opcional) Implementar `RentalNotFoundException`.

---

## Etapa 3 – Repositorios en memoria (repository)

- [x] Crear paquete `repository`.

### Interfaces

- [x] Crear interfaz `AnimalRepository`:
  - [x] `Animal save(Animal animal);`
  - [x] `Animal findById(int id);`
  - [x] `List<Animal> findAll();`
  - [x] `List<Animal> findByStatus(AnimalStatus status);`
- [x] Crear interfaz `CustomerRepository`:
  - [x] `Customer save(Customer customer);`
  - [x] `Customer findById(int id);`
  - [x] `List<Customer> findAll();`
- [x] Crear interfaz `RentalRepository`:
  - [x] `Rental save(Rental rental);`
  - [x] `Rental findById(int id);`
  - [x] `List<Rental> findAll();`
  - [x] `List<Rental> findActiveByAnimal(Animal animal);`

### Implementaciones en memoria

- [x] Implementar `InMemoryAnimalRepository` usando `ArrayList`:
  - [x] Lista interna de `Animal`.
  - [x] Implementar métodos de la interfaz.
- [x] Implementar `InMemoryCustomerRepository` usando `ArrayList`.
- [x] Implementar `InMemoryRentalRepository` usando `ArrayList`.

*(Opcional Etapa 3.5)*  
- [ ] Crear una segunda implementación usando `HashMap` y comparar complejidad.

---

## Etapa 4 – Servicios de negocio (service)

- [x] Crear paquete `service`.

### `AnimalService`
- [x] Recibir `AnimalRepository` por constructor.
- [x] Métodos:
  - [x] `registerAnimal(...)` – crear animal con estado `AVAILABLE`.
  - [x] `listAllAnimals()`.
  - [x] `listAvailableAnimals()`.

### `CustomerService`
- [x] Recibir `CustomerRepository` por constructor.
- [x] Métodos:
  - [x] `registerCustomer(...)`.
  - [x] `listAllCustomers()`.

### `RentalService`
- [x] Recibir `AnimalRepository` y `RentalRepository` por constructor.
- [x] Métodos:
  - [x] `startRental(animalId, customerId, rentalType)`:
    - [x] Validar existencia de animal y cliente.
    - [x] Validar `AnimalStatus.AVAILABLE`.
    - [x] Crear `Rental` con `startTime = now` esto se hace en el constructor ya implementado.
    - [x] Cambiar estado de animal a `RENTED`.
    - [x] Guardar cambios en repositorios.
  - [x] `finishRental(rentalId)`:
    - [x] Buscar rental.
    - [x] Registrar `endTime = now`.
    - [x] Cambiar estado del animal a `AVAILABLE`.
  - [x] `listAllRentals()`.

---

## Etapa 5 – Controlador de consola (controller)

- [x] Crear paquete `controller`.
- [x] Implementar clase `AppController`:
  - [x] Recibir servicios por constructor.
  - [x] Implementar menú de texto:
    - [x] Opción 1: Registrar animal.
    - [x] Opción 2: Registrar cliente.
    - [x] Opción 3: Crear alquiler.
    - [x] Opción 4: Finalizar alquiler.
    - [x] Opción 5: Listar animales disponibles.
    - [x] Opción 0: Salir.
  - [x] Manejar entradas de usuario con `Scanner`.

- [x] Crear clase `Main` en paquete raíz:
  - [x] Instanciar repositorios en memoria.
  - [x] Instanciar servicios.
  - [x] Instanciar `AppController`.
  - [x] Llamar a `controller.run()`.

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
