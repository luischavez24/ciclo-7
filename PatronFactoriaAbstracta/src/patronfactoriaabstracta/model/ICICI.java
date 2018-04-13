/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patronfactoriaabstracta.model;

/**
 *
 * @author guis
 */
public class ICICI implements Banco{
    
    private final String BNAME;
    public ICICI(){
        BNAME = "Banco ICICI";
    }

    @Override
    public String getNombreBanco() {
        return BNAME;
    }
    
}
