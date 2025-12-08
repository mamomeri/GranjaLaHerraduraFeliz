# Buenas Prácticas en GitHub para Proyectos Pequeños y Profesionales

Este documento resume buenas prácticas para usar Git y GitHub en proyectos como tu **Sistema de Gestión de Alquiler de Animales**, pero con mentalidad profesional. Incluye:

- Organización de ramas.
- Convenciones de commits.
- Pull Requests.
- Conceptos básicos de CI/CD y DevOps.
- Un ejemplo de flujo de trabajo.

---

## 1. Ramas en GitHub: ¿cómo organizarse?

### 1.1. Rama principal: `main`

- Representa el estado **estable** del proyecto.
- Todo lo que está en `main` **debería compilar y funcionar**.
- Es la rama que otros usarían si “clonan” tu proyecto para verlo.

Regla mental:  
> “Si algo está roto, no debería estar en `main`.”

---

### 1.2. Rama de desarrollo (opcional): `develop`

En proyectos pequeños, puedes trabajar directamente con:

- `main` + ramas de características.

En proyectos un poco más serios, se usa:

- `main` → código estable (versiones).
- `develop` → código en desarrollo, donde se integran nuevas funcionalidades antes de pasar a `main`.

Para tu proyecto, basta con:

- `main`
- ramas de características (`feature/...`)

---

### 1.3. Ramas de características (features)

Cada “tarea grande” debería ir en una rama aparte:

Ejemplos de nombres:

- `feature/model-layer`
- `feature/rental-service`
- `feature/javafx-ui`

Flujo típico:

1. Creas rama desde `main`:
   ```bash
   git checkout main
   git pull
   git checkout -b feature/rental-service
   ```
2. Programas y haces commits en esa rama.
3. Cuando terminas, subes la rama y creas un Pull Request hacia `main`.

Ventajas:

- El historial de `main` se mantiene limpio.
- Puedes revisar cambios antes de fusionarlos.
- Si rompes algo, solo afecta esa rama.

---

### 1.4. Ramas de corrección (bugfix/hotfix)

Cuando algo en `main` está roto o tiene un bug urgente, se crean ramas tipo:

- `bugfix/wrong-rental-status`
- `hotfix/null-pointer-rental`

Se corrige, se prueba y se fusiona de vuelta.

---

## 2. Convenciones para mensajes de commit

Un buen commit responde:  
> “¿Qué cambió y por qué, de forma corta?”

Formato recomendado:

```text
tipo: resumen corto en presente
```

Ejemplos de **tipos** útiles:

- `feat`: nueva funcionalidad.
- `fix`: corrección de bug.
- `refactor`: cambio interno sin alterar comportamiento.
- `docs`: cambios en documentación.
- `test`: cambios o adición de tests.
- `chore`: tareas menores (config, formateo, etc).

Ejemplos aplicados a tu proyecto:

```text
feat: implement rental service basic logic
fix: prevent rental creation when animal is rented
docs: add architecture overview and requirements
refactor: extract animal state validation to service
```

Reglas:

- No hagas commits del tipo `update`, `cambios`, `cosas`.
- Un commit debe representar un cambio coherente, no “todo lo del día”.
- Commits pequeños pero significativos son mejores que uno gigante ilegible.

---

## 3. Pull Requests (PRs)

Un **Pull Request** es la forma ordenada de decir:

> “Quiero fusionar mi rama `feature/...` en `main`. Estos son mis cambios. Revísalos.”

Aunque estés solo, **crear PRs tiene sentido** porque:

- Dejas un registro claro de qué se implementó y cuándo.
- Puedes escribir una descripción de alto nivel.
- En el futuro, si trabajas en equipo, ya tendrás el hábito.

Contenido recomendado en un PR:

- Resumen de la funcionalidad.
- Lista breve de cambios.
- Cómo probarlo (pasos de prueba).
- Referencias a issues (si las hay).

---

## 4. ¿Qué es CI/CD y qué tiene que ver con GitHub?

### 4.1. CI – Integración Continua (Continuous Integration)

Idea principal:

> Cada vez que subes código, una máquina (pipeline) lo compila, ejecuta tests y valida que no hayas roto nada.

En GitHub, esto se hace con **GitHub Actions**.

Ejemplo simple:

- Cada vez que haces `push` o PR a `main`:
  - Se ejecuta `mvn test` (o similar).
  - Si falla, sabes que el cambio rompió algo.

Beneficios:

- Menos miedo a integrar cambios.
- Detectas errores temprano.

---

### 4.2. CD – Despliegue Continuo (Continuous Delivery/Deployment)

Idea principal:

> Después de pasar CI, el sistema puede desplegarse automáticamente a un servidor, entorno de pruebas o producción.

En proyectos personales, puede ser:

- Hacer build automático y subir un `.jar` a GitHub Releases.
- Publicar una versión en una máquina propia o en la nube.

Para tu caso actual, con una app local de práctica, con CI basta.

---

### 4.3. ¿Qué tiene que ver esto con DevOps?

**DevOps** es una cultura/práctica donde:

- Desarrollo (Dev) y Operaciones (Ops) trabajan juntos.
- Se automatizan:
  - Integración de código (CI).
  - Despliegue (CD).
  - Monitoreo.
  - Escalabilidad.

No necesitas montarte una infraestructura pesada, pero entender CI/CD ya te pone en modo “DevOps-lite”.

---

## 5. Ejemplo simple de flujo completo

Aplicado a tu proyecto de la granja:

### Paso 1 – Crear repo y rama principal

```bash
git init
git add .
git commit -m "chore: initial project structure"
git branch -M main
git remote add origin <url-del-repo>
git push -u origin main
```

---

### Paso 2 – Nueva funcionalidad: Rental Service

```bash
git checkout -b feature/rental-service
```

Trabajas en:

- `RentalService`
- `AnimalNotAvailableException`
- Actualizas `TODO.md`

Commits:

```bash
git add .
git commit -m "feat: implement basic rental service"
git commit -m "docs: update TODO steps for rental feature"
```

Subes rama:

```bash
git push -u origin feature/rental-service
```

---

### Paso 3 – Pull Request

En GitHub:

1. Creas PR `feature/rental-service → main`.
2. En la descripción pones:
   - Resumen de cambios.
   - Cómo probar:
     - “Correr `Main`, registrar 2 animales, registrar un cliente, crear y finalizar un alquiler.”
3. Revisas tus propios cambios (auto-code review).
4. Haces merge a `main` cuando estés conforme.

---

### Paso 4 – CI sencillo (idea)

En `.github/workflows/java-ci.yml` podrías tener algo que:

- Se ejecute en cada push/PR a `main`.
- Corra `mvn -q -DskipTests=false test`.

Si los tests fallan → el PR marca error.

---

## 6. Resumen práctico para ti

- Usa **`main`** como rama estable.
- Crea ramas `feature/...` para tareas nuevas.
- Escribe commits claros con tipo + descripción.
- Haz PRs aunque trabajes solo (disciplina profesional).
- Aprende CI como siguiente paso natural (GitHub Actions).
- Piensa siempre en que tu proyecto pueda:
  - crecer,
  - integrarse,
  - probarse automáticamente.

---

Este documento sirve como guía base para tus próximos repositorios, tanto personales como futuros proyectos freelance o laborales.
