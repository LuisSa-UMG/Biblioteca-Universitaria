package model;

import java.time.LocalDateTime;

public class prestamo {
    private String idP;
    private int refLibro;
    private int refUsuario;
    private LocalDateTime salida;
    private LocalDateTime devolucion;

    public prestamo (String IdP, int RefLibro, int RefUsuario, LocalDateTime Salida, LocalDateTime Devolucion) {
        this.idP = IdP;
        this.refLibro = RefLibro;
        this.refUsuario = RefUsuario;
        this.salida = Salida;
        this.devolucion = Devolucion;
    }

    public String getIdP () {
        return idP;
    }

    public void setIdP (String idP) {
        this.idP = idP;
    }

    public int getRefLibro () {
        return refLibro;
    }

    public void setRefLibro (int refLibro) {
        this.refLibro = refLibro;
    }

    public int getRefUsuario () {
        return refUsuario;
    }

    public void setRefUsuario (int refUsuario) {
        this.refUsuario = refUsuario;
    }

    public LocalDateTime getSalida () {
        return salida;
    }

    public void setSalida (LocalDateTime salida) {
        this.salida = salida;
    }

    public LocalDateTime getDevolucion () {
        return devolucion;
    }

    public void setDevolucion (LocalDateTime devolucion) {
        this.devolucion = devolucion;
    }
}
