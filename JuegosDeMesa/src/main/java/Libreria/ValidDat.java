/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Libreria;

import Excepciones.ExcepcionMia;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase contenedora de métodos que validan la introducción de datos por teclado
 * controlando las posibles excepciones
 *
 * @author eduardolucasmunozdelucas
 */
public class ValidDat {

    /**
     * Este metodo pide un numero por teclado y verifica que este entre los
     * valores a y b, si no se repite hasta que cumpla la condición.
     *
     * @param a --> Debe de ser un numero entero
     * @param b --> Debe de ser un numero entero
     * @return --> Retorna un el numero entero elegido en caso de cumplir la
     * restricción.
     */
    public static int enteroEntreDosValores(int a, int b, String texto) {

        Scanner sc = new Scanner(System.in);
        boolean ok = true;
        int num = -1;
        do {
            System.out.print(texto);
            try {
                num = sc.nextInt();
                if (num >= a && num <= b) {
                    ok = false;
                } else {
                    System.out.println("El valor debe estar comprendido entre: " + a + " y " + b + ":");
                    System.out.print("Vuelve a intentarlo:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Has introducido un dato invalido introduce un numero entre " + a + " y " + b + ".");
                System.out.print("Vuelve a intentarlo:");
                sc.next();
            }
        } while (ok);
        return num;
    }

    /**
     * Metodo que pide por teclado un texto, hasta que no verifica que hay algún
     * caracter no sale del bucle
     *
     * @return ---> Devuelve texto introducido.
     */
    public static String textoNoVacio(String t) {
        Scanner sc = new Scanner(System.in);
        String texto = null;
        System.out.print(t);
        do {
            texto = sc.nextLine();
        } while (palabraVacia(texto));

        return texto;
    }

    /**
     * Metodo que verifica que el texto introducido como parámetro tenga una
     * longitud mayor que 0
     *
     * @param t ---> String
     * @return ---> true o false
     */
    public static boolean palabraVacia(String t) {
        boolean si = t.length() == 0;
        if (si) {
            System.out.println("Tienes que escribir algo, intentalo de nuevo:");

        }
        return si;
    }

    /**
     * Pide un numero decimal que ha de ser mayor o igual que 0.
     *
     * @return --> Double
     */
    public static double pedirNumeroDecimal(int a, int b, String t) {
        Scanner sc = new Scanner(System.in);
        boolean ok = true;
        double num = -15;
        do {
            System.out.print(t);
            try {
                num = sc.nextDouble();
                if (num >= a && num <= b) {
                    ok = false;
                } else {
                    System.out.println("El valor debe de estar comprendido entre " + a + " y " + b + ". Vuelve a intentarlo");
                }

            } catch (InputMismatchException e) {
                System.out.println("El parámetro introduciod es incorrecto, debe ser un número decimal. Vuelve a intentarlo.");
                sc.next();
            }

        } while (ok);
        return num;
    }

    /**
     * Pide un numero entero que ha de ser mayor o igual que 0.
     *
     * @return
     */
    public static int pedirNumeroEntero(String t) {
        Scanner sc = new Scanner(System.in);
        boolean ok = true;
        int num = -15;
        do {
            try {
                System.out.print(t);
                num = sc.nextInt();
                if (num >= 0) {
                    ok = false;
                } else {
                    sc.next();
                    System.out.println("El valor debe de ser positivo. Vuelve a intentarlo");

                }

            } catch (InputMismatchException e) {
                System.out.println("El valor introduciod debe ser un número entero. Vuelve a intentarlo.");
                sc.next();
            }

        } while (ok);
        return num;
    }

    public static boolean pedirBooleano(String a, String b, String t) throws ExcepcionMia {
        Scanner sc = new Scanner(System.in);
        String textoIntroducido;
        boolean yes = true;
        boolean ok = true;

        do {
            System.out.println(t);
            try {
                textoIntroducido = sc.nextLine();
                if (textoIntroducido.equalsIgnoreCase(a)) {
                    yes = true;
                    ok = false;
                } else if (textoIntroducido.equalsIgnoreCase(b)) {
                    yes = false;
                    ok = false;
                } else {
                    System.out.println("Error, debes escribir  (y / n)");
                    
                }
            } catch (InputMismatchException e) {
                sc.next();
                throw new ExcepcionMia("Tiene que introducir una sola letra (y si es si y n si es no");
            }
        } while (ok);
        return yes;

    }

}
