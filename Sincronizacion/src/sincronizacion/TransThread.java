/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sincronizacion;

import java.util.Date;

/**
 *
 * @author lucho
 */
public class TransThread extends Thread {
    
  //  public String mio = new String("asdf");
    private FinTrans ft;
    //name indica el tipo de transaccion

    public TransThread(FinTrans ft, String name) {
        super(name); // Save thread's name
        this.ft = ft; // Save reference to financial transaction object
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            if (this.getName().equals("deposito")) {
                deposito();
            } else {
                retiro();
            }
        }

    }

    private void deposito() {
        
        synchronized(Sincronizacion.mio){
            ft.transName = "Deposito";
        
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            ft.amount = 2000;
            System.out.println("DEPOSITO => Nombre: " + ft.transName + " Cantidad: " + ft.amount + " - " + new Date());
        }
    }

    private void retiro() {
        synchronized(Sincronizacion.mio){
            ft.transName = "Retiro";
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            ft.amount = 250;
            System.out.println("RETIRO => Nombre: " + ft.transName + " Cantidad: " + ft.amount + " - " + new Date());
         }
     }
}
