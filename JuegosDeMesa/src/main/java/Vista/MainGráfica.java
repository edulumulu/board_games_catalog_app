/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Excepciones.ExcepcionMia;
import interfazGráfica.MenuPrincipal;

/**
 *
 * @author eduardolucasmunozdelucas
 */
public class MainGráfica {
     /**
     * Muestra un ménú mediante interfaz gráfica que te permite ejecutar distintas órdenes.
     * @param args
     * @throws ExcepcionMia 
     */
    public static void main(String[] args) throws ExcepcionMia{
        
        MenuPrincipal menu = new MenuPrincipal();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }
}
