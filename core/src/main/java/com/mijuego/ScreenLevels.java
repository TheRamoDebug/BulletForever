package com.mijuego;

import com.ScreensClasses.LevelsScreenClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.com.mygdx.game.Main;

public class ScreenLevels implements Screen {
    private final Main game;

    private Sprite background;
    private Sprite Title;
    private Sprite Thunder;

    private Texture iconlevel1;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;

    private float superCont = 1;
    private float cont = 0;


    private Stage stage;
    private Image imagenLevel1;
    private Image imagenLevel2;
    private Image imagenLevel3;
    private Image imagenLevel4;



    private LevelsScreenClass screenClass = new LevelsScreenClass();
    public ScreenLevels(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT ,camera);

        stage = new Stage(new StretchViewport(1280, 720));
        Gdx.input.setInputProcessor(null);
        iconlevel1 = new Texture("things/iconolevel1.jpg");
        imagenLevel1 = new Image(iconlevel1);
        imagenLevel2 = new Image(iconlevel1);
        imagenLevel3 = new Image(iconlevel1);
        imagenLevel4 = new Image(iconlevel1);





        Thunder = new Sprite(new Texture("things/thunderV2.png"));
        Title = new Sprite(new Texture("BackgroundsEtc/TitleLevels.png"));
        background = new Sprite(new Texture("BackgroundsEtc/BackgroundScreenLevels.png"));

        screenClass.organizedImmages(imagenLevel1,imagenLevel2,imagenLevel3,imagenLevel4,stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f,0f,0f,1f);

        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        screenClass.Background(background,Thunder, delta, game.batch);


        cont += delta;

        screenClass.menuTitle(game.batch, Title ,cont, delta);

        game.batch.end();




        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        game.shapeRenderer.setProjectionMatrix(camera.combined);

        superCont = screenClass.shapeRenderer(superCont,delta,game);


        stage.act(delta);

        stage.draw();

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height);
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
        Title.getTexture().dispose();
        Thunder.getTexture().dispose();
        iconlevel1.dispose();
        stage.dispose();

    }

}
