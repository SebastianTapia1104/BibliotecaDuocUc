package com.sebastian.biblioteca_duoc_uc;

import com.sebastian.biblioteca_duoc_uc.Excepciones.ArchivoNoEncontradoException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.EntradaInvalidaException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.ErrorLecturaArchivoException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.LibroNoEncontradoException;
// import com.sebastian.biblioteca_duoc_uc.Excepciones.LibroPrestadoException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.RutInvalidoException;

//import com.sebastian.biblioteca_duoc_uc.modelos.libros.Libro;
import com.sebastian.biblioteca_duoc_uc.modelos.clientes.Usuario;
import com.sebastian.biblioteca_duoc_uc.servicios.Biblioteca;

import java.util.Scanner;

// El main solo tendrá el menú principal y captura los datos ingresados por el usuario como el rut, idLibro, titulos
public class Main {
    
        private static final String RUTA_PRESTAMOS = "src/com/sebastian/biblioteca_duoc_uc/resources/prestamos.txt"; // Ruta del archivo de registro de préstamos
        private static final String RUTA_LIBROS_CSV = "src/com/sebastian/biblioteca_duoc_uc/resources/libros.csv"; // Ruta del archivo CSV de libros
    
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner sc = new Scanner(System.in);
        int opcion;

        try { // Cargar libros
            biblioteca.cargarLibrosDesdeCSV(RUTA_LIBROS_CSV);
        } catch (ArchivoNoEncontradoException | ErrorLecturaArchivoException e) { // No encuentra libros
            System.err.println("Error crítico al cargar libros: " + e.getMessage());
            System.err.println("El programa se cerrará.");
            return; // Salir del programa si no se pueden cargar los libros
        }
        
        do {
            System.out.println("\n--- Bienvenido a la Biblioteca DUOC UC ---");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Consultar disponibilidad de libro");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Listar libros ordenados alfabéticamente");
            System.out.println("6. Listar usuarios ordenados alfabéticamente");
            System.out.println("7. Mostrar libros únicos, sin duplicado.");
            System.out.println("8. Mostrar usuarios únicos, sin duplicado.");
            System.out.println("9. Salir");
            
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Validador.leerEntero();

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese RUT (sin dígito verificador): ");
                        String rut = sc.nextLine();
                        try {
                            Validador.validarRut(rut);  // lanza excepción si es inválido
                        } catch (RutInvalidoException e) {
                            System.out.println("❌ " + e.getMessage());
                            continue; 
                        }
                        System.out.print("Ingrese nombre del usuario: ");
                        String nombre = sc.nextLine();
                        if (!Validador.esNombreValido(nombre)) {
                            System.out.println("Nombre inválido, debe tener mínimo 3 letras.");
                            break;
                        }
                        Usuario usuario = new Usuario(nombre, rut);
                        if(biblioteca.registrarUsuario(usuario)) {
                            System.out.println("Usuario registrado exitosamente.");
                        } else {
                            System.out.println("Rut ingresado ya está registrado.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese ID del libro: ");
                        String idLibro = sc.nextLine();
                        try {
                            String info = biblioteca.consultarDisponibilidadLibro(idLibro);
                            System.out.println(info);
                        } catch (LibroNoEncontradoException e) {
                            System.out.println("x" + e.getMessage());
                        }
                    }
                    case 3 -> {
                        System.out.print("Ingrese RUT usuario: ");
                        String rut = sc.nextLine();
                        System.out.print("Ingrese título del libro: ");
                        String titulo = sc.nextLine();
                        boolean exito = biblioteca.prestarLibro(rut, titulo);
                        System.out.println(exito ? "Libro prestado." : "No se pudo prestar el libro.");
                    }
                    case 4 -> {
                        System.out.print("Ingrese RUT usuario: ");
                        String rut = sc.nextLine();
                        System.out.print("Ingrese título del libro: ");
                        String titulo = sc.nextLine();
                        boolean exito = biblioteca.devolverLibro(rut, titulo);
                        System.out.println(exito ? "Libro devuelto" : "No se pudo devolver el libro");
                    }
                    case 5 -> biblioteca.listarCatalogoOrdenado();
                    case 6 -> biblioteca.listarUsuariosOrdenados();
                    case 7 -> biblioteca.mostrarLibrosUnicos();
                    case 8 -> biblioteca.mostrarUsuariosUnicos();
                    case 9 -> System.out.println("👋 Cerrando sistema...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (EntradaInvalidaException  e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número: " + e.getMessage());
                opcion = 0; // Bucle
            }

        } while (opcion != 9);
    }
}