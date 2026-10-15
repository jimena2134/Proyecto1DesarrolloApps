# QHambron

## Descripción del proyecto

QHambron es una aplicación móvil que permitirá a los usuarios registrar y organizar restaurantes y lugares de comida que hayan visitado o que les gustaría conocer.

La idea del proyecto es facilitar que las personas puedan guardar información de diferentes establecimientos, ya que muchas veces encontramos lugares interesantes para comer, pero después olvidamos sus nombres o ubicaciones.

Por medio de la aplicación, los usuarios podrán crear una lista personal de restaurantes, agregar fotografías, consultar sus ubicaciones y llevar un control de los lugares visitados y los que tienen pendientes.

## Integrantes

- Diana Gómez Alvarado
- Jimena Acosta

## Organización del trabajo



- **Jimena Acosta:** Encargada del diseño de los mockups de las pantallas de la aplicación, considerando buenas prácticas de UX y manteniendo una interfaz sencilla y consistente.
- **Diana Gómez Alvarado:** Encargada de la documentación del proyecto, incluyendo la descripción, objetivos, funcionalidades y organización del README.



## Objetivo del proyecto

Desarrollar una aplicación móvil que permita registrar, organizar y consultar lugares de comida de manera sencilla, utilizando diferentes funcionalidades que faciliten el manejo de la información.

## Funcionalidades principales

### 1. CRUD de lugares de comida

La aplicación permitirá que los usuarios administren los restaurantes y establecimientos que deseen guardar.

Se podrán realizar las siguientes acciones:

- **Crear:** Agregar nuevos lugares de comida.
- **Consultar:** Ver la información de los establecimientos registrados.
- **Actualizar:** Modificar los datos de un lugar.
- **Eliminar:** Borrar establecimientos que ya no se quieran mantener en la lista.

Cada registro podrá incluir información como el nombre, tipo de comida, provincia, descripción, rango de precio, calificación, estado, fotografía y ubicación.

### 2. Lista con filtros personalizados

QHambron contará con una lista donde se mostrarán todos los establecimientos registrados por el usuario.

Para facilitar la búsqueda, se podrán aplicar los siguientes filtros:

- **Tipo de comida:** Hamburguesas, pizza, comida típica, cafeterías, postres, entre otros.
- **Provincia:** Permite buscar lugares según la provincia donde se encuentran.
- **Estado:** Permitirá clasificar los establecimientos en dos categorías:
  - **Visitado:** Lugares de comida que el usuario ya ha conocido.
  - **Quiero visitar:** Lugares que el usuario desea conocer en el futuro.

Estos filtros ayudarán a encontrar los establecimientos de una manera más rápida y organizada.

### 3. Acceso a cámara o galería

La aplicación permitirá agregar hasta cinco fotografías por lugar, ya sea tomando imágenes con la cámara o seleccionándolas desde la galería. Estas fotografías podrán visualizarse en la pantalla de detalle mediante un carrusel de imágenes.

### 4. Ventanas Dialog

Se utilizarán ventanas emergentes para confirmar algunas acciones dentro de la aplicación.

Por ejemplo, cuando el usuario quiera eliminar un restaurante, aparecerá un mensaje preguntando si está seguro de realizar esta acción.

El usuario tendrá dos opciones:

- **Cancelar:** Mantener el registro.
- **Eliminar:** Confirmar que desea borrar el establecimiento.

Esto permitirá evitar que se elimine información por accidente.

### 5. Mapa de ubicaciones

QHambron contará con una función de mapa que permitirá consultar la ubicación de los establecimientos registrados.

Al agregar un nuevo lugar, el usuario podrá seleccionar su ubicación en el mapa. Esta ubicación se guardará mediante coordenadas de latitud y longitud.

Posteriormente, desde la pantalla de detalle del establecimiento, el usuario podrá visualizar su ubicación mediante un marcador en el mapa.

Esto permite que el mapa sea sencillo de implementar y cumpla con el requisito del profesor.

## Funcionamiento de la aplicación

Al ingresar a QHambron, el usuario encontrará una pantalla de inicio desde la cual podrá agregar un nuevo lugar o acceder a la lista de establecimientos registrados.

Desde la lista podrá agregar nuevos establecimientos, aplicar filtros y seleccionar un lugar para consultar su información completa.

Al registrar un establecimiento, el usuario podrá ingresar datos como nombre, tipo de comida, provincia, descripción, rango de precio, calificación, estado, fotografía y ubicación.

También podrá editar la información de los lugares registrados, eliminar establecimientos y visualizar sus ubicaciones en el mapa.

Toda la información quedará almacenada localmente en el dispositivo, permitiendo que el usuario organice sus propios lugares de comida.

## Alcance inicial

QHambron será una aplicación móvil de uso personal, donde cada usuario podrá administrar sus propios lugares de comida.

Los establecimientos registrados serán almacenados localmente y no se compartirán públicamente con otros usuarios.

La primera versión de la aplicación no incluirá funciones de pedidos, reservaciones, pagos, comentarios públicos ni interacción entre usuarios.

El proyecto se enfocará principalmente en registrar, organizar, consultar y administrar lugares de comida de una manera sencilla.

## Pantallas de la aplicación

Para el desarrollo de QHambron se proponen las siguientes pantallas:

1. **Inicio:** Será la pantalla principal de la aplicación. Permitirá acceder rápidamente al formulario para agregar un nuevo lugar y navegar hacia la lista de establecimientos registrados.
2. **Lista de lugares:** Mostrará los restaurantes y establecimientos registrados por el usuario. Contará con opciones de búsqueda y filtros personalizados.
3. **Detalle del lugar:** Permitirá consultar la información completa de un establecimiento, incluyendo fotografía, tipo de comida, provincia, precio, calificación, estado, descripción y ubicación.
4. **Agregar lugar:** Contendrá un formulario donde el usuario podrá registrar nuevos establecimientos, agregar fotografías y seleccionar su ubicación.
5. **Editar lugar:** Permitirá modificar la información de un establecimiento registrado anteriormente.
6. **Mapa:** Permitirá visualizar la ubicación guardada de un establecimiento mediante un marcador. También se utilizará al agregar o editar un lugar para seleccionar o modificar su ubicación.

Además, se utilizará una ventana **Dialog de confirmación** cuando el usuario desee eliminar un establecimiento, para evitar que se borre información accidentalmente.

## Tecnologías propuestas

Para el desarrollo de la aplicación se contempla utilizar las siguientes herramientas:

- **Android Studio:** Entorno de desarrollo para crear la aplicación móvil.
- **Kotlin:** Lenguaje de programación utilizado para desarrollar las funcionalidades.
- **Base de datos local:** Para almacenar la información de los establecimientos registrados por el usuario.
- **Cámara y galería de Android:** Para tomar fotografías o seleccionar imágenes del dispositivo.
- **Google Maps u otra alternativa:** Para seleccionar y visualizar las ubicaciones de los establecimientos.
- **GitHub:** Para almacenar el proyecto, documentar los avances y administrar los cambios mediante Pull Requests.

## Mockups

Los mockups de QHambron representan el diseño inicial de las pantallas que tendrá la aplicación.

Estos diseños se elaborarán considerando buenas prácticas de experiencia de usuario (UX), buscando una interfaz minimalista, intuitiva y fácil de utilizar.

Se utilizará una paleta de colores cálidos, principalmente naranja y coral, acompañados de tonos beige, amarillo y verde suave.

Las pantallas mantendrán una estructura visual consistente, con botones identificables, navegación sencilla y una distribución organizada de la información.

A continuación, se presentan los mockups de las pantallas propuestas:

### Pantalla de inicio

### Lista de lugares

### Detalle del lugar

### Agregar lugar

### Editar lugar

### Mapa

### Dialog de eliminación
