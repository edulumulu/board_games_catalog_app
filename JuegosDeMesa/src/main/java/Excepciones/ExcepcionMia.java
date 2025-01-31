
package Excepciones;

/**
 *Clase que hereda de Excepcion y que configura las excepciones personales que crearé relaccionada con la conexión
 * la modificación y las querys realizadas en la base de datos
 * 
 * @author edulumulu
 */
public class ExcepcionMia extends Exception{
    
    /**
     * Excepción personal sin parametro de entrada
     */
    public ExcepcionMia(){
        
    }
    
    public ExcepcionMia (String texto){
        super(texto);
    }
}
