package patronfactoriaabstracta.factory;

import patronfactoriaabstracta.model.Banco;
import patronfactoriaabstracta.model.Prestamo;

/**
 *
 * @author guis
 */
public abstract class AbstractFactory {
    public abstract Banco getBanco(String banco);
    public abstract Prestamo getPrestamo(String prestamo);
}
