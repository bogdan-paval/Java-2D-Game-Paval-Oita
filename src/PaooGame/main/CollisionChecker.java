package PaooGame.main;

import PaooGame.entity.Entity;
import PaooGame.map.Harta;
import java.awt.*;
import java.util.ArrayList;

public class CollisionChecker {
    private Harta hartaObstacole;

    public CollisionChecker() {
    }

    public void setHarta(Harta harta) {
        this.hartaObstacole = harta;
    }

    public void checkTile(Entity entity) {
        if (hartaObstacole == null) return;

        int moveX = 0;
        int moveY = 0;

        switch (entity.getDirection()) {
            case "up":    moveY = -entity.getSpeed(); break;
            case "down":  moveY = entity.getSpeed();  break;
            case "left":  moveX = -entity.getSpeed(); break;
            case "right": moveX = entity.getSpeed();  break;
        }

        java.awt.Rectangle hitboxViitor = entity.getHitboxPredictiv(moveX, moveY);

        if (hitboxViitor.x < 0 ||
                (hitboxViitor.x + hitboxViitor.width) >= (PaooGame.main.Game.MAP_MAX_X - PaooGame.main.Game.TILE_SIZE) ||
                hitboxViitor.y < 0 ||
                (hitboxViitor.y + hitboxViitor.height) >= (PaooGame.main.Game.MAP_MAX_Y - 3*PaooGame.main.Game.TILE_SIZE)) {

            entity.setCollisionOn(true);
            return;
        }

        int leftCol   = hitboxViitor.x / Game.TILE_SIZE;
        int rightCol  = (hitboxViitor.x + hitboxViitor.width) / Game.TILE_SIZE;
        int topRow    = hitboxViitor.y / Game.TILE_SIZE;
        int bottomRow = (hitboxViitor.y + hitboxViitor.height) / Game.TILE_SIZE;

        int dalaNumar1, dalaNumar2;

        switch (entity.getDirection()) {
            case "up":
                dalaNumar1 = hartaObstacole.getDalaID(leftCol, topRow);
                dalaNumar2 = hartaObstacole.getDalaID(rightCol, topRow);
                if (esteDalaSolida(dalaNumar1) || esteDalaSolida(dalaNumar2)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                dalaNumar1 = hartaObstacole.getDalaID(leftCol, bottomRow);
                dalaNumar2 = hartaObstacole.getDalaID(rightCol, bottomRow);
                if (esteDalaSolida(dalaNumar1) || esteDalaSolida(dalaNumar2)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                dalaNumar1 = hartaObstacole.getDalaID(leftCol, topRow);
                dalaNumar2 = hartaObstacole.getDalaID(leftCol, bottomRow);
                if (esteDalaSolida(dalaNumar1) || esteDalaSolida(dalaNumar2)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                dalaNumar1 = hartaObstacole.getDalaID(rightCol, topRow);
                dalaNumar2 = hartaObstacole.getDalaID(rightCol, bottomRow);
                if (esteDalaSolida(dalaNumar1) || esteDalaSolida(dalaNumar2)) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    public int checkEntity(Entity entity, ArrayList<Entity> targetList) {
        int indexLovit = -1;

        for (int i = 0; i < targetList.size(); i++) {
            Entity target = targetList.get(i);

            if (target != null && target != entity) {
                int offsetX = 0;
                int offsetY = 0;

                switch (entity.getDirection()) {
                    case "up": offsetY -= entity.getSpeed(); break;
                    case "down": offsetY += entity.getSpeed(); break;
                    case "left": offsetX -= entity.getSpeed(); break;
                    case "right": offsetX += entity.getSpeed(); break;
                }

                Rectangle hitboxEntitateViitor = entity.getHitboxPredictiv(offsetX, offsetY);
                Rectangle hitboxTarget = target.getHitboxPredictiv(0, 0);
                if (hitboxEntitateViitor.intersects(hitboxTarget)) {
                    entity.setCollisionOn(true);
                    indexLovit = i;
                }
            }
        }
        return indexLovit;
    }

    private boolean esteDalaSolida(int idDala) {
        if (idDala == -1) return false;
        if (idDala >= 0 && idDala < PaooGame.map.Tile.tiles.length) {
            PaooGame.map.Tile tileLovit = PaooGame.map.Tile.tiles[idDala];

            if (tileLovit != null) {
                return true;
            }
        }
        return false;
    }
}