package PaooGame.main;

public interface GameController {
    void restartJoc();
    void intoarcereLaMeniu();
    void setStareCurenta(Game.GameState stare);
    void setTimpStartCutScene(long timp);
    void incarcaStareSalvata(int nivel, int x, int y, int hp, int oite, int lupi);
    void salveazaJoculCurent();
}