package PaooGame.entity;

import PaooGame.exceptions.InvalidEntityException;

public class EntityFactory {

    public static Entity createEntity(String tipAnimal, int x, int y, int nivelCurent) throws InvalidEntityException {
        TipEntitate tip = TipEntitate.fromString(tipAnimal);

        switch (tip) {
            case SHEEP_FUNDITA: return new Sheep(x, y, "fundita");
            case SHEEP_PAPION:  return new Sheep(x, y, "papion");
            case SHEEP_COWBOY:  return new Sheep(x, y, "cowboy");
            case SHEEP_MICHAEL: return new Sheep(x, y, "michael");
            case LUP:
                Wolf lup = new Wolf(x, y);

                if (nivelCurent == 1) {
                    lup.speed = 2;
                    lup.maxHp = 1;
                }
                else if (nivelCurent == 2) {
                    lup.speed = 2;
                    lup.maxHp = 2;
                }
                else if (nivelCurent == 3) {
                    lup.speed = 4;
                    lup.maxHp = 3;
                }

                lup.hp = lup.maxHp;
                return lup;

            default:
                return null;
        }
    }
}