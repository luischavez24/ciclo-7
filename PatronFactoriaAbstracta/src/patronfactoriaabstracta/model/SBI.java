/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patronfactoriaabstracta.model;

/**
 *
 * @author LABO01-9
 */
public class SBI implements Banco{
    
    private final String BNAME;
    public SBI(){
        BNAME = "Banco SBI";
    }

    @Override
    public String getNombreBanco() {
        return BNAME;
    }
    
}
