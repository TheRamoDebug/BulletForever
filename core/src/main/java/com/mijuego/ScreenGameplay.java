package com.mijuego;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import io.github.com.mygdx.game.Main;
import com.utilClasses.*;



public class ScreenGameplay implements Screen {
    private final Main game;
    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;


    private OrthographicCamera cameraFirst;
    private OrthographicCamera cameraSecond;
    private OrthographicCamera cameraThird;
    private Viewport viewportFirst;
    private Viewport viewportSecond;
    private Viewport viewportThird;


    private Sprite plane;
    private Sprite enemySprite;
    private Texture bullet;
    private Texture background;
    private Texture backTexture;
    private Texture enemyTexture;

    private ControllerBullets bulletsPlayer;
    private ControllerBullets bulletsEnemy;
    private ControllerEnemies controllerMoreEnemies;


    private Rectangle colisionPlayer;


    private LevelsController controllerLevelsNew;
    private GameOverOverlay gameOverOverlay;
    private StatsClass statsClass;
    private Controls newController;


    private float superCont = 1;
    private float deltaFinal;
    private int level = 0;
    private Vector2 movementPlayer;



    public ScreenGameplay(Main game){
        this.game = game;

    }

    public ScreenGameplay(Main game, int level){
        this.game = game;
        this.level = level;
    }



    @Override
    public void show(){

        if(level == 0){
            level = Player.getNumberLevel();
        }
        MasterClass.stopMusicMenu();
        MasterClass.backgroundMusicGameplay();

        controllerLevelsNew = new LevelsController();

        cameraFirst = new OrthographicCamera();
        cameraSecond = new OrthographicCamera();
        cameraThird = new OrthographicCamera();


        //this is the two viewports
        viewportFirst = new StretchViewport(WORLD_WIDTH , WORLD_HEIGHT, cameraFirst);
        viewportSecond = new StretchViewport(800,600, cameraSecond);
        viewportThird = new StretchViewport(1280, 720, cameraThird);
        gameOverOverlay = new GameOverOverlay(viewportThird, game);

        movementPlayer = new Vector2(0f,0f);
        newController = new Controls();

        enemyTexture = new Texture("Sprites/Enemy1.png");
        bullet = new Texture("Sprites/disparoNew.png");
        background = new Texture("BackgroundsEtc/backgroundGameplay.jfif");
        backTexture = new Texture("BackgroundsEtc/backgroundStats.jpg");
        plane = new Sprite(new Texture("Sprites/playerPlane.png"));
        enemySprite = new Sprite (enemyTexture);
        enemySprite.flip(false,true);



        bulletsPlayer = new ControllerBullets();
        bulletsEnemy = new ControllerBullets();

        controllerMoreEnemies = new ControllerEnemies();

        colisionPlayer = new Rectangle();

        statsClass = new StatsClass(viewportSecond, game.batch);

        Player.setHealth(3);
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        if (Player.isAlive()){
            deltaFinal = delta;
        }else{
            deltaFinal = 0;
        }

        game.batch.setColor(Color.WHITE);


        //put the viewport, configure the matrix, and another thing xd
        viewportFirst.apply();
        game.batch.setProjectionMatrix(cameraFirst.combined);
        game.batch.enableBlending();


        game.batch.begin();

        statsClass.actu();

        //controller for the levels and more (like draw enemies, shots, draw the background and move the enemies)
        controllerLevelsNew.selectLevel(game.batch, controllerMoreEnemies, bulletsEnemy, background, enemySprite, deltaFinal, level, movementPlayer);


        //functions for draw bullets and collides
        bulletsPlayer.drawBulletsAndCollide(game.batch, bullet, controllerMoreEnemies, statsClass);
        bulletsEnemy.drawBulletsEnemies(game.batch, bullet, colisionPlayer);



        //functions for player
        game.batch.draw(plane, movementPlayer.x, movementPlayer.y, 0.8f, 0.8f );
        colisionPlayer.set(movementPlayer.x + 0.4f - ((0.8f / 2) / 2) , movementPlayer.y, 0.8f / 2, 0.8f);

        if (Player.isAlive()) {
            newController.controlsKeysShots(bulletsPlayer, movementPlayer, MasterClass.getShotPlayer());
            movementPlayer = newController.controlsKeys(movementPlayer, deltaFinal);
        }

        bulletsEnemy.updateScreen(deltaFinal, WORLD_WIDTH, WORLD_HEIGHT);
        bulletsPlayer.updateScreen(deltaFinal, WORLD_WIDTH,WORLD_HEIGHT);


        game.batch.end();


        if (!Player.isAlive()) {
            Gdx.input.setInputProcessor(gameOverOverlay.getStage());
            gameOverOverlay.render(delta);
            gameOverOverlay.shapeRenderer(game.shapeRenderer, delta);
        }


        viewportSecond.apply();
        game.batch.setProjectionMatrix(cameraSecond.combined);

        game.batch.begin();
        game.batch.draw(backTexture, 0,0, viewportSecond.getWorldWidth(),viewportSecond.getWorldHeight());
        game.batch.end();

        statsClass.render(delta);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        viewportFirst.apply();
        game.shapeRenderer.setProjectionMatrix(cameraFirst.combined);



        if ( superCont > 0){
            superCont -= delta * 0.2;
        }else{
            superCont = 0;
        }



        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(new Color(0f, 0f, 0f, superCont));
        game.shapeRenderer.rect(0, 0, 1280,720);
        game.shapeRenderer.end();

    }





    @Override
    public void resize(int width, int height) {

        int widthFirst = (int) (width * 0.75f);
        int widthSecond = width - widthFirst;

        viewportFirst.update(widthFirst, height, true);
        viewportFirst.setScreenBounds(0, 0, widthFirst, height);

        viewportSecond.update(widthSecond, height, true);
        viewportSecond.setScreenBounds(widthFirst, 0, widthSecond, height);

        viewportThird.update(widthFirst, height, true);
        viewportThird.setScreenBounds(0, 0,widthFirst, height);
    }


    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}



    @Override
    public void dispose() {
        if (plane != null && plane.getTexture() != null) plane.getTexture().dispose();
        if (bullet != null) bullet.dispose();
        if (background != null) background.dispose();
        if (backTexture != null) backTexture.dispose();
        if (enemyTexture != null) enemyTexture.dispose();
        if (gameOverOverlay != null) gameOverOverlay.dispose();
        if (statsClass != null) statsClass.dispose();
    }


}
