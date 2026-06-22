package PaooGame.event;

import PaooGame.entity.Entity;

public interface GameObserver {
    void onEvent(String eventType, Entity entity);
}