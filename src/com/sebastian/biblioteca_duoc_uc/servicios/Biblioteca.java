package com.sebastian.biblioteca_duoc_uc.servicios;

import com.sebastian.biblioteca_duoc_uc.Excepciones.ArchivoNoEncontradoException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.ErrorLecturaArchivoException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.LibroNoEncontradoException;
import com.sebastian.biblioteca_duoc_uc.modelos.libros.Libro;
import com.sebastian.biblioteca_duoc_uc.modelos.clientes.Usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Biblioteca {
    private final ArrayList<Libro> listaLibros = new ArrayList<>();
    
    private final HashMap<String, Usuario> usuarios = new HashMap<>();
    
    // TreeSet para ordenar alfabeticamente    
    private final TreeSet<Libro> catalogoOrdenado = new TreeSet<>();
    private final TreeSet<Usuario> usuariosOrdenados = new TreeSet<>();
    
    //HashSet para colecciones únicas sin orden específico
    private final HashSet<Libro> librosUnicos = new HashSet<>();
    private final HashSet<Usuario> usuariosUnicos = new HashSet<>();


    public boolean registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getRut())) {
            System.out.println("Usuario ya registrado.");
            return false;
        }
        usuariosUnicos.add(usuario);  // Añade a usuarios unicos
        usuarios.put(usuario.getRut(), usuario); // Añade para acceso rápido por rut
        usuariosOrdenados.add(usuario); // Añade a usuarios ordenados
        return true;
    }

    public Libro buscarLibro(String titulo) {
        return listaLibros.stream()
                .filter(libro -> libro.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarUsuario(String rut) {
        return usuarios.get(rut);
    }

    public boolean prestarLibro(String rut, String titulo) {
        Usuario usuario = buscarUsuario(rut);
        Libro libro = buscarLibro(titulo);
        if (usuario == null || libro == null || !libro.estaDisponible()) return false;
        usuario.prestarLibro(libro);
        return true;
    }

    public boolean devolverLibro(String rut, String titulo) {
        Usuario usuario = buscarUsuario(rut);
        Libro libro = buscarLibro(titulo);
        if (usuario == null || libro == null || libro.estaDisponible()) return false;
        usuario.devolverLibro(libro);
        return true;
    }
    
    // TreeSet
    public void listarCatalogoOrdenado() {
        System.out.println("\n📚 Catálogo ordenado:");
        catalogoOrdenado.forEach(System.out::println);
    }

    public void listarUsuariosOrdenados() {
        System.out.println("\n👥 Usuarios ordenados:");
        usuariosOrdenados.forEach(System.out::println);
    }
    
    // HashSet
    public void mostrarLibrosUnicos() {
        System.out.println("\n📦 Libros únicos (sin orden):");
        if (librosUnicos.isEmpty()) {
            System.out.println("No hay libros en el conjunto único.");
            return;
        }
        for (Libro libro : librosUnicos) {
            System.out.println(libro);
        }
    }
    
    public void mostrarUsuariosUnicos() {
        System.out.println("\n👥 Usuarios únicos (sin orden):");
        if (usuariosUnicos.isEmpty()) {
            System.out.println("No hay usuarios en el conjunto único.");
            return;
        }
        for (Usuario usuario : usuariosUnicos) {
            System.out.println(usuario);
        }
    }

    
    public void cargarLibrosDesdeCSV(String rutaArchivo) throws ArchivoNoEncontradoException, ErrorLecturaArchivoException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // Divide la línea por comas
                if (datos.length == 4) { // ID, Titulo, Autor, Año
                    try {
                        String id = datos[0].trim();
                        String titulo = datos[1].trim();
                        String autor = datos[2].trim();
                        int ano = Integer.parseInt(datos[3].trim()); // Año a entero
                        
                        Libro libro = new Libro(id, titulo, autor, ano);
                        
                        listaLibros.add(libro); // Añade a lista libros
                        librosUnicos.add(libro); // Añade a libros unicos
                        catalogoOrdenado.add(libro); // Añade al catalogo ordenado alfabeticamente
                        
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: Línea con formato de año incorrecto saltada: " + linea + " | Error: " + e.getMessage());
                    }
                } else {
                    System.err.println("Advertencia: Línea con formato incorrecto saltada: " + linea);
                }
            }
            System.out.println("Libros cargados exitosamente desde " + rutaArchivo);
        } catch (java.io.FileNotFoundException e) {
            throw new ArchivoNoEncontradoException("El archivo de libros CSV no se encontró en la ruta: " + rutaArchivo);
        } catch (IOException e) {
            throw new ErrorLecturaArchivoException("Error al leer el archivo CSV de libros: " + e.getMessage(), e);
        }
    }
    
    public String consultarDisponibilidadLibro(String idLibro) throws LibroNoEncontradoException {
    Libro libro = listaLibros.stream()
            .filter(l -> l.getIdLibro().equalsIgnoreCase(idLibro))
            .findFirst()
            .orElseThrow(() -> new LibroNoEncontradoException("No se encontró el libro con ID: " + idLibro));

    StringBuilder sb = new StringBuilder("📘 " + libro + "\n");

    if (!libro.estaDisponible()) {
        String rut = libro.getIdUsuarioPrestado();
        Usuario u = usuarios.get(rut);
        String prestadoPor = (u != null) ? u.getNombre() : "ID " + rut + " (usuario no encontrado)";
        sb.append("Estado: ¡El libro se encuentra PRESTADO por ").append(prestadoPor).append("!");
    } else {
        sb.append("Estado: ¡El libro está DISPONIBLE!");
    }

    return sb.toString();
}
        
}