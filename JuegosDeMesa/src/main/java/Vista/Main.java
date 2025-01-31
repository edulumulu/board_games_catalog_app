/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import ControladorJuegos.ControladorJuegos;
import ControladorJuegos.InterfazJuegos;
import Excepciones.ExcepcionMia;
import static Libreria.ValidDat.pedirBooleano;

/**
 *
 * @author eduardolucasmunozdelucas
 */
public class Main {
    private static InterfazJuegos intjue;
    
    public static void main(String[] args) throws ExcepcionMia {
        intjue = new ControladorJuegos();
        intjue.ingresarJuego();
//        pedirBooleano("y", "n", "dime y o n: ");
    }
}
