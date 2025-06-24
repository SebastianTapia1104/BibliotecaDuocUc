package com.sebastian.biblioteca_duoc_uc.modelos.clientes;

import com.sebastian.biblioteca_duoc_uc.modelos.libros.Libro;

import java.util.ArrayList;

// Implementar comparable para usar TreeSet
public class Usuario implements Comparable<Usuario> {
    
    // ATRIBUTOS
    private String nombre;
    private String rut; // sin digito
    private ArrayList<Libro> librosPrestados;
    
    //CONSTRUCTOR
    public Usuario(String nombre, String rut) {
        this.nombre = nombre;
        this.rut = rut;
        this.librosPrestados = new ArrayList<>();
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getRut() {
        return rut;
    }

    public ArrayList<Libro> getLibrosPrestados() {
        return librosPrestados;
    }
    
    
    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    
    // MÉTODOS
    public void prestarLibro(Libro libro) {
        librosPrestados.add(libro);
        libro.prestar(this.rut); // Asocia el préstamo al rut del usuario
    }
    
    public void devolverLibro(Libro libro) {
        librosPrestados.remove(libro);
        libro.devolver(); // Libera el libro
    }
    
    @Override
    public String toString() {
        return String.format("%s (RUT: %s) - Libros Prestados: %d", nombre, rut, librosPrestados.size());
    }
    
    // Comparación para ordenar por nombre (de usuarios)
    @Override
    public int compareTo(Usuario otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        return rut.equals(((Usuario) o).rut);
    }

    @Override
    public int hashCode() {
        return rut.hashCode();
    }
    
}