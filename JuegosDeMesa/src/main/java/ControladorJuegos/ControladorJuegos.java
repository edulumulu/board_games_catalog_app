
package ControladorJuegos;

import ControladorDAO.GestionBBDD;
import ControladorDAO.InterfazDAO;
import Excepciones.ExcepcionMia;
import Libreria.ValidDat;
import static Libreria.ValidDat.enteroEntreDosValores;
import static Libreria.ValidDat.pedirBooleano;
import static Libreria.ValidDat.pedirNumeroDecimal;
import static Libreria.ValidDat.pedirNumeroEntero;
import static Libreria.ValidDat.textoNoVacio;
import Modelo.Juego;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;

/**
 *
 * @author edulumulu
 */
public class ControladorJuegos implements InterfazJuegos {

    //Creo como atributo de clase una variable de la interfazDao para poder acceder a los distintos
    //metodos para interactuar en este caso con la bbdd
    private InterfazDAO daoEst;

    /**
     * Solicita por pantalla los datos del alumno y lo inserta en la base de
     * datos a travez de la clase GestionBBDD que implementa de la interfaz
     * daoEst
     *
     * @throws ExcepcionMia
     */
    @Override
    public void ingresarJuego() throws ExcepcionMia {

        /*
        Solicito los distintos datos del juego a ingresar haciendo las valdaciones necesarias
        Utilizo el metodo insertar de la interfaz DAO y compruebo que se ha insertado comprobando
        el valor devuelto con el metodo insertar, si se verifica con el valor uno imprimo por pantalla
        mensaje de ingresado con éxito, si no mensaje de error.
        Elevo hacia arriba la posible excepción de inserción de datos con la interfaz excepción mía.
         */
        daoEst = new GestionBBDD();
        Calendar cal= Calendar.getInstance();
        int juegossregistrados;
        
        String nombre = textoNoVacio("Introduce el nombre del juego: ");
        String disenador = textoNoVacio("Introduce el Diseñador del juego: ");
        int año = enteroEntreDosValores(1980, cal.get(Calendar.YEAR), "Introduce el año de lanzamiento: ");
        int numJugMax = enteroEntreDosValores (1, 20, "Introduce el número máximo de jugadores: ");
        int numJugMin = enteroEntreDosValores (1, (numJugMax-1), "Introduce el número mínimo de jugadores: ");
        int duracion = enteroEntreDosValores (5, 500, "Introduce la duración de una partida: ");
        String tematica = textoNoVacio("Introduce la temática del juego: ");
        int dificultad = enteroEntreDosValores (1, 5, "Introduce el nivel de dificultad: ");
        int estrategia = enteroEntreDosValores (1, 5, "Introduce el nivel de estrategia: ");
        int suerte = enteroEntreDosValores (1, 5, "Introduce el nivel de suerte: ");
        int interaccion = enteroEntreDosValores (1, 5, "Introduce el nivel de interacción: ");
        String dueño = textoNoVacio("Introduce el nombre del dueño: ");
        boolean expansion = pedirBooleano("y" , "n", "¿Este juego es una expansión? (y / n): ");
      
        if(expansion == true){
            String expansionDe = textoNoVacio("Introduce el nombre del juego original: ");
            juegossregistrados = daoEst.insertar(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueño, expansion, expansionDe));
        }else{
            juegossregistrados = daoEst.insertar(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueño, expansion));
        }
        
        if (juegossregistrados == 1) {
            System.out.println("Juego " + nombre + " ingresado con éxito.");
        } else {
            System.out.println("No se ha podiso realizar el ingreso.");
        }
    }

}
