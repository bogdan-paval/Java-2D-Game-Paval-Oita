package PaooGame.entity;

import PaooGame.main.CollisionChecker;
import PaooGame.main.Game;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Sheep extends Entity implements InteractiaCuZone {

    private static final int ACTION_INTERVAL = 120;
    private static final int SHEEP_SIZE = 64;

    private int razaUrmarire = 400;
    private int distantaOprireMax = 70;
    private int distantaOprireMin = 50;
    private boolean esteSalvata = false;

    private String tipOaie;

    private java.awt.image.BufferedImage[] animSus;
    private java.awt.image.BufferedImage[] animJos;
    private java.awt.image.BufferedImage[] animStanga;
    private java.awt.image.BufferedImage[] animDreapta;

    private ArrayList<Rectangle> portiTarc = new ArrayList<>();
    private int tarcX, tarcY;

    public Sheep(int startX, int startY, String tipOaie) {
        this.worldX = startX;
        this.worldY = startY;
        this.speed = 2;
        this.direction = "down";
        this.tipOaie = tipOaie;

        incarcaAnimatii();

        this.solidArea = new Rectangle();
        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidArea.width = 48;
        this.solidArea.height = 48;

        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
    }

    public void seteazaDestinatieTarc(int colTarc, int randTarc) {
        int tileSize = Game.TILE_SIZE;
        this.tarcX = (colTarc * tileSize) + 10;
        this.tarcY = (randTarc * tileSize) + 10;
    }

    public boolean isEsteSalvata() {
        return esteSalvata;
    }

    public String getTipOaie() {
        return tipOaie;
    }

    @Override
    public void primesteZonaSpeciala(int colPoarta, int randPoarta, int latimeInDale, int inaltimeInDale) {
        int tileSize = PaooGame.main.Game.TILE_SIZE;
        int poartaPixelX = colPoarta * tileSize;
        int poartaPixelY = randPoarta * tileSize;
        int poartaPixelLatime = latimeInDale * tileSize;
        int poartaPixelInaltime = inaltimeInDale * tileSize;

        this.portiTarc.add(new java.awt.Rectangle(poartaPixelX, poartaPixelY, poartaPixelLatime, poartaPixelInaltime));
    }

    public void incarcaAnimatii() {
        this.animSus = PaooGame.Graphics.Assets.sheep_sus;

        switch (tipOaie) {
            case "fundita":
                this.animJos = PaooGame.Graphics.Assets.sheep_fundita_jos;
                this.animStanga = PaooGame.Graphics.Assets.sheep_fundita_stanga;
                this.animDreapta = PaooGame.Graphics.Assets.sheep_fundita_dreapta;
                break;
            case "papion":
                this.animJos = PaooGame.Graphics.Assets.sheep_papion_jos;
                this.animStanga = PaooGame.Graphics.Assets.sheep_papion_stanga;
                this.animDreapta = PaooGame.Graphics.Assets.sheep_papion_dreapta;
                break;
            case "cowboy":
                this.animJos = PaooGame.Graphics.Assets.sheep_cowboy_jos;
                this.animStanga = PaooGame.Graphics.Assets.sheep_cowboy_stanga;
                this.animDreapta = PaooGame.Graphics.Assets.sheep_cowboy_dreapta;
                break;
            case "michael":
                this.animJos = PaooGame.Graphics.Assets.sheep_mafia_jos;
                this.animStanga = PaooGame.Graphics.Assets.sheep_mafia_stanga;
                this.animDreapta = PaooGame.Graphics.Assets.sheep_mafia_dreapta;
                break;
        }
    }

    @Override
    public void update(CollisionChecker checker, ArrayList<Entity> entitati) {
        if (isDead()) return;

        if (esteSalvata) {
            return;
        }

        Rectangle sheepHitbox = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);

        for (Rectangle poarta : portiTarc) {
            if (poarta.intersects(sheepHitbox)) {
                this.worldX = tarcX;
                this.worldY = tarcY;
                this.esteSalvata = true;
                this.direction = "down";

                notifyObservers("SHEEP_SAVED");
                return;
            }
        }

        Player bogo = Player.getInstance();
        boolean ilUrmarestePeBogo = false;
        boolean seMisca = true;

        if (bogo != null) {
            double distBogo = Math.sqrt(Math.pow(bogo.getWorldX() - this.worldX, 2) + Math.pow(bogo.getWorldY() - this.worldY, 2));

            if (distBogo < razaUrmarire) {
                ilUrmarestePeBogo = true;

                int diffX = Math.abs(bogo.getWorldX() - this.worldX);
                int diffY = Math.abs(bogo.getWorldY() - this.worldY);
                int toleranta = 20;

                String directieSpreBogo = direction;
                if (direction.equals("left") || direction.equals("right")) {
                    if (diffY > diffX + toleranta) {
                        directieSpreBogo = (this.worldY < bogo.getWorldY()) ? "down" : "up";
                    } else {
                        directieSpreBogo = (this.worldX < bogo.getWorldX()) ? "right" : "left";
                    }
                } else {
                    if (diffX > diffY + toleranta) {
                        directieSpreBogo = (this.worldX < bogo.getWorldX()) ? "right" : "left";
                    } else {
                        directieSpreBogo = (this.worldY < bogo.getWorldY()) ? "down" : "up";
                    }
                }

                if (distBogo > distantaOprireMax) {
                    seMisca = true;
                    direction = directieSpreBogo;
                }
                else if (distBogo < distantaOprireMin) {
                    seMisca = true;
                    switch(directieSpreBogo) {
                        case "up": direction = "down"; break;
                        case "down": direction = "up"; break;
                        case "left": direction = "right"; break;
                        case "right": direction = "left"; break;
                    }
                }
                else {
                    seMisca = false;
                    direction = directieSpreBogo;
                }
            }
        }

        if (!ilUrmarestePeBogo) {
            alegeDirectieAleatorie(ACTION_INTERVAL);
        }

        if (seMisca) {
            collisionOn = false;
            checker.checkTile(this);
            checker.checkEntity(this, entitati);

            if (!collisionOn) {
                switch (direction) {
                    case "up":    worldY -= speed; break;
                    case "down":  worldY += speed; break;
                    case "left":  worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            int max_X = Game.MAP_MAX_X - SHEEP_SIZE;
            int max_Y = Game.MAP_MAX_Y - SHEEP_SIZE;

            if (worldX < 0) worldX = 0;
            if (worldY < 0) worldY = 0;
            if (worldX > max_X) worldX = max_X;
            if (worldY > max_Y) worldY = max_Y;

            actualizeazaAnimatia();
        } else {
            spriteNum = 0;
        }
    }

    private void actualizeazaAnimatia() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum++;
            if (spriteNum > 2) spriteNum = 0;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        if (isDead()) return;
        java.awt.image.BufferedImage image = null;

        if (esteSalvata) {
            image = animJos[0];
        } else {
            switch (direction) {
                case "up":    image = animSus[spriteNum]; break;
                case "down":  image = animJos[spriteNum]; break;
                case "left":  image = animStanga[spriteNum]; break;
                case "right": image = animDreapta[spriteNum]; break;
            }
        }

        if (image != null) {
            int screenX = getWorldX() - cameraX;
            int screenY = getWorldY() - cameraY;
            g.drawImage(image, screenX, screenY, 80, 80, null);
        }
    }
}