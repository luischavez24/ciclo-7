/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sincronizacion;

/**
 *
 * @author lucho
 */
public class Sincronizacion {
    public static String mio = new String("asdf");
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        FinTrans alfa = new FinTrans();
        TransThread tt1 = new TransThread(alfa, "deposito");
        
        TransThread tt2 = new TransThread(alfa, "retiro");
        
        
        tt1.start();
        tt2.start();
        tt1.join();
        tt2.join();
    }
    
}
