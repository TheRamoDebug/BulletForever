package com.clasesUtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

public class ControllerEnemies {
    private ArrayList<ClassEnemy> enemies = new ArrayList<>();
    private ClassEnemy enemyAuxiliar;


    public ControllerEnemies() {}

    public void addEnemy(ClassEnemy e){
        this.enemies.add(e);
    }


    public boolean isCollidingWithEnemy(ClaseBalas bullet) {
        if (enemies.size() != 0) {
            for (int i = 0; i < enemies.size(); i++) {

                if (bullet.collision.overlaps(enemies.get(i).collisionEnemy)) {
                    enemies.get(i).health -= 10;
                    if (enemies.get(i).health <= 0) {
                        enemies.remove(i);

                        System.out.println("ENEMIGOS VIVOS: " + enemies.size());
                    }
                    return true;
                }
            }
        }
        return false;
    }
    public void  movementEnemies(float delta, float oscilation, SpriteBatch c, Sprite textureEnemy, ControladorBalas bala){

        for (int i = enemies.size() - 1; i >= 0; i--){
            enemies.get(i).newPosition(delta, oscilation);
            enemies.get(i).drawEnemyAndShot(c,textureEnemy,bala);
        }

    }
}
