/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patronfactoriaabstracta.factory;

/**
 *
 * @author LABO01-9
 */
public class FactoryCreator {
    public static AbstractFactory getFactory(String eleccion){
        if (eleccion.equalsIgnoreCase("Banco")){
            return new BancoFactory();
        } else if (eleccion.equalsIgnoreCase("Prestamo")){
            return new PrestamoFactory();
        }
        return null;
    }
 
}
