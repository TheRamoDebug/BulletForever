package com.ScreensClasses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mijuego.ScreenLevels;
import io.github.com.mygdx.game.Main;

public class LevelsScreenClass {

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;


    private float movementBackground = 0;
    private float contAuxiliar = 0;
    private boolean state = true;



    public void Background(Sprite background, float delta, SpriteBatch c){
        movementBackground += delta * 12f;

        contAuxiliar += delta;

        c.setColor(Color.WHITE);

        c.draw(background, -movementBackground,0, WORLD_WIDTH, WORLD_HEIGHT * 1.2f);
        c.draw(background, 16f - movementBackground,0, WORLD_WIDTH, WORLD_HEIGHT * 1.2f);

        if (movementBackground >= 16f) {
            movementBackground = 0;
        }
    }


    public float shapeRenderer(float superCont, float delta, Main game){
        if(state) {
            if (superCont > 0) {
                superCont -= delta * 0.5f;
            } else {
                superCont = 0;
            }

            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            game.shapeRenderer.setColor(new Color(1f, 1f, 1f, superCont));
            game.shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            game.shapeRenderer.end();
        }
        if(!state){
            if (superCont <= 1) {
                superCont += delta * 0.5f;
            } else {
                superCont = 1;
                game.setScreen(new ScreenLevels(game));
            }

            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            game.shapeRenderer.setColor(new Color(1f, 1f, 1f, superCont));
            game.shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            game.shapeRenderer.end();
        }
        return superCont;
    }


}
