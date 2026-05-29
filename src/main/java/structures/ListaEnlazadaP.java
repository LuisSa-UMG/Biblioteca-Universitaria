package structures;

import model.prestamo;

public class ListaEnlazadaP {
    private static class Nodo {
        prestamo d1;
        Nodo siguiente;

        Nodo (prestamo d2) {
            this.d1 = d2;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;
    private int tam;

    public ListaEnlazadaP () {
        this.cabeza = null;
        this.tam = 0;
    }

    public void insertar (prestamo p3) {
        Nodo nuevo = new Nodo(p3);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tam++;
    }

    public prestamo buscar (String IdP) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.d1.getIdP().equals(IdP)) {
                return actual.d1;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean eliminar (String IdP) {
        if (cabeza == null) return false;

        if (cabeza.d1.getIdP().equals(IdP)) {
            cabeza = cabeza.siguiente;

            tam--;
            return true;
        }

        Nodo anterior = cabeza;
        Nodo actual = cabeza.siguiente;

        while (actual != null) {
            if (actual.d1.getIdP().equals(IdP)) {
                anterior.siguiente = actual.siguiente;
                tam--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return  false;
    }

    public prestamo[] todosP () {
        prestamo[] Lista = new prestamo[tam];

        Nodo actual = cabeza;
        int i = 0;

        while (actual != null) {
            Lista [i++] = actual.d1;
            actual = actual.siguiente;
        }
        return Lista;
    }

    public int getTam() {
        return tam;
    }

    public boolean vacia () {
        return cabeza == null;
    }
}
