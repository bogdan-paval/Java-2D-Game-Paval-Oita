package PaooGame.exceptions;

public class MapLoadingException extends GameException {
    public MapLoadingException(String fisierHarta, Throwable cauza) {
        super("Eroare critica! Nu am putut incarca harta din fisierul: " + fisierHarta, cauza);
    }
}