/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patronfactoriaabstracta.factory;

import patronfactoriaabstracta.model.Banco;
import patronfactoriaabstracta.model.Prestamo;
import patronfactoriaabstracta.model.HDFC;
import patronfactoriaabstracta.model.ICICI;
import patronfactoriaabstracta.model.SBI;

/**
 *
 * @author guis
 */
public class BancoFactory extends AbstractFactory{

    @Override
    public Banco getBanco(String banco) {
        if(banco == null){
            return null;
        }
        if(banco.equalsIgnoreCase("HDFC")){
            return new HDFC();
        } else if (banco.equalsIgnoreCase("ICICI")){
            return new ICICI();
        } else if (banco.equalsIgnoreCase("SBI")){
            return new SBI();
        }
        return null;
    }

    @Override
    public Prestamo getPrestamo(String prestamo) {
        return null;
    }
    
}
