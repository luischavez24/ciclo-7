/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patronfactoriaabstracta.model;

import java.time.Clock;

/**
 *
 * @author LABO01-9
 */
public abstract class Prestamo {

    protected double tasa;

    public abstract void setTasaInteres(double tasa);

    public void calcularPagoPrestamo(double cantidadPrestamo, int anios) {
        double EMI;
        int n;
        n = anios * 12;
        tasa = tasa / 1200;
        EMI = ((tasa * Math.pow((1 + tasa), n)) / ((Math.pow((1 + tasa), n)) - 1)) * cantidadPrestamo;
        
        System.out.println("your monthly EMI is" + EMI + "for the amount"+cantidadPrestamo+" you have borrowed");
    }

}
