/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author eduardolucasmunozdelucas
 */
public class ControladorJuegos implements InterfazJuegos {

    //Creo como atributo de clase una variable de la interfazDao para poder acceder a los distintos
    //metodos para interactuar en este caso con la bbdd
    private InterfazDAO daoEst;

    /**
     * Recorre un arraylist de objetos alumno mosstrando un listado. Si el array
     * eestá vaccío imprime mensaje de error
     *
     * @return --> true (listado tiene ocurrencias) o false (está vacío)
     * @throws ExcepcionMia
     */
//    @Override
//    public boolean listadoAlumnos() throws ExcepcionMia {
//        boolean tieneAlumnos = false;
//        daoEst = new GestionBBDD(); //inicializo un objeto de la clase gestion bbdd
//
//        //creo una assaylist de alumnos que es igual que el array list generado por las ocurrencias de la tabla alumnos de la base de datos
//        ArrayList<Alumno> alumnos = daoEst.leerTodos();
//        //Si está no está vacío el listado lo recorro e imprimo por pantalla un listado, si no muestro un mensaje con la lista vacía
//        if (!alumnos.isEmpty()) {
//            System.out.println(" ");
//            System.out.println("---- LISTA DE ALUMNOS ----");
//            alumnos.forEach(e -> {
//                System.out.println(e.toString());
//            });
//            tieneAlumnos = true;    //Cambio booleana a true (listado de alumnos tiene ocurrencias)
//        } else {
//            System.out.println(" ");
//            System.out.println("---- LISTA DE ALUMNOS ----");
//            System.out.println("(No hay ningún alumno ingresado.)");
//        }
//        return tieneAlumnos;    //retorno el valor de la variable ya sea false o true
//    }

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
        Solicito los distintos datos del alumno a ingresar haciendo las valdaciones necesarias
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

//    /**
//     * Elimina el alumno con id indicado solicitado por consola. Si hay un erro
//     * con la conexión y escritura con la bbdd elevo excepción
//     *
//     * @throws ExcepcionMia
//     */
//    @Override
//    public void eliminarAlumnos() throws ExcepcionMia {
//
//        /*
//        Si la base de datos contiene alumnos (aprovecho y lo imprimo por pantalla el listado) en la tabla solicito por consola el id
//        Creo un alumno con dicho ide. Comparo mediante el metodo leer de la interfaz DAO si está en la base de datos
//        Si el id está en la base de datos, lo elimino con el metodo correpondiente y compruebo se si ha ejecutado la acción
//        mostrandolo por pantalla, si no existe un alumno con ese id tambien lo muestro por pantalla
//         */
//        daoEst = new GestionBBDD();
//        if (listadoAlumnos() == true) {
//            System.out.println(" ");
//            int id = pedirNumeroEntero("Introduce el id del alumno que quieres eliminar: ");
//            Alumno alumnoParaBorrar = new Alumno(id);
//
//            if (daoEst.leer(alumnoParaBorrar).isEmpty()) {
//                System.out.println(" ");
//                System.out.println("El alumno con id  " + alumnoParaBorrar.getId() + " no existe verífica el listado nuevamente.");
//            } else {
//                if (daoEst.elimniar(new Alumno(id)) == 1) {
//                    System.out.println("El alumno con id " + alumnoParaBorrar.getId() + " ha sido borrado.");
//                    listadoAlumnos();
//                } else {
//                    System.out.println("El alumno con id " + alumnoParaBorrar.getId() + " no ha sido borrado.");
//                }
//            }
//
//        }
//
//    }
//
//    /**
//     * Muestra por consola un menú para con distintas opciones de actualizar
//     * alumnos. Si hay un erro con la conexión y escritura con la bbdd elevo
//     * excepción
//     *
//     * @throws ExcepcionMia
//     */
//    @Override
//    public void menuActualizar() throws ExcepcionMia {
//
//        /*
//        Si el listado de alumnos no está vacio:
//        Muestro un menú con distintas opciones de actualización de datos de los alumnos.
//        Se elija la opción que se elija despues sale de bucle do, lo he dejado por si quisiera hacer algun bucle para no salir del menu
//        pero bueno... 
//         */
//        
//        if (listadoAlumnos() != false) {
//            boolean ok = true;
//            do {
//                System.out.println("""
//                               
//                               ------- ¿Qué deseas modificar? --------
//                               1. Modifica todos los datos del alumno.
//                               2. Modifica el nombre.
//                               3. Modifica la edad.
//                               4. Modifica el curso.
//                               5. Modifica la nota media.
//                               6. Salir
//                               ---------------------------------------
//                               """);
//                int opc = enteroEntreDosValores(1, 6, "Introduce la opción deseada: ");
//
//                switch (opc) {
//                    case 1:
//                        actualizarAlumnos();
//                        ok = false;
//                        break;
//                    case 2:
//                        updNombre();
//                        ok = false;
//                        break;
//                    case 3:
//                        updEdad();
//                        ok = false;
//                        break;
//                    case 4:
//                        updCurso();
//                        ok = false;
//                        break;
//                    case 5:
//                        updMedia();
//                        ok = false;
//                        break;
//                    case 6:
//                        ok = false;
//                  
//                    }
////                
//            } while (ok);
//        }
//    }
//
//    /**
//     * Actualiza todos los datos del alumno elegido según su id
//     * @throws ExcepcionMia 
//     */
//    public void actualizarAlumnos() throws ExcepcionMia {
//
//        /*
//        Muestro listado, solicito un id y creo un alumno con ese id para comprobar si existe en la base de datos
//        si no existe lo muestro por pantalla, si si esiste solicito el resto de datos a modificar
//        Finalmente seteo el cada dato del alumno con los introducidos y actualizo la lista pasándole el
//        alumno con los datos modificados, si se ejecuta o no el cambio en la bbdd lo muestro por pantalla
//        Finalmente muestro el listado de alumnos para que se pueda ver el cambio.
//        */
//        
//        listadoAlumnos();
//        System.out.println(" ");
//        int id = pedirNumeroEntero("Introduce el id del alumno: ");
//        Alumno EstudianteAActualizar = new Alumno(id);
//        daoEst = new GestionBBDD();
//        if (daoEst.leer(EstudianteAActualizar).isEmpty()) {
//            System.out.println(" ");
//            System.out.println("El id  " + EstudianteAActualizar.getId() + " no existe verífica el listado nuevamente.");
//        } else {
//            String nombre = textoNoVacio("Introduce el nombre del estudiante: ");
//            int edad = enteroEntreDosValores(4, 99, "Introduce la edad del alumno: ");
//            String curso = textoNoVacio("Introduce el curso del estudiante: ");
//            double media = pedirNumeroDecimal(0, 10, "Introduce la nota media del alumno:");
//
//            EstudianteAActualizar.setNombre(nombre);
//            EstudianteAActualizar.setEdad(edad);
//            EstudianteAActualizar.setCurso(curso);
//            EstudianteAActualizar.setMedia(media);
//
//            daoEst.actualizarTodo(EstudianteAActualizar);
//
//            if (daoEst.actualizarTodo(EstudianteAActualizar) == 1) {
//                System.out.println("Alumno actualizado");
//            } else {
//                System.out.println("no se ha podido actualizar");
//            }
//        }
//
//        listadoAlumnos();
//
//    }
//    
//    /*
//    Los 4 métodos restantes siguen exactamente la mísma lógica que el anterior solo que en cada uno
//    de ellos modifico un atributo del alumno.
//    */
//
//    /**
//     * Actualiza el nombre del alumno elegido según su id
//     * @throws ExcepcionMia 
//     */
//    public void updNombre() throws ExcepcionMia {
//
//        int id = pedirNumeroEntero("Introduce el id del alumno: ");
//        Alumno EstudianteAActualizar = new Alumno(id);
//        daoEst = new GestionBBDD();
//        if (daoEst.leer(EstudianteAActualizar).isEmpty()) {
//            System.out.println(" ");
//            System.out.println("El id  " + EstudianteAActualizar.getId() + " no existe verífica el listado nuevamente.");
//        } else {
//            String nombre = textoNoVacio("Introduce el nombre del estudiante: ");
//            EstudianteAActualizar.setNombre(nombre);
//            daoEst.actualizarNombre(EstudianteAActualizar);
//
//            if (daoEst.actualizarNombre(EstudianteAActualizar) == 1) {
//                System.out.println("Alumno actualizado");
//            } else {
//                System.out.println("no se ha podido actualizar");
//            }
//        }
//        listadoAlumnos();
//    }
//
//    /**
//     * Actualiza la edad del alumno elegido según su id.
//     * 
//     * @throws ExcepcionMia 
//     */
//    public void updEdad() throws ExcepcionMia {
//
//        int id = pedirNumeroEntero("Introduce el id del alumno: ");
//        Alumno EstudianteAActualizar = new Alumno(id);
//        daoEst = new GestionBBDD();
//        if (daoEst.leer(EstudianteAActualizar).isEmpty()) {
//            System.out.println(" ");
//            System.out.println("El id  " + EstudianteAActualizar.getId() + " no existe verífica el listado nuevamente.");
//        } else {
//            int edad = enteroEntreDosValores(4, 99, "Introduce la edad del alumno: ");
//            EstudianteAActualizar.setEdad(edad);
//            daoEst.actualizarEdad(EstudianteAActualizar);
//
//            if (daoEst.actualizarEdad(EstudianteAActualizar) == 1) {
//                System.out.println("Alumno actualizado");
//            } else {
//                System.out.println("no se ha podido actualizar");
//            }
//        }
//        listadoAlumnos();
//    }
//
//    /**
//     * Actualiza el curso del alumno elegido según su id.
//     * 
//     * @throws ExcepcionMia 
//     */
//    public void updCurso() throws ExcepcionMia {
//
//        int id = pedirNumeroEntero("Introduce el id del alumno: ");
//        Alumno EstudianteAActualizar = new Alumno(id);
//        daoEst = new GestionBBDD();
//        if (daoEst.leer(EstudianteAActualizar).isEmpty()) {
//            System.out.println(" ");
//            System.out.println("El id  " + EstudianteAActualizar.getId() + " no existe verífica el listado nuevamente.");
//        } else {
//            String curso = textoNoVacio("Introduce el curso del estudiante: ");
//            EstudianteAActualizar.setCurso(curso);
//            daoEst.actualizarCurso(EstudianteAActualizar);
//
//            if (daoEst.actualizarCurso(EstudianteAActualizar) == 1) {
//                System.out.println("Alumno actualizado");
//            } else {
//                System.out.println("no se ha podido actualizar");
//            }
//        }
//        listadoAlumnos();
//    }
//
//    /**
//     * Actualiza la nota media del alumno elegido según su id
//     * 
//     * @throws ExcepcionMia 
//     */
//    public void updMedia() throws ExcepcionMia {
//
//        int id = pedirNumeroEntero("Introduce el id del alumno: ");
//        Alumno EstudianteAActualizar = new Alumno(id);
//        daoEst = new GestionBBDD();
//        if (daoEst.leer(EstudianteAActualizar).isEmpty()) {
//            System.out.println(" ");
//            System.out.println("El id  " + EstudianteAActualizar.getId() + " no existe verífica el listado nuevamente.");
//        } else {
//            double media = pedirNumeroDecimal(0, 10, "Introduce la nota media del alumno:");
//            EstudianteAActualizar.setMedia(media);
//            daoEst.actualizarMedia(EstudianteAActualizar);
//
//            if (daoEst.actualizarMedia(EstudianteAActualizar) == 1) {
//                System.out.println("Alumno actualizado");
//            } else {
//                System.out.println("no se ha podido actualizar");
//            }
//        }
//        listadoAlumnos();
//    }

}
