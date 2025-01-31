/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *Clase que hereda de Excepcion y que configura las excepciones personales que crearé relaccionada con la conexión
 * la modificación y las querys realizadas en la base de datos
 * 
 * @author eduardolucasmunozdelucas
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
