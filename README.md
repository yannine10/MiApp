# Proyecto mini app de prueba

---

## Características
- Clona el repo.
- Abre android studio.
- Elige la carpeta con el proyecto clonado.
- Sincroniza el gradle.
- Conecta a un dispositivo real o inicia el emulador.
- Presiona el boton Run para compilar e instalar la app

---

## Estructura de la mini app

En este proyecto utilicé MVVM. La View se encarga solo de mostrar la interfaz y recibir las acciones del usuario, 
mientras que el ViewModel maneja la lógica y expone los datos. 
Para el manejo de datos, uso Room como base de datos local, creando DAO para consultas. Esta forma de trabajar hace que la app sea
más fácil de probar, escalar y mantener, y me permitió separar claramente la interfaz y la lógica.
