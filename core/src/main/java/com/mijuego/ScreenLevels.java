package com.mijuego;

import com.ScreensClasses.LevelsScreenClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.com.mygdx.game.Main;

public class ScreenLevels implements Screen {
    private final Main game;

    private Sprite background;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;

    private float superCont = 1;


    private LevelsScreenClass screenClass = new LevelsScreenClass();
    public ScreenLevels(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT ,camera);

        background = new Sprite(new Texture("BackgroundsEtc/BackgroundScreenLevels.png"));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f,0f,0f,1f);

        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        screenClass.Background(background, delta, game.batch);


        game.batch.end();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        game.shapeRenderer.setProjectionMatrix(camera.combined);

        superCont = screenClass.shapeRenderer(superCont,delta,game);


    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}




    @Override
    public void dispose() {
        background.getTexture().dispose();
    }
}
