package PaooGame.entity;

import PaooGame.exceptions.InvalidEntityException;

public enum TipEntitate {
    SHEEP_FUNDITA,
    SHEEP_PAPION,
    SHEEP_COWBOY,
    SHEEP_MICHAEL,
    LUP;

    public static TipEntitate fromString(String text) throws InvalidEntityException {
        switch (text.toLowerCase()) {
            case "sheep_fundita": return SHEEP_FUNDITA;
            case "sheep_papion":  return SHEEP_PAPION;
            case "sheep_cowboy":  return SHEEP_COWBOY;
            case "sheep_michael": return SHEEP_MICHAEL;
            case "lup":           return LUP;
            default:
                throw new InvalidEntityException(text);
        }
    }
}