package edu.umg.luu;

import model.libro;
import model.usuario;
import model.prestamo;
import model.historial;
import service.BibliotecaS;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final BibliotecaS servicio;
    private final Scanner scanner;

    public Menu(BibliotecaS servicio) {
        this.servicio = servicio;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("     SISTEMA DE GESTIÓN DE BIBLIOTECA UNIVERSITARIA     ");
            System.out.println("________________________________________________________");
            System.out.println("1. Gestión de Libros");
            System.out.println("2. Gestión de Usuarios");
            System.out.println("3. Préstamos y Devoluciones");
            System.out.println("4. Colas de Espera por Libro");
            System.out.println("5. Recomendaciones de Libros");
            System.out.println("6. Acciones Reciente");
            System.out.println("0. Salir");
            System.out.println("\nSelecciona una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        submenuLibros();
                        break;
                    case 2:
                        submenuUsuarios();
                        break;
                    case 3:
                        submenuPrestamos();
                        break;
                    case 4:
                        verColasDeEspera();
                        break;
                    case 5:
                        submenuRelaciones();
                        break;
                    case 6:
                        mostrarHistorialAuditoria();
                        break;
                    case 0:
                        System.out.println("\nGracias por utilizar el sistema de la Biblioteca! Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }

                if (opcion != 0) {
                    pausarPantalla();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingresa un número válido.");
                pausarPantalla();
            }
        }
    }


    // SubMenu para Libros

    private void submenuLibros() {
        System.out.println("\n---------  LIBROS  ---------");
        System.out.println("1. Registrar Libro");
        System.out.println("2. Mostrar Libros Registrados");
        System.out.println("3. Mostrar Libros en Preorden");
        System.out.println("4. Mostrar Libros en Postorden");
        System.out.println("5. Buscar Libro");
        System.out.println("6. Eliminar Libro");
        System.out.println("\nOpción: ");

        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1:
                System.out.print("Ingresa ID (Código numérico): ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Ingresa ISBN: ");
                String isbn = scanner.nextLine();
                System.out.print("Ingresa Título: ");
                String titulo = scanner.nextLine();
                System.out.print("Ingresa Categoría: ");
                String cat = scanner.nextLine();
                servicio.registrarLibro(id, isbn, titulo, cat);
                System.out.println("\nLibro registrado exitosamente en el Árbol BST y en el Grafo.");
                break;
            case 2:
                mostrarLibros(servicio.LibrosOrdenadosId(), "REGISTRADOS");
                break;
            case 3:
                // Adaptado: servicio.LibrosPreorden()
                mostrarLibros(servicio.LibrosPreorden(), "PREORDEN");
                break;
            case 4:
                // Adaptado: servicio.LibrosPostorden()
                mostrarLibros(servicio.LibrosPostorden(), "POSTORDEN");
                break;
            case 5:
                buscarLibro();
                break;
            case 6:
                System.out.print("Ingresa el ID del libro a eliminar: ");
                int idEliminar = Integer.parseInt(scanner.nextLine());
                if (servicio.eliminarLibro(idEliminar)) {
                    System.out.println("Libro eliminado del árbol y del grafo con éxito.");
                } else {
                    System.out.println("No se encontró ningún libro con ese ID.");
                }
                break;
            default:
                System.out.println("Opción cancelada.");
        }
    }

    private void mostrarLibros(List<libro> libros, String tipoRecorrido) {
        System.out.println("\n--- LISTADO DE LIBROS (" + tipoRecorrido + ") ---");
        if (libros.isEmpty()) {
            System.out.println("Ningún libro registrado.");
            return;
        }
        for (libro l : libros) {
            System.out.printf("ID: %-4d | ISBN: %-13s | Título: %-25s | Categoría: %-15s | Disponible: %-5s%n",
                    l.getId(), l.getIsbn(), l.getTitulo(), l.getCategoria(), l.getDisponible() ? "SÍ" : "NO");
        }
    }

    private void buscarLibro() {
        System.out.println("TIPO DE BUSQUEDA");
        System.out.println("1. Código");
        System.out.println("2. ISBN");
        System.out.println("3. Título");
        System.out.println("\nOpción: ");
        int criterio = Integer.parseInt(scanner.nextLine());

        libro encontrado = null;
        switch (criterio) {
            case 1:
                System.out.print("Ingresa ID a buscar: ");
                int id = Integer.parseInt(scanner.nextLine());
                encontrado = servicio.buscarLibroId(id);
                break;
            case 2:
                System.out.print("Ingresa ISBN a buscar: ");
                String isbn = scanner.nextLine();
                encontrado = servicio.buscarLibroIsbn(isbn);
                break;
            case 3:
                System.out.print("Ingresa Título exacto a buscar: ");
                String titulo = scanner.nextLine();
                encontrado = servicio.buscarLibroTitulo(titulo);
                break;
        }

        if (encontrado != null) {
            System.out.println("\n\nLibro Encontrado:");
            System.out.println("----------------------------------------");
            System.out.println("ID: " + encontrado.getId());
            System.out.println("Título: " + encontrado.getTitulo());
            System.out.println("ISBN: " + encontrado.getIsbn());
            System.out.println("Categoría: " + encontrado.getCategoria());
            System.out.println("Disponible: " + (encontrado.getDisponible() ? "SÍ" : "NO"));
            System.out.println("----------------------------------------");
        } else {
            System.out.println("No se encontró ningún libro con el criterio ingresado.");
        }
    }


    // SubMenu Usuarios

    private void submenuUsuarios() {
        System.out.println("\n---   USUARIOS  ---");
        System.out.println("1. Registrar Usuario");
        System.out.println("2. Usuarios Registrados");
        System.out.println("3. Buscar Usuario");
        System.out.println("\nOpción: ");

        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1:
                System.out.print("Ingresa ID de Usuario: ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Apellido: ");
                String apellido = scanner.nextLine();
                System.out.print("Correo: ");
                String correo = scanner.nextLine();
                System.out.print("Tipo de Usuario (Estudiante/Profesor/Administrador): ");
                String tipo = scanner.nextLine();
                servicio.registrarUsuario(id, nombre, apellido, correo, tipo);
                System.out.println("Usuario registrado exitosamente.");
                break;
            case 2:
                List<usuario> usuarios = servicio.obtenerTodosUsuarios();
                System.out.println("\n--- LISTADO DE USUARIOS ---");
                if (usuarios.isEmpty()) {
                    System.out.println("Ningún usuario registrado.");
                } else {
                    for (usuario u : usuarios) {
                        System.out.printf("ID: %-4d | Nombre Completo: %-25s | Correo: %-20s | Tipo: %-15s%n",
                                u.getId(), u.getNombre() + " " + u.getApellido(), u.getCorreo(), u.getTipo());
                    }
                }
                break;
            case 3:
                System.out.print("Ingresa ID de Usuario a buscar: ");
                int idBuscar = Integer.parseInt(scanner.nextLine());
                usuario u = servicio.buscarUsuarioPorId(idBuscar);
                if (u != null) {
                    System.out.println("\nUsuario Encontrado:");
                    System.out.println("\nID: " + u.getId());
                    System.out.println("Nombre: " + u.getNombre() + " " + u.getApellido());
                    System.out.println("Correo: " + u.getCorreo());
                    System.out.println("Tipo: " + u.getTipo());
                } else {
                    System.out.println("El usuario no existe en la base de datos.");
                }
                break;
            default:
                System.out.println("Opción cancelada.");
        }
    }


    // SubMenu Prestamos / Devoluciones

    private void submenuPrestamos() {
        System.out.println("\n--- PRÉSTAMOS Y DEVOLUCIONES ---");
        System.out.println("1. Registrar Préstamo");
        System.out.println("2. Registrar Devolución");
        System.out.println("3. Ver Listado de Préstamos Activos (Lista Enlazada)");
        System.out.println("\nOpción: ");

        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1:
                System.out.print("Ingresa un ID único: ");
                String idP = scanner.nextLine();
                System.out.print("Ingresa ID del Libro: ");
                int idL = Integer.parseInt(scanner.nextLine());
                System.out.print("Ingresa ID del Usuario: ");
                int idU = Integer.parseInt(scanner.nextLine());

                String resultado = servicio.registrarPrestamo(idP, idL, idU);
                System.out.println(resultado);

                if (resultado.contains("no está disponible")) {
                    System.out.print("¿Deseas agregar a este usuario a la Cola de Espera para este libro?: ");
                    String respuesta = scanner.nextLine();
                    if (respuesta.equalsIgnoreCase("s")) {
                        String rCola = servicio.encolarUsuarioEnEspera(idL, idU);
                        System.out.println(rCola);
                    }
                }
                break;
            case 2:
                System.out.print("Id del Libro a Devolver: ");
                String idDevolver = scanner.nextLine();
                String rDevolucion = servicio.registrarDevolucion(idDevolver);
                System.out.println(rDevolucion);
                break;
            case 3:
                prestamo[] activos = servicio.obtenerPrestamosActivos();
                System.out.println("\n--- LISTADO DE PRÉSTAMOS ACTIVOS ---");
                if (activos.length == 0) {
                    System.out.println("No hay préstamos activos en este momento.");
                } else {
                    for (prestamo p : activos) {
                        System.out.printf("Op. ID: %-10s | Libro ID: %-5d | Usuario ID: %-5d | F. Salida: %s%n",
                                p.getIdP(), p.getRefLibro(), p.getRefUsuario(), p.getSalida());
                    }
                }
                break;
            default:
                System.out.println("Opción cancelada.");
        }
    }


    // Cola de espera

    private void verColasDeEspera() {
        System.out.print("Ingresa el ID del Libro ocupado para ver su fila de espera: ");
        int idLibro = Integer.parseInt(scanner.nextLine());

        libro l = servicio.buscarLibroId(idLibro);
        if (l == null) {
            System.out.println("El libro no existe.");
            return;
        }

        usuario[] esperando = servicio.obtenerColaEsperaDeLibro(idLibro);
        System.out.println("\n--- COLA DE ESPERA PARA EL LIBRO: '" + l.getTitulo() + "' ---");
        if (esperando.length == 0) {
            System.out.println("No hay usuarios en cola de espera para este libro.");
        } else {
            for (int i = 0; i < esperando.length; i++) {
                usuario u = esperando[i];
                System.out.printf("Posición %d: [ID: %d] %s %s%n",
                        (i + 1), u.getId(), u.getNombre(), u.getApellido());
            }
        }
    }


    // Relaciones

    private void submenuRelaciones() {
        System.out.println("\n--- SUBMENÚ: RELACIONES Y RECOMENDACIONES ---");
        System.out.println("1. Relacionar dos libros (Afinidad o Categoría)");
        System.out.println("2. Obtener Recomendaciones de un Libro");
        System.out.println("3. Visualizar Mapa Completo de Relaciones (Grafo)");
        System.out.print("\nOpción: ");

        int op = Integer.parseInt(scanner.nextLine());
        switch (op) {
            case 1:
                System.out.print("Ingresa el ID del primer Libro: ");
                int id1 = Integer.parseInt(scanner.nextLine());
                System.out.print("Ingresa el ID del segundo Libro con el que se relaciona: ");
                int id2 = Integer.parseInt(scanner.nextLine());
                if (servicio.relacionarLibros(id1, id2)) {
                    System.out.println("Relación bidireccional agregada con éxito al Grafo.");
                } else {
                    System.out.println("Asegúrate de que ambos IDs correspondan a libros registrados.");
                }
                break;
            case 2:
                System.out.print("Ingresa el ID del Libro de referencia: ");
                int idRef = Integer.parseInt(scanner.nextLine());
                libro ref = servicio.buscarLibroId(idRef);
                if (ref == null) {
                    System.out.println("El libro no existe.");
                    return;
                }
                List<libro> recomendados = servicio.obtenerRecomendaciones(idRef);
                System.out.println("\n--- RECOMENDACIONES BASADAS EN: '" + ref.getTitulo() + "' ---");
                if (recomendados.isEmpty()) {
                    System.out.println("No hay libros vinculados a este para generar recomendaciones.");
                } else {
                    for (libro l : recomendados) {
                        System.out.printf("'%s' (ID: %d | Categoría: %s)%n",
                                l.getTitulo(), l.getId(), l.getCategoria());
                    }
                }
                break;
            case 3:
                System.out.println("\n--- CONEXIONES DEL SISTEMA ---");
                System.out.print(servicio.mostrarMapaDeRelaciones());
                break;
            default:
                System.out.println("Opción cancelada.");
        }
    }


    // Historial

    private void mostrarHistorialAuditoria() {
        historial[] logs = servicio.obtenerHistorialAcciones();

        System.out.println("-HISTORIAL");
        System.out.println("_______________________________________________________\n\n");
        if (logs.length == 0) {
            System.out.println("No se han registrado acciones aún.");
        } else {
            for (historial h : logs) {
                System.out.printf("[%s] - Acción: %s (Log ID: %d)%n",
                        h.getEjecutado(), h.getAccion(), h.getIdH());
            }
        }
        System.out.println("=======================================================");
    }

    private void pausarPantalla() {
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
}