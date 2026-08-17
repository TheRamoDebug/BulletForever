package com.utilClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class LevelsController {
    private int[] enemysForLevel = {4,8,12,16,20,24,28,32,36,40};
    private int[] contLevel = {};
    private int i = 0;
    private int contEnemyLevel = 0;

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;

    private float movementBackground = 0;
    private float oscillation = 0;
    private float cont = -8;


    public void BackgroundChange(SpriteBatch c, Texture fondo , float delta){
        movementBackground += delta * 20;

        c.draw(fondo, 0, -movementBackground, WORLD_WIDTH, WORLD_HEIGHT);
        c.draw(fondo, 0, 9f - movementBackground, WORLD_WIDTH, WORLD_HEIGHT);

        if (movementBackground >= 9f) {
            movementBackground = 0;
        }
    }



    public void level1(SpriteBatch c, ControllerEnemies ce, ControllerBullets cbEnemy, Texture fondo, Sprite enemySprite, float delta, Vector2 playerPosition){
        BackgroundChange(c, fondo, delta);

        cont += delta;

        oscillation += delta * 0.5;

        if (cont > 0.2 && enemysForLevel[i] != contEnemyLevel){
            cont = 0;
            oscillation += 1.5;

            float spawnX = 1f + MathUtils.random(WORLD_WIDTH - 2f);
            float spawnY = WORLD_HEIGHT + 1f;
            Vector2 spawnPosition = new Vector2(spawnX, spawnY);

            ClassEnemy auxiliarEnemy = new ClassEnemy(100, 20, 4, spawnPosition, 3, oscillation, 1, true);
            auxiliarEnemy.targetHeight = WORLD_HEIGHT / 2f;
            ce.addEnemy(auxiliarEnemy);

            contEnemyLevel += 1;
        }

        if(contEnemyLevel >= enemysForLevel[i] - (i == 0 ? 0 : enemysForLevel[i-1]) && i < 9){
            i += 1;
            contEnemyLevel = 0;
        }


        ce.movementEnemies(delta, oscillation,c, enemySprite, cbEnemy, 1, playerPosition);

    }



    public void level2(){


    }




}
