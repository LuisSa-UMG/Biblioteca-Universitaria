package model;

public class libro {
    private int id ;
    private String isbn;
    private String titulo;
    private String categoria;
    private boolean disponible;

    public libro (int Id, String Isbn, String Titulo, String Categoria, boolean Disponible) {
        this.id = Id;
        this.isbn = Isbn;
        this.titulo = Titulo;
        this.categoria = Categoria;
        this.disponible = Disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn () {
        return isbn;
    }

    public void setIsbn (String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo () {
        return titulo;
    }

    public void setTitulo (String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria () {
        return categoria;
    }

    public void setCategoria (String categoria) {
        this.categoria = categoria;
    }

    public boolean getDisponible () {
        return disponible;
    }

    public void setDisponible (boolean disponible) {
        this.disponible = disponible;
    }
}
