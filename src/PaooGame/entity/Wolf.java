package PaooGame.entity;

import PaooGame.main.CollisionChecker;
import PaooGame.main.Game;
import java.awt.*;
import java.util.ArrayList;

public class Wolf extends Entity implements InteractiaCuZone{

    public int razaVizuala = 300;
    public int distantaOprire = 85;
    private static final int ACTION_INTERVAL = 120;

    private boolean atac = false;
    private int attackCounter = 0;
    private int attackSpriteNum = 0;

    private ArrayList<Rectangle> zoneInterzise = new ArrayList<>();

    public Wolf(int startX, int startY) {
        this.worldX = startX;
        this.worldY = startY;
        this.speed = 2;
        this.direction = "down";
        this.solidArea = new Rectangle();
        this.solidArea.x = 10;
        this.solidArea.y = 20;
        this.solidArea.width = 44;
        this.solidArea.height = 40;

        this.maxHp = 1;
        this.hp = this.maxHp;

        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
    }

    @Override
    public void primesteZonaSpeciala(int colPoarta, int randPoarta, int latimeInDale, int inaltimeInDale) {
        int tileSize = PaooGame.main.Game.TILE_SIZE;
        int poartaPixelX = colPoarta * tileSize;
        int poartaPixelY = randPoarta * tileSize;
        int poartaPixelLatime = latimeInDale * tileSize;
        int poartaPixelInaltime = inaltimeInDale * tileSize;

        this.zoneInterzise.add(new java.awt.Rectangle(poartaPixelX, poartaPixelY, poartaPixelLatime, poartaPixelInaltime));
    }

    private void verificaZoneInterzise() {
        int viitorX = this.worldX + this.solidArea.x;
        int viitorY = this.worldY + this.solidArea.y;

        switch(direction) {
            case "up": viitorY -= speed; break;
            case "down": viitorY += speed; break;
            case "left": viitorX -= speed; break;
            case "right": viitorX += speed; break;
        }

        Rectangle hitboxViitor = new Rectangle(viitorX, viitorY, solidArea.width, solidArea.height);

        for (Rectangle zona : zoneInterzise) {
            if (hitboxViitor.intersects(zona)) {
                this.collisionOn = true;
                break;
            }
        }
    }

    @Override
    public void update(CollisionChecker checker, ArrayList<Entity> entitati) {

        if (invincibil) {
            invincibilCounter++;
            if (invincibilCounter > 30) {
                invincibil = false;
                invincibilCounter = 0;
            }
        }

        if (isDead()) {
            return;
        }

        Entity tinta = null;
        double distantaMinima = razaVizuala;

        Player bogo = Player.getInstance();
        if (bogo != null) {
            double distBogo = Math.sqrt(Math.pow(bogo.getWorldX() - this.worldX, 2) + Math.pow(bogo.getWorldY() - this.worldY, 2));
            if (distBogo < distantaMinima) {
                distantaMinima = distBogo;
                tinta = bogo;
            }
        }

        for (Entity e : entitati) {
            if (e instanceof Sheep && !e.isDead()) {
                if (((Sheep) e).isEsteSalvata()) {
                    continue;
                }

                double distOaie = Math.sqrt(Math.pow(e.getWorldX() - this.worldX, 2) + Math.pow(e.getWorldY() - this.worldY, 2));
                if (distOaie < distantaMinima) {
                    distantaMinima = distOaie;
                    tinta = e;
                }
            }
        }

        boolean seMisca = true;

        if (tinta != null) {
            int diffX = Math.abs(tinta.getWorldX() - this.worldX);
            int diffY = Math.abs(tinta.getWorldY() - this.worldY);

            if (distantaMinima <= distantaOprire) {
                seMisca = false;

                if (Math.abs(tinta.getWorldX() - this.worldX) > Math.abs(tinta.getWorldY() - this.worldY)) {
                    direction = (this.worldX > tinta.getWorldX()) ? "left" : "right";
                } else {
                    direction = (this.worldY < tinta.getWorldY()) ? "down" : "up";
                }

                if (!atac) {
                    atac = true;
                    attackSpriteNum = 0;
                    attackCounter = 0;
                }

                attackCounter++;
                if (attackCounter > 10) {
                    attackSpriteNum++;

                    if (attackSpriteNum == 2 && !tinta.isDead()) {
                        tinta.primesteDamage(1);
                    }

                    if (attackSpriteNum > 4) {
                        attackSpriteNum = 0;
                    }
                    attackCounter = 0;
                }
            }
            else {
                atac = false;
                attackSpriteNum = 0;

                int toleranta = 20;
                if (direction.equals("left") || direction.equals("right")) {
                    if (diffY > diffX + toleranta) {
                        direction = (this.worldY < tinta.getWorldY()) ? "down" : "up";
                    } else {
                        direction = (this.worldX < tinta.getWorldX()) ? "right" : "left";
                    }
                } else {
                    if (diffX > diffY + toleranta) {
                        direction = (this.worldX < tinta.getWorldX()) ? "right" : "left";
                    } else {
                        direction = (this.worldY < tinta.getWorldY()) ? "down" : "up";
                    }
                }
            }
        }
        else {
            atac = false;
            alegeDirectieAleatorie(ACTION_INTERVAL);
        }

        if (seMisca) {
            collisionOn = false;

            checker.checkTile(this);
            checker.checkEntity(this, entitati);

            verificaZoneInterzise();

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum++;
                if (direction.equals("up") || direction.equals("down")) {
                    if (spriteNum > 3) spriteNum = 0;
                } else {
                    if (spriteNum > 4) spriteNum = 0;
                }
                spriteCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        java.awt.image.BufferedImage image = null;

        if (atac) {
            if (direction.equals("left")) {
                image = PaooGame.Graphics.Assets.wolf_atac_stanga[attackSpriteNum];
            } else {
                image = PaooGame.Graphics.Assets.wolf_atac_dreapta[attackSpriteNum];
            }
        } else {
            switch(direction) {
                case "up":    image = PaooGame.Graphics.Assets.wolf_sus[spriteNum]; break;
                case "down":  image = PaooGame.Graphics.Assets.wolf_jos[spriteNum]; break;
                case "left":  image = PaooGame.Graphics.Assets.wolf_stanga[spriteNum]; break;
                case "right": image = PaooGame.Graphics.Assets.wolf_dreapta[spriteNum]; break;
            }
        }

        if (image != null) {
            int screenX = worldX - cameraX;
            int screenY = worldY - cameraY;

            double scale = 2.0;
            int drawWidth = (int) (image.getWidth() * scale);
            int drawHeight = (int) (image.getHeight() * scale);

            int offsetX = 0;
            int offsetY = 0;

            if (atac) {
                if (direction.equals("left")) {
                    offsetX = (int)(drawWidth * 0.4);
                } else {
                    offsetX = -(int)(drawWidth * 0.4);
                }
                offsetY = 15;
            } else {
                int marimeStandard = 80;
                offsetX = (marimeStandard - drawWidth) / 2;
                offsetY = marimeStandard - drawHeight;
            }

            g.drawImage(image, screenX + offsetX, screenY + offsetY, drawWidth, drawHeight, null);
        }
    }
}