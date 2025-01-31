
package ControladorJuegos;

import Excepciones.ExcepcionMia;

/**
 *Interfaz que estructura los métodos que tendran que desarrollar las clases que implementen de ella.
 * En este caso como la clase que la implementa trata con la interfazDa todos han de lanzar la excepciónMias hacia arriba
 * 
 * @author edualumulu
 */
public interface InterfazJuegos {

    public abstract void ingresarJuego() throws ExcepcionMia;

}
