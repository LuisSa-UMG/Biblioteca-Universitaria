package model;

public class usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String tipo;

    public usuario (int Id, String Nombre, String Apellido, String Correo, String Tipo) {
        this.id = Id;
        this.nombre = Nombre;
        this.apellido = Apellido;
        this.correo = Correo;
        this.tipo = Tipo;
    }

    //Tipo se usara para definir que usuario es; estudiante, profesor o adiministrador

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public String getApellido () {
        return apellido;
    }

    public void setApellido (String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo () {
        return correo;
    }

    public void  setCorreo (String correo) {
        this.correo = correo;
    }

    public String getTipo () {
        return tipo;
    }

    public void setTipo (String tipo) {
        this.tipo = tipo;
    }
}
