package PaooGame.entity;

import PaooGame.input.KeyHandler;
import PaooGame.input.MouseHandler;
import PaooGame.main.CollisionChecker;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    private static final int DEFAULT_SPEED = 4;
    private static final int ATTACK_DURATION = 10;
    private static final double ATTACK_SCALE = 1.8;
    private static final int PLAYER_SIZE = 145;
    private static final int ANIMATION_SPEED = 12;
    private static final int MAX_WALK_FRAMES = 3;
    private static final int MAX_ATTACK_FRAMES = 2;

    private boolean aLovitDeja = false;

    private KeyHandler keyH;
    private MouseHandler mouseH;

    private boolean attacking = false;
    private int attackCounter = 0;

    private static Player instance = null;

    public static Player getInstance(int screenW, int screenH, KeyHandler keyH, MouseHandler mouseH) {
        if (instance == null) {
            instance = new Player(screenW, screenH, keyH, mouseH);
        }
        return instance;
    }

    public static Player getInstance() {
        return instance;
    }

    private Player(int screenW, int screenH, KeyHandler keyH, MouseHandler mouseH) {
        this.keyH = keyH;
        this.mouseH = mouseH;

        worldX = 100;
        worldY = 100;
        speed = DEFAULT_SPEED;
        direction = "down";

        this.maxHp = 6;
        this.hp = this.maxHp;

        solidArea = new Rectangle();
        solidArea.x = -2;
        solidArea.y = -2;
        solidArea.width = 68;
        solidArea.height = 68;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void omoara() {
        super.omoara();
        this.spriteNum = 0;
        this.spriteCounter = 0;
    }
    public void invie() {
        this.hp = this.maxHp;
        this.spriteNum = 0;
        this.spriteCounter = 0;
        this.attacking = false;
        this.dead = false;
    }

    @Override
    public void update(CollisionChecker checker, ArrayList<Entity> entitati) {

        if (isDead()) {
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum < 2) {
                    spriteNum++;
                }
                spriteCounter = 0;
            }
            return;
        }

        if (invincibil) {
            invincibilCounter++;
            if (invincibilCounter > 120) {
                invincibil = false;
                invincibilCounter = 0;
            }
        }

        verificaAtac();

        if (attacking) {
            executaAtac(entitati);
        } else {
            verificaMiscare(checker, entitati);
        }
    }

    private void verificaAtac() {
        if (mouseH.isLeftClicked() && !attacking) {
            attacking = true;
            spriteNum = 0;
            attackCounter = 0;
            aLovitDeja = false;
        }
    }

    private void executaAtac(ArrayList<Entity> entitati) {
        attackCounter++;

        if (spriteNum == 1 && !aLovitDeja) {

            int bogoCentruX = this.worldX + this.solidArea.x + (this.solidArea.width / 2);
            int bogoCentruY = this.worldY + this.solidArea.y + (this.solidArea.height / 2);

            int raza = 80;

            Rectangle hitboxAtac = new Rectangle(
                    bogoCentruX - raza,
                    bogoCentruY - raza,
                    raza * 2,
                    raza * 2
            );

            for (Entity e : entitati) {
                if (e != this && !e.isDead() && e instanceof Wolf) {

                    int eHitX = e.getWorldX() + e.solidArea.x;
                    int eHitY = e.getWorldY() + e.solidArea.y;
                    Rectangle hitboxEntitate = new Rectangle(eHitX, eHitY, e.solidArea.width, e.solidArea.height);

                    if (hitboxAtac.intersects(hitboxEntitate)) {
                        e.primesteDamage(1);
                    }
                }
            }

            aLovitDeja = true;
        }

        if (attackCounter > ATTACK_DURATION) {
            spriteNum++;
            attackCounter = 0;

            if (spriteNum > MAX_ATTACK_FRAMES) {
                spriteNum = 0;
                attacking = false;
                mouseH.setLeftClicked(false);
            }
        }
    }

    private void verificaMiscare(CollisionChecker checker, ArrayList<Entity> entitati) {
        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {

            if (keyH.isUpPressed()) { direction = "up"; }
            else if (keyH.isDownPressed()) { direction = "down"; }
            else if (keyH.isLeftPressed()) { direction = "left"; }
            else if (keyH.isRightPressed()) { direction = "right"; }

            collisionOn = false;

            checker.checkTile(this);
            boolean lovitZid = collisionOn;

            int indexNPC = checker.checkEntity(this, entitati);

            if (!lovitZid) {
                switch (direction) {
                    case "up":    worldY -= speed; break;
                    case "down":  worldY += speed; break;
                    case "left":  worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            actualizeazaAnimatiaMers();
        } else {
            spriteNum = 0;
        }
    }

    private void actualizeazaAnimatiaMers() {
        spriteCounter++;
        if (spriteCounter > ANIMATION_SPEED) {
            spriteNum++;
            if (spriteNum > MAX_WALK_FRAMES) spriteNum = 0;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage image = null;

        if (isDead()) {
            int indexMort = Math.min(spriteNum, 2);
            image = PaooGame.Graphics.Assets.bogo_mort[indexMort];
        }
        else if (invincibil) {
            if (invincibilCounter <= 30) {
                int indexRanit = (invincibilCounter / 10) % 3;
                image = PaooGame.Graphics.Assets.bogo_ranit[indexRanit];
            } else {
                if (invincibilCounter % 10 < 5) {
                    image = null;
                } else {
                    switch (direction) {
                        case "up":    image = PaooGame.Graphics.Assets.bogo_mers_sus[spriteNum]; break;
                        case "down":  image = PaooGame.Graphics.Assets.bogo_mers_jos[spriteNum]; break;
                        case "left":  image = PaooGame.Graphics.Assets.bogo_mers_stanga[spriteNum]; break;
                        case "right": image = PaooGame.Graphics.Assets.bogo_mers_dreapta[spriteNum]; break;
                    }
                }
            }
        }
        else if (attacking) {
            image = PaooGame.Graphics.Assets.bogo_atac[spriteNum];
        }
        else {
            switch (direction) {
                case "up":    image = PaooGame.Graphics.Assets.bogo_mers_sus[spriteNum]; break;
                case "down":  image = PaooGame.Graphics.Assets.bogo_mers_jos[spriteNum]; break;
                case "left":  image = PaooGame.Graphics.Assets.bogo_mers_stanga[spriteNum]; break;
                case "right": image = PaooGame.Graphics.Assets.bogo_mers_dreapta[spriteNum]; break;
            }
        }

        if (image != null) {
            int bogoScreenX = worldX - cameraX;
            int bogoScreenY = worldY - cameraY;

            if (attacking) {
                int drawWidth = (int) (image.getWidth() * ATTACK_SCALE);
                int drawHeight = (int) (image.getHeight() * ATTACK_SCALE);
                int offsetX = (PLAYER_SIZE - drawWidth) / 2;
                int offsetY = PLAYER_SIZE - drawHeight;
                g.drawImage(image, bogoScreenX + offsetX, bogoScreenY + offsetY, drawWidth, drawHeight, null);
            } else {
                g.drawImage(image, bogoScreenX, bogoScreenY, PLAYER_SIZE, PLAYER_SIZE, null);
            }
        }
    }
}