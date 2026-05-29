package structures;

import model.libro;
import java.util.ArrayList;
import java.util.List;

public class ArbolLibrosBST {

    private static class NodoArbol {
        libro d1;
        NodoArbol izquierdo;
        NodoArbol derecho;

        NodoArbol(libro dato) {
            this.d1 = dato;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private NodoArbol raiz;

    public ArbolLibrosBST() {
        this.raiz = null;
    }

    public void insertar(libro nLibro) {
        raiz = insertarRecursivo(raiz, nLibro);
    }

    private NodoArbol insertarRecursivo(NodoArbol actual, libro nuevoLibro) {
        if (actual == null) {
            return new NodoArbol(nuevoLibro);
        }

        if (nuevoLibro.getId() < actual.d1.getId()) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, nuevoLibro);
        } else if (nuevoLibro.getId() > actual.d1.getId()) {
            actual.derecho = insertarRecursivo(actual.derecho, nuevoLibro);
        }
        return actual;
    }

    public libro buscarPorId(int id) {
        return buscarPorIdRecursivo(raiz, id);
    }

    private libro buscarPorIdRecursivo(NodoArbol actual, int id) {
        if (actual == null || actual.d1.getId() == id) {
            return actual != null ? actual.d1 : null;
        }

        if (id < actual.d1.getId()) {
            return buscarPorIdRecursivo(actual.izquierdo, id);
        }
        return buscarPorIdRecursivo(actual.derecho, id);
    }

    public libro buscarPorIsbn(String isbn) {
        return buscarPorIsbnRecursivo(raiz, isbn);
    }

    private libro buscarPorIsbnRecursivo(NodoArbol actual, String isbn) {
        if (actual == null) return null;
        if (actual.d1.getIsbn().equalsIgnoreCase(isbn)) return actual.d1;

        libro encontradoIzq = buscarPorIsbnRecursivo(actual.izquierdo, isbn);
        if (encontradoIzq != null) return encontradoIzq;

        return buscarPorIsbnRecursivo(actual.derecho, isbn);
    }

    public libro buscarPorTitulo(String titulo) {
        return buscarPorTituloRecursivo(raiz, titulo);
    }

    private libro buscarPorTituloRecursivo(NodoArbol actual, String titulo) {
        if (actual == null) return null;
        if (actual.d1.getTitulo().equalsIgnoreCase(titulo)) return actual.d1;

        libro encontradoIzq = buscarPorTituloRecursivo(actual.izquierdo, titulo);
        if (encontradoIzq != null) return encontradoIzq;

        return buscarPorTituloRecursivo(actual.derecho, titulo);
    }

    public void eliminar(int id) {
        raiz = eliminarRecursivo(raiz, id);
    }

    private NodoArbol eliminarRecursivo(NodoArbol actual, int id) {
        if (actual == null) return null;

        if (id < actual.d1.getId()) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, id);
        } else if (id > actual.d1.getId()) {
            actual.derecho = eliminarRecursivo(actual.derecho, id);
        } else {
            if (actual.izquierdo == null) return actual.derecho;
            if (actual.derecho == null) return actual.izquierdo;

            actual.d1 = encontrarMinimo(actual.derecho);
            actual.derecho = eliminarRecursivo(actual.derecho, actual.d1.getId());
        }
        return actual;
    }

    private libro encontrarMinimo(NodoArbol actual) {
        libro minVal = actual.d1;
        while (actual.izquierdo != null) {
            minVal = actual.izquierdo.d1;
            actual = actual.izquierdo;
        }
        return minVal;
    }

    public List<libro> recorridoInorden() {
        List<libro> lista = new ArrayList<>();
        inordenRecursivo(raiz, lista);
        return lista;
    }

    private void inordenRecursivo(NodoArbol actual, List<libro> lista) {
        if (actual != null) {
            inordenRecursivo(actual.izquierdo, lista);
            lista.add(actual.d1);
            inordenRecursivo(actual.derecho, lista);
        }
    }

    public List<libro> recorridoPreorden() {
        List<libro> lista = new ArrayList<>();
        preordenRecursivo(raiz, lista);
        return lista;
    }

    private void preordenRecursivo(NodoArbol actual, List<libro> lista) {
        if (actual != null) {
            lista.add(actual.d1);
            preordenRecursivo(actual.izquierdo, lista);
            preordenRecursivo(actual.derecho, lista);
        }
    }

    public List<libro> recorridoPostorden() {
        List<libro> lista = new ArrayList<>();
        postordenRecursivo(raiz, lista);
        return lista;
    }

    private void postordenRecursivo(NodoArbol actual, List<libro> lista) {
        if (actual != null) {
            postordenRecursivo(actual.izquierdo, lista);
            postordenRecursivo(actual.derecho, lista);
            lista.add(actual.d1);
        }
    }
}