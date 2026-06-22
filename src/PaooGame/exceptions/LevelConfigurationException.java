package PaooGame.exceptions;

public class LevelConfigurationException extends GameException {
    public LevelConfigurationException(String caleFisier, String liniaCuEroare, Throwable cauza) {
        super("Format invalid in fisierul de nivel: " + caleFisier + " la linia: [" + liniaCuEroare + "]", cauza);
    }
}