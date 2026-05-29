package structures;

import model.libro;
import java.util.ArrayList;
import java.util.List;

public class GrafosRelacionesLibros {

    private static class Arista {
        Vertice destino;
        Arista siguiente;

        Arista(Vertice destino) {
            this.destino = destino;
            this.siguiente = null;
        }
    }

    private static class Vertice {
        libro data;
        Arista primerAdyacente;
        Vertice siguienteVertice;

        Vertice(libro data) {
            this.data = data;
            this.primerAdyacente = null;
            this.siguienteVertice = null;
        }
    }

    private Vertice prVertice;
    private int tVertices;

    public GrafosRelacionesLibros() {
        this.prVertice = null;
        this.tVertices = 0;
    }

    public void agregarVertice(libro l) {
        if (buscarVertice(l.getId()) != null) return; // Evita duplicar el vértice

        Vertice nuevo = new Vertice(l);
        nuevo.siguienteVertice = prVertice;
        prVertice = nuevo;
        tVertices++;
    }

    private Vertice buscarVertice(int id) {
        Vertice actual = prVertice;
        while (actual != null) {
            if (actual.data.getId() == id) {
                return actual;
            }
            actual = actual.siguienteVertice;
        }
        return null;
    }

    public void agregarRelacion(int idLibro1, int idLibro2) {
        Vertice v1 = buscarVertice(idLibro1);
        Vertice v2 = buscarVertice(idLibro2);

        if (v1 == null || v2 == null) return;

        if (!existeRelacionDirecta(v1, v2)) {
            Arista nuevaArista1 = new Arista(v2);
            nuevaArista1.siguiente = v1.primerAdyacente;
            v1.primerAdyacente = nuevaArista1;
        }

        if (!existeRelacionDirecta(v2, v1)) {
            Arista nuevaArista2 = new Arista(v1);
            nuevaArista2.siguiente = v2.primerAdyacente;
            v2.primerAdyacente = nuevaArista2;
        }
    }

    private boolean existeRelacionDirecta(Vertice origen, Vertice destino) {
        Arista actual = origen.primerAdyacente;
        while (actual != null) {
            if (actual.destino.data.getId() == destino.data.getId()) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public List<libro> recomendados(int idLibro) {
        List<libro> recomendado = new ArrayList<>();
        Vertice v = buscarVertice(idLibro);

        if (v == null) return recomendado;

        Arista actual = v.primerAdyacente;
        while (actual != null) {
            recomendado.add(actual.destino.data);
            actual = actual.siguiente;
        }
        return recomendado;
    }

    public void eliminarLibro(int idLibro) {
        Vertice v = buscarVertice(idLibro);
        if (v == null) return;

        Vertice actualV = prVertice;
        while (actualV != null) {
            eliminarArista(actualV, idLibro);
            actualV = actualV.siguienteVertice;
        }

        if (prVertice.data.getId() == idLibro) {
            prVertice = prVertice.siguienteVertice;
        } else {
            Vertice anteriorV = prVertice;
            actualV = prVertice.siguienteVertice;
            while (actualV != null) {
                if (actualV.data.getId() == idLibro) {
                    anteriorV.siguienteVertice = actualV.siguienteVertice;
                    break;
                }
                anteriorV = actualV;
                actualV = actualV.siguienteVertice;
            }
        }
        tVertices--;
    }

    private void eliminarArista(Vertice origen, int idDestino) {
        Arista actual = origen.primerAdyacente;
        if (actual == null) return;

        if (actual.destino.data.getId() == idDestino) {
            origen.primerAdyacente = actual.siguiente;
            return;
        }

        Arista anterior = actual;
        actual = actual.siguiente;
        while (actual != null) {
            if (actual.destino.data.getId() == idDestino) {
                anterior.siguiente = actual.siguiente;
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    public String mostrarGrafo() {
        StringBuilder sb = new StringBuilder();
        Vertice actual = prVertice;
        while (actual != null) {
            sb.append("[").append(actual.data.getTitulo()).append("] -> Conectado con: ");
            Arista arista = actual.primerAdyacente;
            if (arista == null) {
                sb.append("Ninguno");
            }
            while (arista != null) {
                sb.append("{").append(arista.destino.data.getTitulo()).append("} ");
                arista = arista.siguiente;
            }
            sb.append("\n");
            actual = actual.siguienteVertice;
        }
        return sb.toString();
    }
}