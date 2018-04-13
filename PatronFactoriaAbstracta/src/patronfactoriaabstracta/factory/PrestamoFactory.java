/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patronfactoriaabstracta.factory;

import patronfactoriaabstracta.model.Banco;
import patronfactoriaabstracta.model.Prestamo;
import patronfactoriaabstracta.model.PrestamoCasa;
import patronfactoriaabstracta.model.PrestamoEducacion;
import patronfactoriaabstracta.model.PrestamoNegocio;

/**
 *
 * @author guis
 */
public class PrestamoFactory extends AbstractFactory {

    @Override
    public Banco getBanco(String banco) {
        return null;
    }

    @Override
    public Prestamo getPrestamo(String prestamo) {
        if(prestamo == null){
            return null;
        }
        if(prestamo.equalsIgnoreCase("Casa")){
            return new PrestamoCasa();
        } else if (prestamo.equalsIgnoreCase("Negocio")){
            return new PrestamoNegocio();
        } else if (prestamo.equalsIgnoreCase("Educacion")){
            return new PrestamoEducacion();
        }
        return null;
    }

}
