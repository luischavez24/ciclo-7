/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patronfactoriaabstracta;

import java.io.*;
import patronfactoriaabstracta.factory.AbstractFactory;
import patronfactoriaabstracta.factory.FactoryCreator;
import patronfactoriaabstracta.model.Banco;
import patronfactoriaabstracta.model.Prestamo;

/**
 *
 * @author LABO01-9
 */
public class PatronFactoriaAbstracta {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the name of Bank from where you want to take loan amount: ");
        String nombreBanco = br.readLine();
        System.out.print("\n");
        System.out.print("Enter the type of loan e.g. home loan or business loan or education loan: ");
        
        String loanName = br.readLine();
        AbstractFactory bancoFactory = FactoryCreator.getFactory("Banco");
        Banco b = bancoFactory.getBanco(nombreBanco);
        System.out.print("\n");
        System.out.print("Enter the interest rate for " + b.getNombreBanco() + ": ");
        double tasa = Double.parseDouble(br.readLine());
        
        System.out.print("\n");
        System.out.print("Enter the loan amount you want to take: ");
        double cantidadPrestamo = Double.parseDouble(br.readLine());
        System.out.print("\n");
        
        System.out.print("Enter the number of years to pay your entire loan amount:");
        int anios = Integer.parseInt(br.readLine());
        System.out.print("\n");
        System.out.println("you are taking the loan from " + b.getNombreBanco());
        
        AbstractFactory loanFactory = FactoryCreator.getFactory("Loan");
        Prestamo prestamo = loanFactory.getPrestamo(loanName);
        prestamo.setTasaInteres(tasa);
        prestamo.calcularPagoPrestamo(cantidadPrestamo, anios);
    }

}
