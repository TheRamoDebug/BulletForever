package com.utilClasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class ControllerEnemies {
    private final ArrayList<ClassEnemy> enemies = new ArrayList<>();


    public ControllerEnemies() {}

    public void addEnemy(ClassEnemy e){
        this.enemies.add(e);
    }

    public int getEnemiesCount(){return enemies.size();}


    public boolean isCollidingWithEnemy(ClassBullets bullet, StatsClass statsClass) {
        for (int i = enemies.size() - 1; i >= 0; i--) {

            if (bullet.collision.overlaps(enemies.get(i).collisionEnemy)) {
                enemies.get(i).health -= 10;
                if (enemies.get(i).health <= 0) {
                    statsClass.addI();
                    enemies.remove(i);
                }
                return true;
            }
        }

        return false;
    }


    public void  movementEnemies(float delta, float oscillation, SpriteBatch c, Sprite textureEnemy, ControllerBullets bala){

        for (int i = enemies.size() - 1; i >= 0; i--){
            enemies.get(i).newPosition(delta, oscillation,c,textureEnemy, enemies.get(i).typeOfMovement);
            enemies.get(i).drawEnemyAndShot(bala, delta);
        }

    }
}
