package structures;

import model.usuario;
import java.util.ArrayList;
import java.util.List;

public class MapaUsuarios {
    private static class NodoHash {
        int clave;
        usuario valor;
        NodoHash siguiente;

        NodoHash(int clave, usuario valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private NodoHash[] tabla;
    private final int CAP = 31;
    private int tam;

    public MapaUsuarios() {
        this.tabla = new NodoHash[CAP];
        this.tam = 0;
    }

    private int obtenerHash(int clave) {
        return Math.abs(clave) % CAP;
    }

    public void put(int clave, usuario valor) {
        int indice = obtenerHash(clave);
        NodoHash cabeza = tabla[indice];

        NodoHash actual = cabeza;
        while (actual != null) {
            if (actual.clave == clave) {
                actual.valor = valor;
                return;
            }
            actual = actual.siguiente;
        }

        NodoHash nuevo = new NodoHash(clave, valor);
        nuevo.siguiente = tabla[indice];
        tabla[indice] = nuevo;
        tam++;
    }

    public usuario get(int clave) {
        int indice = obtenerHash(clave);
        NodoHash actual = tabla[indice];

        while (actual != null) {
            if (actual.clave == clave) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean remove(int clave) {
        int indice = obtenerHash(clave);
        NodoHash actual = tabla[indice];
        NodoHash anterior = null;

        while (actual != null) {
            if (actual.clave == clave) {
                if (anterior == null) {
                    tabla[indice] = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tam--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    public int getTam() {
        return tam;
    }

    public List<usuario> obtenerTodos() {
        List<usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < CAP; i++) {
            NodoHash actual = tabla[i];
            while (actual != null) {
                usuarios.add(actual.valor);
                actual = actual.siguiente;
            }
        }
        return usuarios;
    }
}