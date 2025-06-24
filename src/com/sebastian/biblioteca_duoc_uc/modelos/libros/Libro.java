package com.sebastian.biblioteca_duoc_uc.modelos.libros;

// Implementar comparable para usar TreeSet
public class Libro implements Comparable<Libro> {
    
    // ATRIBUTOS
    private String idLibro;
    private String titulo;
    private String autor;
    private int anoPublicacion;
    private boolean disponible; 
    private String idUsuarioPrestado;

    // CONSTRUCTOR
    public Libro(String idLibro, String titulo, String autor, int anoPublicacion) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.disponible = true;
        this.idUsuarioPrestado = null;
    }

    // GETTERS
    public String getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    public boolean estaDisponible() {
        return disponible;
    }
    
    public String getIdUsuarioPrestado() {
        return idUsuarioPrestado;
    }
    
    // SETTERS
    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAnoPublicacion(int anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }
    
    public void setIdUsuarioPrestado(String rut) {
        this.idUsuarioPrestado = rut;
    }
    
    // MÉTODOS
    public void prestar(String rut) {
        this.disponible = false; // deja de estar disponible al prestarlo
        this.idUsuarioPrestado = rut;
    }
    
    public void devolver() {
        this.disponible = true; // vuelve a estar disponible cuando se devuelve
        this.idUsuarioPrestado = null;
    }
    
    
    @Override
    public String toString() {
        return String.format("%s (%d) - %s [%s]", titulo, anoPublicacion, autor, disponible ? "Disponible" : "Prestado");
    }

    
    // Comparación para ordenar por titulo
    @Override
    public int compareTo(Libro otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }
    
    // Comparación para evitar duplicados de Libros (idLibro para ser exactos)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // indica que son exactamente el mismo objeto
        if (!(o instanceof Libro)) return false; // si no es un Libro, no compara
        return idLibro.equals(((Libro) o).idLibro); 
    }
    
    // Ayuda en la búsqueda de idLobro para realizar la comparación más rápido
    @Override
    public int hashCode() {
        return idLibro.hashCode();
    }
    
}