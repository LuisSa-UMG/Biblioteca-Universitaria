package model;

import java.time.LocalDateTime;

public class historial {
    private int idH;
    private String accion;
    private LocalDateTime ejecutado;

    public historial (int IdH, String Accion, LocalDateTime Ejecutado) {
        this.idH = IdH;
        this.accion = Accion;
        this.ejecutado = Ejecutado;
    }

    public int getIdH() {
        return idH;
    }

    public void setIdH (int idH) {
        this.idH = idH;
    }

    public String getAccion () {
        return accion;
    }

    public void setAccion (String accion) {
        this.accion = accion;
    }

    public  LocalDateTime getEjecutado () {
        return ejecutado;
    }

    public void setEjecutado (LocalDateTime ejecutado) {
        this.ejecutado = ejecutado;
    }
}
