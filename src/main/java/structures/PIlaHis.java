package structures;

import model.historial;

public class PIlaHis {
    private static class Nodo {
        historial d1;
        Nodo siguiente;

        Nodo (historial d2) {
            this.d1 = d2;
            this.siguiente = null;
        }
    }

    private Nodo tope;
    private int tam;

    public  PIlaHis () {
        this.tope = null;
        this.tam = 0;
    }

    public void push (historial h) {
        Nodo nuevo = new Nodo(h);

        nuevo.siguiente = tope;
        tope = nuevo;
        tam++;
    }

    public historial pop () {
        if (vacia()) {
            return null;
        }

        historial d3 = tope.d1;
        tope = tope.siguiente;
        tam--;
        return d3;
    }

    public historial peek() {
        if (vacia()) {
            return null;
        }
        return tope.d1;
    }

    public boolean vacia () {
        return tope == null;
    }

    public int getTam() {
        return tam;
    }

    public historial[] Historial () {
        historial[] registro = new historial[tam];
        Nodo actual = tope;
        int i = 0;

        while (actual != null) {
            registro[i++] = actual.d1;
            actual = actual.siguiente;
        }
        return registro;
    }
 }
