package com.utilClasses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

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


    public void movementEnemies(float delta, float oscillation, SpriteBatch c, Sprite textureEnemy, ControllerBullets bala, Vector2 playerPosition){

        for (int i = enemies.size() - 1; i >= 0; i--){

            if (enemies.get(i).isOutOfBounds()) {
                enemies.remove(i);
                continue;
            }

            enemies.get(i).newPosition(delta, oscillation, c, textureEnemy, enemies.get(i).typeOfMovement);
            enemies.get(i).drawEnemyAndShot(bala, delta, playerPosition);
        }

    }
}
