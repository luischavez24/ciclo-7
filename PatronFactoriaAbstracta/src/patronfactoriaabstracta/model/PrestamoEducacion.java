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
public class PrestamoEducacion extends Prestamo {
    @Override
    public void setTasaInteres(double tasa) {
        super.tasa = tasa;
    }
}
