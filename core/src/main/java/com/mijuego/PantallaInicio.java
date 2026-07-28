package com.mijuego;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import io.github.com.mygdx.game.Main;

public class PantallaInicio implements Screen {
    private final Main game;

    public PantallaInicio(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.2f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)){
            System.out.println("BINOMO") ;
            game.setScreen(new PantallaGameplay(game));
            this.dispose();
        }


        game.batch.begin();


        game.batch.end();


    }

    @Override public void resize(int width, int height){}


    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void hide(){}

    @Override
    public void dispose(){}
}
