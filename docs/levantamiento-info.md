# README – Introducción al Levantamiento de Información, Casos de Uso y Ciclos de Desarrollo

## 1. ¿Qué es el levantamiento de información?

El **levantamiento de información** es la primera etapa del desarrollo de software, donde se recolecta, organiza e interpreta todo lo necesario para comprender el problema que se resolverá. Permite evitar malentendidos, limitar el alcance del proyecto y establecer bases sólidas para el diseño del sistema.

### Objetivos principales:
- Comprender qué necesita el cliente o negocio.
- Identificar el alcance real del sistema.
- Documentar entidades, reglas y procesos.
- Alinear expectativas entre desarrollador y cliente.
- Crear una guía clara para escribir código.

---

## 2. Componentes del levantamiento de información

### **2.1. Narrativa o descripción del negocio**
Explica quién es la empresa o cliente, qué hace y por qué necesita el sistema. Da contexto humano al proyecto.

### **2.2. Problema a resolver**
Resume qué está fallando y por qué es necesario un software.

### **2.3. Objetivo del sistema**
Define qué se busca lograr con la solución.

### **2.4. Alcance**
Describe:
- Qué funcionalidades se incluirán en la versión inicial.
- Qué funciones quedan fuera (para evitar sobrecarga y confusión).

### **2.5. Entidades**
Identifica los objetos principales del dominio y sus atributos.

### **2.6. Reglas del negocio**
Son restricciones o comportamientos reales que deben reflejarse en el sistema.

### **2.7. Casos de uso**
Explican cómo interactúan los actores con el sistema (acciones concretas).

### **2.8. Requisitos funcionales y no funcionales**
Sirven como base técnica para el desarrollo y pruebas.

---

## 3. Diferencias entre levantamiento en Freelance y en Empresa

### ✔ Freelance
- Más informal.
- Tú mismo debes guiar la conversación.
- El cliente no conoce términos técnicos.
- Debes traducir necesidades vagas a requisitos claros.
- Puedes usar documentos simples (markdown, notas, PDFs).

**En freelance tú diriges el proceso.**

### ✔ En empresa
- El levantamiento puede ser muy formal.
- Roles como Business Analysts pueden encargarse de esta fase.
- Se generan documentos como:
  - Especificación funcional.
  - Historias de usuario.
  - Diagramas UML o procesos BPMN.
- Revisión con varias áreas (Producto, Ingeniería, UX).

**En empresa el proceso es más estandarizado y técnico.**

---

## 4. ¿Dónde se manifiestan los casos de uso en el código?

### 📌 **1. Servicios (`service/`)**
Aquí vive la lógica principal del caso de uso.  
Ejemplo:  
CU03 “Crear alquiler” → método `startRental()` del `RentalService`.

Incluye:
- Validaciones.
- Reglas del negocio.
- Cambios de estado.

### 📌 **2. Controlador (`controller/`)**
Coordina la interacción:
- Recibe acciones del usuario.
- Solicita datos.
- Llama al servicio correspondiente.

No contiene reglas de negocio.

### 📌 **3. Repositorios (`repository/`)**
Ofrecen acceso a los datos requeridos por los casos de uso.

### 📌 **4. Documentación dentro del código**
Se expresa mediante:
- Comentarios JavaDoc.
- Nombres descriptivos de métodos.
- Organización clara del código.

---

## 5. ¿Dónde van las validaciones?

### ✔ En **servicios**
Siempre deben manejar:
- Disponibilidad.
- Reglas del negocio.
- Estados válidos.

### ✖ No deben ir en:
- Controladores (son solo coordinación).
- Vistas (UI).
- Repositorios (excepto validaciones menores de existencia).

---

## 6. Ciclos de desarrollo (iteraciones de un proyecto)

Cada software profesional evoluciona en **ciclos**, también llamados iteraciones.

### **Ciclo 1 – Levantamiento de información**
Recolectar requisitos y definir el sistema.

### **Ciclo 2 – Diseño de arquitectura**
- Definir entidades.
- Definir capas.
- Elegir estructuras de datos.
- Diagramar si es necesario.

### **Ciclo 3 – Implementación de la versión mínima (v1 / MVP)**
Crear la funcionalidad esencial que hace que el sistema funcione, sin extras.

### **Ciclo 4 – Pruebas**
- Validar reglas del negocio.
- Probar casos de uso.

### **Ciclo 5 – Mejoras (v2, v3…)**
Agregar:
- Vista gráfica (JavaFX).
- Persistencia SQL.
- Nuevos casos de uso.
- Refactoring.

### **Ciclo 6 – Documentación y cierre**
Actualizar:
- README
- requirements.md
- TODO.md (siguientes pasos)

---

## 7. Resumen general

| Elemento | Ubicación | Propósito |
|---------|-----------|-----------|
| Levantamiento | Documentos (`README`, `requirements`) | Entender el problema |
| Casos de uso | Servicios y controladores | Modelar el comportamiento esperado |
| Validaciones | Servicios | Reglas del negocio |
| Arquitectura | Paquetes y clases | Orden y escalabilidad |
| Ciclos | Iteraciones del proyecto | Evolución del software |

---

Este documento sirve como guía conceptual para desarrolladores que están aprendiendo arquitectura y análisis de requisitos en proyectos reales.

