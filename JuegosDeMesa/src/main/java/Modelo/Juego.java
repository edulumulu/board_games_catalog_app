
package Modelo;

import static Libreria.ValidDat.textoNoVacio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author edulumulu
 */
public class Juego {
    private int id;
    private String nombre;
    private String disenador;
    private int año;
    private int numJugMax;
    private int numJugMin;
    private int duracion;
    private String tematica;
    private int dificultad;
    private int estrategia;
    private int suerte;
    private int interaccion;
    private String dueno;
    private boolean expansion;
    private String expansionDe;
    private ArrayList<String> ListaExpansiones;
    private String descripcion;

    public Juego() {
    }
    
    public void cuandoEsExpansion (){
        if (this.expansion == true){
            String nombre = textoNoVacio("Introduce nombre juego original");
            this.expansionDe = nombre;
//            ListaExpansiones.add(nombre);   ((en caso de que sea expansión metodo para modificar el juego original))
        }
    }

    public Juego(int id) {
        this.id = id;
    }

    
    
    public Juego(int id, String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion, ArrayList<String> ListaExpansiones, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.ListaExpansiones = ListaExpansiones;
        this.descripcion = descripcion;
    }

    
    public Juego(int id, String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.descripcion = descripcion;
    }

    
    public Juego(int id, String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion, String expansionDe, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.expansionDe = expansionDe;
        this.descripcion = descripcion;
    }

    public Juego(int id, String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion, String expansionDe, ArrayList<String> ListaExpansiones, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.expansionDe = expansionDe;
        this.ListaExpansiones = ListaExpansiones;
        this.descripcion = descripcion;
    }

    
    
    public Juego(String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion, String expansionDe, String descripcion) {
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.expansionDe = expansionDe;
        this.descripcion = descripcion;
    }
    
    public Juego(String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion,  String descripcion) {
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.descripcion = descripcion;
    }

    
    
    public Juego(String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion, String expansionDe, ArrayList<String> ListaExpansiones, String descripcion) {
        
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.expansionDe = expansionDe;
        this.ListaExpansiones = ListaExpansiones;
        this.descripcion = descripcion;
    }

    
    public Juego(String nombre, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, boolean expansion) {
        this.nombre = nombre;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.expansion = expansion;
    }

    public Juego(String nombre, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, boolean expansion, String expansionDe) {
        this.nombre = nombre;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.expansion = expansion;
        this.expansionDe = expansionDe;
    }

    
    public Juego(String nombre, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String expansionDe) {
        this.nombre = nombre;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.expansionDe = expansionDe;
    }

    public Juego(String nombre, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, ArrayList<String> ListaExpansiones) {
        this.nombre = nombre;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.ListaExpansiones = ListaExpansiones;
    }

    
    public Juego( String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion) {
        
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
    }

   

    public Juego(String nombre, String disenador, int año, int numJugMax, int numJugMin, int duracion, String tematica, int dificultad, int estrategia, int suerte, int interaccion, String dueno, boolean expansion, String expansionDe, ArrayList<String> ListaExpansiones) {
        this.nombre = nombre;
        this.disenador = disenador;
        this.año = año;
        this.numJugMax = numJugMax;
        this.numJugMin = numJugMin;
        this.duracion = duracion;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.estrategia = estrategia;
        this.suerte = suerte;
        this.interaccion = interaccion;
        this.dueno = dueno;
        this.expansion = expansion;
        this.expansionDe = expansionDe;
        this.ListaExpansiones = ListaExpansiones;
    }
    
    

    public Juego(String nombre, boolean expansion) {
        this.nombre = nombre;
        this.expansion = expansion;
    }

    public Juego(String nombre) {
        this.nombre = nombre;
    }

    public Juego(String nombre, ArrayList<String> ListaExpansiones) {
        this.nombre = nombre;
        this.ListaExpansiones = ListaExpansiones;
    }
    
    
    public Juego(ResultSet resultSet) throws SQLException {
        
        this.nombre = resultSet.getString("nombre");
        this.disenador = resultSet.getString("disenador");
        this.año = resultSet.getInt("año");
        this.numJugMax = resultSet.getInt("numJugMax");
        this.numJugMin = resultSet.getInt("numJugMin");
        this.duracion = resultSet.getInt("duracion");
        this.tematica = resultSet.getString("tematica");
        this.dificultad = resultSet.getInt("dificultad");
        this.estrategia = resultSet.getInt("estrategia");
        this.suerte = resultSet.getInt("suerte");
        this.interaccion = resultSet.getInt("interaccion");
        this.dueno = resultSet.getString("dueno");
        this.expansion = resultSet.getBoolean("expansion");
        this.expansionDe = resultSet.getString("expansionDe");
        this.descripcion = resultSet.getString("descripcion");
        //       Asumimos que ListaExpansiones es una columna de tipo texto con valores separados por comas
        String listaExpansionesStr = resultSet.getString("ListaExpansiones");
        if (listaExpansionesStr != null && !listaExpansionesStr.isEmpty()) {
            this.ListaExpansiones = new ArrayList<>(List.of(listaExpansionesStr.split(",")));
        } else {
            this.ListaExpansiones = new ArrayList<>();
        }
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.id;
        hash = 11 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Juego other = (Juego) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.nombre, other.nombre);
    }

    @Override
    public String toString() {
        return "Juego{" + "id=" + id + ", nombre=" + nombre + ", diseñador=" + disenador + ", año=" + año + ", numJugMax=" + numJugMax + ", numJugMin=" + numJugMin + ", duracion=" + duracion + ", dificultad=" + dificultad + ", estrategia=" + estrategia + ", suerte=" + suerte + ", interaccion=" + interaccion + ", dueño=" + dueno + ", expansion=" + expansion + ", expansionDe=" + expansionDe + '}';
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   
     public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

   
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDisenador() {
        return disenador;
    }

    public void setDisenador(String disenador) {
        this.disenador = disenador;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getNumJugMax() {
        return numJugMax;
    }

    public void setNumJugMax(int numJugMax) {
        this.numJugMax = numJugMax;
    }

    public int getNumJugMin() {
        return numJugMin;
    }

    public void setNumJugMin(int numJugMin) {
        this.numJugMin = numJugMin;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public int getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(int estrategia) {
        this.estrategia = estrategia;
    }

    public int getSuerte() {
        return suerte;
    }

    public void setSuerte(int suerte) {
        this.suerte = suerte;
    }

    public int getInteraccion() {
        return interaccion;
    }

    public void setInteraccion(int interaccion) {
        this.interaccion = interaccion;
    }

    public String getDueño() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setExpansion(boolean expansion) {
        this.expansion = expansion;
    }

    public String getExpansionDe() {
        return expansionDe;
    }

    public void setExpansionDe(String expansionDe) {
        this.expansionDe = expansionDe;
    }

    public ArrayList<String> getListaExpansiones() {
        return ListaExpansiones;
    }

    public void setListaExpansiones(ArrayList<String> ListaExpansiones) {
        this.ListaExpansiones = ListaExpansiones;
    }
    
    
    
}
