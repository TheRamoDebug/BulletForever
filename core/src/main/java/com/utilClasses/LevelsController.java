package com.utilClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LevelsController {
    private int wave = 0;
    private boolean waveCreated = false;
    private int wavePhase = 0;
    private float waveTimer = 0f;
    private static final float FIRST_WAVE_DELAY = 5f;

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;

    private float movementBackground = 0;
    private float oscillation = 0;
    private float cont = -3f;


    public void selectLevel(SpriteBatch c, ControllerEnemies ce, ControllerBullets cbEnemy, Texture fondo, Sprite enemySprite, float delta, int level, Vector2 playerPosition){
        switch (level){
            case 1 -> {
                level1(c, ce, cbEnemy, fondo, enemySprite, delta, playerPosition);
            }
            case 2 ->{
                level2(c, ce, cbEnemy, fondo,enemySprite,delta);
            }

            case 3-> {
                level3(c, ce, cbEnemy, fondo,enemySprite,delta);
            }

            case 4-> {
                level4(c, ce, cbEnemy, fondo,enemySprite,delta);
            }

            default -> {

            }
        }


    }


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
        oscillation += delta * 0.5f;
        waveTimer += delta;

        //WAVE 1
        if (wave == 0 && !waveCreated && waveTimer >= FIRST_WAVE_DELAY) {

            float startY = WORLD_HEIGHT + 1f;
            for (int row = 0; row < 4; row++) {
                float y = startY + (3 - row) * 0.9f;

                ClassEnemy enemyLeft = new ClassEnemy(100, 20, 4, new Vector2(7.2f, y), 2, oscillation, 0, 1, ClassEnemy.ShotPattern.TARGETED, true);
                enemyLeft.waveRow = row;
                enemyLeft.targetHeight = 5.5f + row*0.55f;

                ClassEnemy enemyRight = new ClassEnemy(100, 20, 4, new Vector2(8.8f, y), 2, oscillation, 0, 1, ClassEnemy.ShotPattern.TARGETED,  true);
                enemyRight.waveRow = row;
                enemyRight.targetHeight = 5.5f + row*0.55f;

                enemyLeft.leaveDelay = 3f;
                enemyRight.leaveDelay = 3f;

                ce.addEnemy(enemyLeft);
                ce.addEnemy(enemyRight);
            }
            waveCreated = true;
        }

        //WAVE 2 - phase 1
            if (wave == 1 && !waveCreated) {
                float startY = WORLD_HEIGHT + 1f;
                for (int row = 0; row < 3; row++) {
                    float y = startY + (2 + row) * 0.9f;

                    float leftX = 7.2f - row * 1.5f;
                    float rightX = 8.8f + row * 1.5f;

                    ClassEnemy enemyLeft = new ClassEnemy(100, 20, 4,new Vector2(leftX,y), 2, oscillation, 0, 1, ClassEnemy.ShotPattern.TARGETED, false);
                    ClassEnemy enemyRight = new ClassEnemy(100, 20, 4, new Vector2(rightX, y), 2, oscillation, 0, 1, ClassEnemy.ShotPattern.TARGETED,  false);

                    enemyLeft.waveRow = row;
                    enemyRight.waveRow = row;

                    enemyLeft.entryDelay = row * 0.5f;
                    enemyRight.entryDelay = row * 0.5f;

                    enemyLeft.targetHeight = 4.5f + row*0.8f;
                    enemyRight.targetHeight = 4.5f + row*0.8f;

                    enemyLeft.singleShot = true;
                    enemyRight.singleShot = true;

                    enemyLeft.leaveDelay = 2f;
                    enemyRight.leaveDelay = 2f;

                    enemyLeft.shotOrder = row * 2;
                    enemyRight.shotOrder = row * 2+1;

                    ce.addEnemy(enemyLeft);
                    ce.addEnemy(enemyRight);
                }
                waveCreated = true;
            }

        ce.movementEnemies(delta, oscillation, c, enemySprite, cbEnemy, playerPosition);

        if(waveCreated && ce.getEnemiesCount() == 0) {
            wave++;
            waveCreated = false;
        }

    }



    public void level2(SpriteBatch c, ControllerEnemies ce, ControllerBullets cbEnemy, Texture fondo, Sprite enemySprite, float delta){


    }

    public void level3(SpriteBatch c, ControllerEnemies ce, ControllerBullets cbEnemy, Texture fondo, Sprite enemySprite, float delta){


    }

    public void level4(SpriteBatch c, ControllerEnemies ce, ControllerBullets cbEnemy, Texture fondo, Sprite enemySprite, float delta){


    }

}
