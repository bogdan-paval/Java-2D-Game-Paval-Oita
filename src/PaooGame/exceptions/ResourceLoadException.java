package PaooGame.exceptions;

public class ResourceLoadException extends GameException {
    public ResourceLoadException(String pathResursa, Throwable cauza) {
        super("Eroare fatala: Nu am putut incarca resursa grafica de la calea -> " + pathResursa, cauza);
    }
}