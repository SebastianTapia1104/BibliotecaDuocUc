package com.sebastian.biblioteca_duoc_uc;

// import com.sebastian.biblioteca_duoc_uc.Excepciones.ArchivoNoEncontradoException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.EntradaInvalidaException;
// import com.sebastian.biblioteca_duoc_uc.Excepciones.ErrorLecturaArchivoException;
// import com.sebastian.biblioteca_duoc_uc.Excepciones.LibroNoEncontradoException;
// import com.sebastian.biblioteca_duoc_uc.Excepciones.LibroPrestadoException;
import com.sebastian.biblioteca_duoc_uc.Excepciones.RutInvalidoException;

import java.util.InputMismatchException;

import java.util.Scanner;

// Clase para dejar validaciones, para que el usuario ingrese lo que se pide
public class Validador {

    // Indica que para el rut, aunque este como string, se debe ingresar 8 digitos numericos
    public static boolean esRutValido(String rut) {
        return rut != null && rut.matches("\\d{8}");
    }
    
    // Creación de una excepción personalizada, usando lo anterior
    public static void validarRut(String rut) throws RutInvalidoException {
        if (!esRutValido(rut)) {
            throw new RutInvalidoException("El RUT debe contener exactamente 8 dígitos numéricos.");
        }
    }

    // Indica que los nombres ingresados deben tener mínimo 3 letras y permite mayusculas, tildes y la ñ
    public static boolean esNombreValido(String nombre) {
        return nombre != null && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,}");
    }

    // Indica que el idLibro debe tener digitos numericos de minimo 10 a maximo 13 
    public static boolean esIdLibroValido(String idLibro) {
        return idLibro != null && idLibro.matches("\\d{10}|\\d{13}");
    }

    // Indica que el titulo no puede estar vacío
    public static boolean esTituloValido(String titulo) {
        return titulo != null && !titulo.trim().isEmpty();
    }

    // Indica que se deben ingresar digitos numericos, enteros positivos
    public static boolean esNumero(String entrada) {
        return entrada != null && entrada.matches("\\d+");
    }
    
    public static int leerEntero() throws EntradaInvalidaException {
        Scanner sc = new Scanner(System.in);
        try {
            int valor = sc.nextInt();
            sc.nextLine();
            return valor;
        } catch (InputMismatchException e) {
            sc.nextLine();
            throw new EntradaInvalidaException("Se esperaba un número entero.", e); 
        }
    }
}