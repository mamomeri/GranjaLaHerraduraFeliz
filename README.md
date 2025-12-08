# Sistema de Gestión de Alquiler de Animales – Granja La Herradura Feliz

## 1. Narrativa del negocio (contexto inventado)

La empresa **Granja La Herradura Feliz**, fundada en 1998 en las afueras de la ciudad de San Prado, ofrece experiencias recreativas a familias, turistas y escuelas. Su principal actividad es el **alquiler de animales entrenados** —como caballos, burros y cerditos amaestrados— para recorridos guiados y actividades en pistas abiertas.

La gerencia, actualmente liderada por **Doña Mariela Rosales**, busca modernizar sus procesos internos. Hasta ahora todo se registra en libretas físicas: qué animal está disponible, qué cliente alquiló qué animal, qué pista está ocupada, etc. Esto ocasiona confusiones, doble asignación de animales y pérdida de historial.

Para mejorar la organización, la empresa requiere un **sistema digital básico**, sin base de datos en esta primera fase, que permita:

- Registrar animales disponibles.
- Registrar clientes.
- Controlar los alquileres de manera sencilla.
- Asegurar que ningún animal sea alquilado dos veces al mismo tiempo.
- Preparar el camino para un sistema visual posterior (JavaFX).

---

## 2. Objetivo del sistema

El sistema tiene como meta:

- Practicar arquitectura Java por capas (controller, service, repository, model).
- Implementar reglas de negocio simples.
- Manejar estados de objetos y validaciones.
- Crear una base sólida para integrar **JavaFX** en una fase posterior.
- Simular un mini–sistema profesional sin complejidad excesiva.

---

## 3. Alcance del sistema (v1)

### Incluido
- Gestión de animales (alta y listado).
- Gestión de clientes.
- Registro de alquileres.
- Finalización de alquileres.
- Validación de disponibilidad del animal.
- Reporte simple de animales disponibles.

### No incluido (posibles v2)
- Persistencia en base de datos.
- Gestión de pistas de manera real.
- Facturación o métodos de pago avanzados.
- Roles de usuario.
- Interfaz visual (por ahora).

---

## 4. Entidades identificadas

### **Animal**
- id (String)
- name (String)
- type (enum `AnimalType`)
- status (enum `AnimalStatus`)

### **Customer**
- id (String)
- fullName (String)

### **Rental**
- id (String)
- animal (Animal)
- customer (Customer)
- startTime (LocalDateTime)
- endTime (LocalDateTime o null)
- rentalType (enum `RentalType`)

---

## 5. Enumeraciones

### **AnimalType**
- HORSE
- DONKEY
- PIG

### **AnimalStatus**
- AVAILABLE
- RENTED

### **RentalType**
- SHORT_RIDE
- HOURLY

### (Opcional) PaymentType
- CASH
- CARD
- TRANSFER

---

## 6. Reglas de negocio

1. Un animal solo puede estar en estado:
   - AVAILABLE  
   - RENTED  
2. Un animal **RENTED** no puede volver a ser alquilado hasta ser devuelto.
3. Un alquiler registra:
   - cliente
   - animal
   - hora inicial
   - tipo de alquiler
4. Si un animal no está disponible o no existe:
   - lanzar `AnimalNotAvailableException`.

---

## 7. Arquitectura del sistema

```
src/
  main/
    java/
      com/empresa/granja/
        controller/
        service/
        repository/
        model/
        exception/
        view/        # reservado para JavaFX
    resources/
      views/         # FXML futuro
```

### Capas principales

- **model** → entidades y enums.
- **repository** → interfaces de acceso a datos + implementación en memoria.
- **service** → reglas de negocio.
- **controller** → flujo de interacción (consola en v1).
- **view** → carpeta preparada para JavaFX.
- **exception** → excepciones personalizadas.

---

## 8. Estructuras de datos utilizadas

- **ArrayList** para almacenamiento principal.
- Búsqueda lineal para simplicidad.
- A futuro:
  - HashMap
  - Base de datos SQL

---

## 9. Casos de uso principales

### CU01 – Registrar animal  
Permite agregar animales al sistema, siempre con estado AVAILABLE.

### CU02 – Registrar cliente  
Crea clientes que podrán alquilar animales.

### CU03 – Iniciar alquiler  
Valida disponibilidad del animal y registra inicio del alquiler.

### CU04 – Finalizar alquiler  
Marca el animal como AVAILABLE nuevamente.

### CU05 – Listar animales disponibles  
Filtra por estado.

---

## 10. Plan de trabajo sugerido

1. Crear estructura de paquetes.
2. Implementar entidades + enums.
3. Crear repositorios en memoria.
4. Implementar servicios.
5. Crear controlador de consola.
6. Integrar JavaFX en v2.

---

## 11. Futuras expansiones

- Manejar pistas como entidades reales.
- Persistencia con SQLite/MySQL.
- Precios, descuentos y facturación.
- Migrar `RentalType` a entidad configurable.
- Interfaz JavaFX completa.

---

## 12. Estado del proyecto

Versión actual: **v1 – Arquitectura básica**  
Creado para fines didácticos y entrenamiento profesional.

---

## 13. Autor

Proyecto de práctica desarrollado por **Marcos Moreira**, inspirado en ejercicios de ingeniería de software y arquitectura profesional en Java.
