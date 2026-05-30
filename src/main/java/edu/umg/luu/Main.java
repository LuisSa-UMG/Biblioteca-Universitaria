package edu.umg.luu;

import service.BibliotecaS; // Importa tu servicio real

public class Main {
    public static void main(String[] args) {
        BibliotecaS S1 = new BibliotecaS();
        cargarDatosIniciales(S1);

        Menu menu = new Menu(S1);
        menu.mostrarMenuPrincipal();
    }

    private static void cargarDatosIniciales(BibliotecaS ser) {
        // Registro de Libros (Árbol BST y Grafo)
        ser.registrarLibro(101, "978-0134685991", "Estructuras de Datos con Java", "Programación");
        ser.registrarLibro(102, "978-0132350884", "Clean Code", "Ingeniería");
        ser.registrarLibro(103, "978-0321356680", "Effective Java", "Programación");
        ser.registrarLibro(104, "978-0201633610", "Design Patterns", "Arquitectura");

        // Registrar Usuarios (Tabla Hash)
        ser.registrarUsuario(2001, "Mario", "Gomez", "mgomez@umg.edu.gt", "Estudiante");
        ser.registrarUsuario(2002, "Ana", "Martinez", "amartinez@umg.edu.gt", "Profesor");
        ser.registrarUsuario(2003, "Luis", "Saavedra", "lsaavedra@umg.edu.gt", "Administrador");

        // Relacionar libros en el Grafo
        ser.relacionarLibros(101, 103);
        ser.relacionarLibros(102, 104);
        ser.relacionarLibros(103, 102);
    }
}