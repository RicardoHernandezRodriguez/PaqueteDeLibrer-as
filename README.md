# ✨ Proyecto: Máquina Tragamonedas en Java ✨

Este es un proyecto de una máquina tragamonedas desarrollada en Java utilizando **Swing** para la interfaz gráfica y soporte de audio para mejorar la experiencia del usuario.

## ✨ Características
- Interfaz gráfica con **Swing**.
- Simulación de los giros de los carretes.
- Música de fondo durante el juego.
- Sonido especial cuando se obtiene un **Jackpot**.
- Uso del patrón **Modelo-Vista-Controlador (MVC)** para una organización clara del código.

## 🛠 Requisitos
Para ejecutar este proyecto necesitas:
- **Java JDK 8+** instalado.
- Un entorno de desarrollo como **Eclipse**, **IntelliJ IDEA** o **NetBeans**.

## 🚀 Ejecución del Proyecto

1. **Clonar o descargar** el repositorio del proyecto.
2. **Abrir el proyecto** en tu entorno de desarrollo preferido.
3. **Compilar y ejecutar** la clase `Main.java`, que es el punto de entrada del programa.

## 📚 Estructura del Proyecto
Tragamonedas/
├── Main.java                 # Punto de entrada del programa
├── SlotMachine.java          # Lógica de la máquina tragamonedas (modelo)
├── SlotMachineView.java      # Interfaz gráfica (vista)
├── SlotMachineController.java # Controlador que maneja la interacción entre la vista y el modelo
└── resources/                # Carpeta de recursos con los sonidos e imágenes
    ├── 1.png, 2.png, ...     # Imágenes de los símbolos del tragamonedas
    ├── LuigiCasino.wav       # Música de fondo
    └── jackpot.wav           # Sonido cuando se gana el Jackpot

## 📌 Notas
- Asegúrate de que la carpeta `resources/` esté en la ubicación correcta y que los archivos de audio e imagen existan.
- Si la música no se reproduce, revisa que el formato sea **WAV** y que Java tenga acceso a los archivos.
- Puedes modificar los símbolos en `SlotMachine.java` si deseas agregar nuevos.

---
🎮 **Diviértete jugando con esta máquina tragamonedas!** 🚀

```
a
```
