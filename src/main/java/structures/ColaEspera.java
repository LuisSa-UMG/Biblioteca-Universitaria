package structures;

import model.usuario;

public class ColaEspera {
    private static class Nodo {
        usuario d1;
        Nodo siguiente;

        Nodo(usuario d2) {
            this.d1 = d2;
            this.siguiente = null;
        }
    }

    private Nodo frente;
    private Nodo fin;
    private int tam;

    public ColaEspera() {
        this.frente = null;
        this.fin = null;
        this.tam = 0;
    }

    public void enqueue(usuario u) {
        Nodo nuevo = new Nodo(u);
        if (vacia()) {
            frente = nuevo;
        } else {
            fin.siguiente = nuevo;
        }
        fin = nuevo;
        tam++;
    }

    public usuario dequeue() {
        if (vacia()) {
            return null;
        }
        usuario u = frente.d1;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tam--;
        return u;
    }

    public usuario peek() {
        if (vacia()) {
            return null;
        }
        return frente.d1;
    }

    public boolean vacia() {
        return frente == null;
    }

    public int getTam() {
        return tam;
    }

    public usuario[] UserCola() {
        usuario[] lista = new usuario[tam];
        Nodo actual = frente;
        int i = 0;
        while (actual != null) {
            lista[i++] = actual.d1;
            actual = actual.siguiente;
        }
        return lista;
    }
}