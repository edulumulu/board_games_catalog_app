
package ControladorDAO;


import Excepciones.ExcepcionMia;
import Modelo.Juego;
import java.util.ArrayList;

/**
 * Interfaz que propone los métodos que se tendrán en cuenta a la hora de almacenar, modificar y leer los datos.
 * Todos los métodos lanzan las excepciones que tienen que ver con la conexion de la base de datos hacia arriba.
 * @author edulumulu
 */
public interface InterfazDAO {
    
    public abstract ArrayList<Juego> buscarTotal (int numJug, int duracionmin, int duracionmax, int dif, int estra, int suerte, int interac, String tema) throws ExcepcionMia;
 
    public abstract ArrayList<Juego> buscarporTematica (String n) throws ExcepcionMia;
    
    public abstract ArrayList<Juego> buscarporDificultad (int n) throws ExcepcionMia;

    public abstract ArrayList<Juego> buscarporTematicaYDificultad (String t, int n) throws ExcepcionMia;
        
    public abstract ArrayList<Juego> buscarporNumJugadores (int n) throws ExcepcionMia;
    
    public abstract ArrayList<Juego> buscarporTematicaYNumJug (String t, int numjug) throws ExcepcionMia;
     
    public abstract ArrayList<Juego> buscarporNumJugYDificultad (int numjug, int dific) throws ExcepcionMia;
      
    public abstract ArrayList<Juego> buscarporTemáticaYDificultadyNumJu (String p, int numjug, int dific) throws ExcepcionMia;

    public abstract ArrayList<Juego> buscarporNombreConLetras (String n) throws ExcepcionMia;
    
    public abstract ArrayList<Juego> buscarporTematicayDuracion (String n, int min, int max) throws ExcepcionMia;
    
    public abstract ArrayList<Juego> buscarporDuracion (int min, int max) throws ExcepcionMia;
        
    public abstract ArrayList<Juego> buscarporTematicayDuracionyDificultad (String n, int min, int max, int dif) throws ExcepcionMia;
    
    public abstract ArrayList<Juego> buscarporTematicayDuracionyNumJug (String n, int min, int max, int numJug) throws ExcepcionMia;
   
    public abstract ArrayList<Juego> buscarporlas4 (String n, int min, int max, int numJug, int dif) throws ExcepcionMia;

    public abstract ArrayList<Juego> buscarporDuracionyDificultad (int min, int max, int dif) throws ExcepcionMia;
   
    public abstract ArrayList<Juego> buscarporDuracionyNumJug (int min, int max, int numJug) throws ExcepcionMia;
     
    public abstract ArrayList<Juego> buscarporDuracionyNumJugyDificultad (int min, int max, int numJug, int dif) throws ExcepcionMia;

    public abstract int cantidadTotal() throws ExcepcionMia;
    
    public abstract int cantidadporDueño(String nombre) throws ExcepcionMia;
    
    public abstract ArrayList<String> nombresDeJuegosporDueño(String nombre) throws ExcepcionMia;

    public abstract Juego leerExpansiones (Juego j) throws ExcepcionMia;
    
    public abstract ArrayList<String> leerTematica() throws ExcepcionMia;

    public abstract ArrayList<Juego> leerTodos() throws ExcepcionMia;
        
    public abstract ArrayList<Juego> leerTodos2() throws ExcepcionMia;

    public abstract Juego leerJuego (Juego j) throws ExcepcionMia;
        
    public abstract Juego leerJuegosPorNombre(String n) throws ExcepcionMia;
          
    public abstract int insertar(Juego j) throws ExcepcionMia;
    
    public abstract int elimniar(Juego j) throws ExcepcionMia;
    
    public abstract int actualizarTodo(Juego a) throws ExcepcionMia;
   
    public abstract int actualizarExpansiones(Juego j) throws ExcepcionMia;

}
