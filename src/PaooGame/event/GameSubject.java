package PaooGame.event;

public interface GameSubject {
    void addObserver(GameObserver o);
    void removeObserver(GameObserver o);
    void notifyObservers(String eventType);
}