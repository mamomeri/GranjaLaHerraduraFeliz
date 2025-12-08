# Requirements – Sistema de Gestión de Alquiler de Animales  
**Granja La Herradura Feliz**

---

## 1. Narrativa del negocio (resumen formal)

La empresa **Granja La Herradura Feliz**, dedicada al entretenimiento familiar desde 1998, alquila animales entrenados (caballos, burros y cerditos amaestrados) para recorridos cortos o actividades guiadas en pistas abiertas.  
La operación actual se basa en registros manuales, lo que genera:

- Doble asignación accidental de animales.  
- Falta de historial claro de alquileres.  
- Confusión sobre disponibilidad real.  
- Dificultad para atención eficiente en días concurridos.

La gerencia desea un **sistema digital sencillo para controlar estos procesos**, sin base de datos todavía, pero con posibilidad de crecimiento.

---

# 2. Objetivo General

Desarrollar un sistema modular en Java que permita gestionar animales, clientes y alquileres, aplicando principios de arquitectura profesional (capas, repositorios, servicios, excepciones, entidades, MVC básico) y dejando preparada la integración futura con **JavaFX** como interfaz visual.

---

# 3. Alcance del Sistema (Versión 1)

### ✔ Funcionalidades Incluidas
- Registro de animales.  
- Registro de clientes.  
- Registro de alquileres.  
- Finalización de alquileres.  
- Consulta de disponibilidad de animales.  
- Validaciones básicas de reglas del negocio.  
- Arquitectura en capas con repositorios en memoria.

### ✖ Funcionalidades Excluidas (Potenciales v2)
- Persistencia real (SQL, archivos).  
- Precios, facturación o métodos de pago.  
- Gestión de múltiples pistas.  
- Roles o login de usuarios.  
- Reportes avanzados.  
- JavaFX (se agregará después de completar v1).  

---

# 4. Requisitos Funcionales (RF)

### **RF01 – Registrar animal**
El sistema debe permitir registrar un nuevo animal indicando:  
- nombre  
- tipo (`AnimalType`)  
El animal debe crearse con estado `AVAILABLE`.

---

### **RF02 – Registrar cliente**
Debe ser posible registrar clientes indicando:  
- id (generado o ingresado)  
- nombre completo  

---

### **RF03 – Crear alquiler**
El sistema debe permitir iniciar un alquiler si:  
1. El animal existe.  
2. El animal está `AVAILABLE`.  

Los datos a registrar son:  
- animal  
- cliente  
- hora de inicio  
- tipo de alquiler (`RentalType`)  

Si no cumple las reglas → lanzar `AnimalNotAvailableException`.

---

### **RF04 – Finalizar alquiler**
Debe ser posible finalizar un alquiler activo:  
- Registrar hora de finalización.  
- Cambiar estado del animal a `AVAILABLE`.

---

### **RF05 – Listar animales disponibles**
El sistema debe mostrar todos los animales con estado `AVAILABLE`.

---

# 5. Requisitos No Funcionales (RNF)

### **RNF01 – Arquitectura**
El sistema debe estar organizado en capas:  
- **model** (entidades y enums)  
- **repository** (interfaces + implementación en memoria)  
- **service** (reglas de negocio)  
- **controller** (flujo del sistema)  
- **exception**  
- **view** (reservado para JavaFX)

---

### **RNF02 – Estructuras de datos**
Los repositorios en memoria usarán:  
- `ArrayList` para almacenamiento general.  
- Búsqueda lineal.  
- No deben usarse estructuras complejas sin justificación.  

---

### **RNF03 – Independencia de implementación**
Las clases de servicio deben depender únicamente de **interfaces de repositorios**, no de sus implementaciones concretas.

---

### **RNF04 – Simplicidad de uso**
El sistema debe poder ejecutarse por consola mediante un controlador de texto.

---

### **RNF05 – Preparación para JavaFX**
El diseño debe permitir acoplar posteriormente una vista JavaFX sin afectar la lógica interna.

---

# 6. Entidades del Sistema

### **Animal**
- id : String  
- name : String  
- type : AnimalType  
- status : AnimalStatus  

### **Customer**
- id : String  
- fullName : String  

### **Rental**
- id : String  
- animal : Animal  
- customer : Customer  
- startTime : LocalDateTime  
- endTime : LocalDateTime?  
- rentalType : RentalType  

---

# 7. Enumeraciones

### **AnimalType**
```
HORSE  
DONKEY  
PIG  
```

### **AnimalStatus**
```
AVAILABLE  
RENTED  
```

### **RentalType**
```
SHORT_RIDE  
HOURLY  
```

*(Opcional futuro: PaymentType)*

---

# 8. Reglas del Negocio

1. Un animal **no puede** iniciar un alquiler si su estado ≠ `AVAILABLE`.  
2. Al finalizar un alquiler, el animal debe volver a `AVAILABLE`.  
3. Se debe garantizar que no existan dos alquileres activos para el mismo animal.  
4. Todas las operaciones deben realizar validaciones de existencia (`findById`).  

---

# 9. Arquitectura y Estructura del Proyecto

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
        view/       # reservado para JavaFX
    resources/
      views/        # FXML futuro
```

---

# 10. Casos de Uso (Diagrama textual)

**CU01:** Registrar Animal  
**CU02:** Registrar Cliente  
**CU03:** Crear Alquiler  
**CU04:** Finalizar Alquiler  
**CU05:** Consultar Disponibilidad  

---

# 11. Suposiciones del Sistema

- No se manejarán pistas individualmente en esta versión.  
- No se manejarán precios ni transacciones económicas.  
- No existe persistencia entre ejecuciones (datos se pierden al cerrar el programa).  

---

# 12. Potenciales Extensiones (v2 y v3)

- Agregar tabla `RentalPlan` reemplazando enum `RentalType`.  
- Persistencia SQL.  
- JavaFX completamente integrado.  
- Reportes por fecha.  
- Roles: administrador / empleado.  
- Control real de pistas con colas de espera.  

---

# 13. Estado del Documento

Versión: 1.0  
Última actualización: automática  
Propósito: referencia formal para el desarrollo del sistema de práctica.
