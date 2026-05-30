package service;

import model.libro;
import model.usuario;
import model.prestamo;
import model.historial;
import structures.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BibliotecaS {
    private final ArbolLibrosBST arbolLibros;
    private final MapaUsuarios mapaUsuarios;
    private final ListaEnlazadaP listaPrestamos;
    private final PIlaHis pilaHistorial;
    private final GrafosRelacionesLibros grafoRelaciones;

    private final Map<Integer, ColaEspera> colasEsperaLibros;

    private int contadorHistorial;

    public BibliotecaS() {
        this.arbolLibros = new ArbolLibrosBST();
        this.mapaUsuarios = new MapaUsuarios();
        this.listaPrestamos = new ListaEnlazadaP();
        this.pilaHistorial = new PIlaHis();
        this.grafoRelaciones = new GrafosRelacionesLibros();
        this.colasEsperaLibros = new HashMap<>();
        this.contadorHistorial = 1;
    }

    //Gestion de los libros

    public void registrarLibro(int id, String isbn, String titulo, String categoria) {
        libro nuevoLibro = new libro(id, isbn, titulo, categoria, true);

        arbolLibros.insertar(nuevoLibro);
        grafoRelaciones.agregarVertice(nuevoLibro);

        colasEsperaLibros.put(id, new ColaEspera());

        registrarAccion("Libro registrado: '" + titulo + "' (ID: " + id + ")");
    }

    public List<libro> LibrosOrdenadosId() {
        return arbolLibros.recorridoInorden();
    }

    public List<libro> LibrosPreorden() {
        return arbolLibros.recorridoPreorden();
    }

    public List<libro> LibrosPostorden() {
        return arbolLibros.recorridoPostorden();
    }

    public libro buscarLibroId(int id) {
        return arbolLibros.buscarPorId(id);
    }

    public libro buscarLibroIsbn(String isbn) {
        return arbolLibros.buscarPorIsbn(isbn);
    }

    public libro buscarLibroTitulo(String titulo) {
        return arbolLibros.buscarPorTitulo(titulo);
    }

    public boolean eliminarLibro(int id) {
        libro l = arbolLibros.buscarPorId(id);
        if (l != null) {
            arbolLibros.eliminar(id);
            grafoRelaciones.eliminarLibro(id);
            colasEsperaLibros.remove(id);
            registrarAccion("Libro eliminado: '" + l.getTitulo() + "' (ID: " + id + ")");
            return true;
        }
        return false;
    }

    // Gestion de usuarios

    public void registrarUsuario(int id, String nombre, String apellido, String correo, String tipo) {
        usuario nuevoUsuario = new usuario(id, nombre, apellido, correo, tipo);
        mapaUsuarios.put(id, nuevoUsuario);
        registrarAccion("Usuario registrado: " + nombre + " " + apellido + " (ID: " + id + ")");
    }

    public usuario buscarUsuarioPorId(int id) {
        return mapaUsuarios.get(id);
    }

    public List<usuario> obtenerTodosUsuarios() {
        return mapaUsuarios.obtenerTodos();
    }

    // Prestamos / devoluciones

    public String registrarPrestamo(String idP, int idLibro, int idUsuario) {
        libro l = arbolLibros.buscarPorId(idLibro);
        usuario u = mapaUsuarios.get(idUsuario);

        if (l == null) return "Error: El libro con ID " + idLibro + " no existe.";
        if (u == null) return "Error: El usuario con ID " + idUsuario + " no existe.";

        if (l.getDisponible()) {
            l.setDisponible(false);

            LocalDateTime fechaSalida = LocalDateTime.now();
            LocalDateTime fechaDevolucion = fechaSalida.plusDays(7);

            prestamo nuevoPrestamo = new prestamo(idP, idLibro, idUsuario, fechaSalida, fechaDevolucion);
            listaPrestamos.insertar(nuevoPrestamo);

            registrarAccion("Préstamo exitoso de '" + l.getTitulo() + "' a " + u.getNombre() + " " + u.getApellido());
            return "Préstamo registrado con éxito. ID Operación: " + idP + ". Devolución: " + fechaDevolucion;
        } else {
            return "El libro '" + l.getTitulo() + "' no está disponible. Puedes agregarlo a la cola de espera.";
        }
    }

    public String registrarDevolucion(String idP) {
        prestamo p = listaPrestamos.buscar(idP);
        if (p == null) {
            return "Error: No se encontró ningún préstamo activo con el ID: " + idP;
        }

        libro l = arbolLibros.buscarPorId(p.getRefLibro());
        usuario uDevuelve = mapaUsuarios.get(p.getRefUsuario());

        listaPrestamos.eliminar(idP);
        registrarAccion("Devolución del libro '" + l.getTitulo() + "' por " + uDevuelve.getNombre());

        ColaEspera cola = colasEsperaLibros.get(l.getId());

        if (cola != null && !cola.vacia()) {
            usuario siguienteUsuario = cola.dequeue();

            String nuevoIdP = "AUTO-" + l.getId() + "-" + siguienteUsuario.getId();
            LocalDateTime fechaSalida = LocalDateTime.now();
            LocalDateTime fechaDevolucion = fechaSalida.plusDays(7);

            prestamo autoPrestamo = new prestamo(nuevoIdP, l.getId(), siguienteUsuario.getId(), fechaSalida, fechaDevolucion);
            listaPrestamos.insertar(autoPrestamo);

            l.setDisponible(false);

            registrarAccion("Asignación automática de '" + l.getTitulo() + "' a " + siguienteUsuario.getNombre() + " (Cola de Espera)");

            return "Devolución procesada. El libro '" + l.getTitulo() +
                    "' ha sido asignado automáticamente al siguiente en cola de espera: " +
                    siguienteUsuario.getNombre() + " " + siguienteUsuario.getApellido() +
                    " (Nuevo ID préstamo: " + nuevoIdP + ")";
        } else {
            l.setDisponible(true);
            return "Devolución procesada con éxito. El libro '" + l.getTitulo() + "' ahora está disponible.";
        }
    }

    public prestamo[] obtenerPrestamosActivos() {
        return listaPrestamos.todosP();
    }

    // Cola Espera

    public String encolarUsuarioEnEspera(int idLibro, int idUsuario) {
        libro l = arbolLibros.buscarPorId(idLibro);
        usuario u = mapaUsuarios.get(idUsuario);

        if (l == null) return "El libro no existe.";
        if (u == null) return "El usuario no existe.";
        if (l.getDisponible()) return "El libro está disponible, realiza un préstamo directo.";

        ColaEspera cola = colasEsperaLibros.get(idLibro);
        if (cola == null) {
            cola = new ColaEspera();
            colasEsperaLibros.put(idLibro, cola);
        }

        cola.enqueue(u);
        registrarAccion("Usuario " + u.getNombre() + " agregado a la cola de espera de '" + l.getTitulo() + "'");

        return "👤 " + u.getNombre() + " en cola para '" + l.getTitulo() + "'. Posición: " + cola.getTam();
    }

    public usuario[] obtenerColaEsperaDeLibro(int idLibro) {
        ColaEspera cola = colasEsperaLibros.get(idLibro);
        if (cola == null) return new usuario[0];

        return cola.UserCola();
    }

    // Auditoria

    private void registrarAccion(String descripcionAccion) {
        historial nuevoHistorial = new historial(contadorHistorial++, descripcionAccion, LocalDateTime.now());
        pilaHistorial.push(nuevoHistorial);
    }

    public historial[] obtenerHistorialAcciones() {
        return pilaHistorial.Historial();
    }

    // REcomendaciones

    public boolean relacionarLibros(int idLibro1, int idLibro2) {
        libro l1 = arbolLibros.buscarPorId(idLibro1);
        libro l2 = arbolLibros.buscarPorId(idLibro2);

        if (l1 != null && l2 != null) {
            grafoRelaciones.agregarRelacion(idLibro1, idLibro2);
            registrarAccion("Relacionados los libros: '" + l1.getTitulo() + "' y '" + l2.getTitulo() + "'");
            return true;
        }
        return false;
    }

    public List<libro> obtenerRecomendaciones(int idLibro) {
        // Adaptado: uso de .recomendados() de GrafosRelacionesLibros
        return grafoRelaciones.recomendados(idLibro);
    }

    public String mostrarMapaDeRelaciones() {
        return grafoRelaciones.mostrarGrafo();
    }
}