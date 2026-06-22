package PaooGame.entity;

import PaooGame.event.GameObserver;
import PaooGame.event.GameSubject;
import PaooGame.main.CollisionChecker;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Entity implements GameSubject {
    protected int worldX, worldY;
    protected int speed;
    protected String direction;
    protected int spriteCounter = 0;
    protected int spriteNum = 0;
    protected boolean dead = false;

    protected Rectangle solidArea;
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected boolean collisionOn = false;

    protected int maxHp;
    protected int hp;

    protected boolean invincibil = false;
    protected int invincibilCounter = 0;

    protected int actionLockCounter = 0;
    protected java.util.Random random = new java.util.Random();

    public int getHp() { return hp; }
    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp > maxHp) this.hp = maxHp;
        if (this.hp <= 0) {
            this.hp = 0;
            omoara();
        }
    }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public boolean isInvincibil() { return invincibil; }
    public void setInvincibil(boolean invincibil) { this.invincibil = invincibil; }

    public Rectangle getSolidArea() { return solidArea; }

    public boolean isDead() { return dead; }
    public int getWorldX() { return worldX; }
    public void setWorldX(int x) { this.worldX = x; }
    public int getWorldY() { return worldY; }
    public void setWorldY(int y) { this.worldY = y; }
    public String getDirection() { return direction; }
    public int getSpeed() { return speed; }
    public boolean isCollisionOn() { return collisionOn; }
    public void setCollisionOn(boolean val) { this.collisionOn = val; }

    protected void alegeDirectieAleatorie(int actionInterval) {
        actionLockCounter++;
        if (actionLockCounter >= actionInterval) {
            int i = random.nextInt(100) + 1;
            if (i <= 25) { direction = "up"; }
            else if (i <= 50) { direction = "down"; }
            else if (i <= 75) { direction = "left"; }
            else { direction = "right"; }
            actionLockCounter = 0;
        }
    }

    public void primesteDamage(int damage) {
        if (!invincibil && !dead) {
            this.hp -= damage;
            this.invincibil = true;
            if (this.hp <= 0) {
                this.hp = 0;
                omoara();
            }
        }
    }

    public Rectangle getHitboxPredictiv(int offsetX, int offsetY) {
        return new Rectangle(
                this.worldX + this.solidArea.x + offsetX,
                this.worldY + this.solidArea.y + offsetY,
                this.solidArea.width,
                this.solidArea.height
        );
    }

    public abstract void update(CollisionChecker checker, ArrayList<Entity> entitati);
    public abstract void draw(Graphics g, int cameraX, int cameraY);

    private ArrayList<GameObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(GameObserver o) { observers.add(o); }
    @Override
    public void removeObserver(GameObserver o) { observers.remove(o); }
    @Override
    public void notifyObservers(String eventType) {
        for (GameObserver o : observers) {
            o.onEvent(eventType, this);
        }
    }

    public void omoara() {
        this.dead = true;
        notifyObservers("ENTITY_DIED");
    }
}