package PaooGame.exceptions;

public class DatabaseSyncException extends GameException {
    public DatabaseSyncException(String mesaj, Throwable cauza) {
        super("Eroare la sincronizarea cu Baza de Date: " + mesaj, cauza);
    }
}