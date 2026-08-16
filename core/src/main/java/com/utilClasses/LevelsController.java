package com.utilClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class LevelsController {
    private int[] enemysForLevel = {4,8,12,16,20,24,28,32,36,40};
    private int[] contLevel = {};
    private int i = 0;
    private int contEnemyLevel = 0;

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;

    private float movementBackground = 0;
    private float oscillation = 0;
    private float cont = 5;


    private Vector2 movementEnemy = new Vector2(0, 0);



    public void BackgroundChange(SpriteBatch c, Texture fondo , float delta){
        movementBackground += delta * 20;

        c.draw(fondo, 0, -movementBackground, WORLD_WIDTH, WORLD_HEIGHT);
        c.draw(fondo, 0, 9f - movementBackground, WORLD_WIDTH, WORLD_HEIGHT);

        if (movementBackground >= 9f) {
            movementBackground = 0;
        }
    }



    public void level1(SpriteBatch c, ControllerEnemies ce, ControllerBullets cbEnemy, Texture fondo, Sprite enemySprite, float delta){
        BackgroundChange(c, fondo, delta);

        cont += delta;

        oscillation += delta * 0.5;

        if (cont > 0.2 && enemysForLevel[i] != contEnemyLevel){
            cont = 0;
            oscillation += 1.5;
            ClassEnemy auxiliarEnemy = new ClassEnemy(100, 20, 4, movementEnemy, 3, oscillation, 1);
            ce.addEnemy(auxiliarEnemy);

            contEnemyLevel += 1;
        }

        if(ce.getEnemiesCount() == 0 && i < 9){
            i += 1;
            contEnemyLevel = 0;
        }


        ce.movementEnemies(delta, oscillation,c, enemySprite, cbEnemy, 1);

    }



    public void level2(){


    }




}
