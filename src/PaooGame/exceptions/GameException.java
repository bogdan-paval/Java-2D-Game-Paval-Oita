package PaooGame.exceptions;

public class GameException extends Exception {
    public GameException(String mesaj) {
        super(mesaj);
    }

    public GameException(String mesaj, Throwable cauza) {
        super(mesaj, cauza);
    }
}