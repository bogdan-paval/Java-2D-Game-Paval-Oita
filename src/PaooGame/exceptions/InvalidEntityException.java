package PaooGame.exceptions;

public class InvalidEntityException extends GameException {
    public InvalidEntityException(String tipPrimit) {
        super("Eroare de configurare: Tipul de entitate '" + tipPrimit + "' nu este recunoscut de EntityFactory!");
    }
}