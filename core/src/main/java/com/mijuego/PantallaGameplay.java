package com.mijuego;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import io.github.com.mygdx.game.Main;
import com.clasesUtiles.*;


public class PantallaGameplay implements Screen {
    public enum EstadoJuego {
        JUGANDO,
        GAME_OVER;
    }

    private final Main game;
    private OrthographicCamera cameraFirst;
    private OrthographicCamera cameraSecond;
    private Viewport viewportFirst;
    private Viewport viewportSecond;
    private Sprite avion;
    private Sprite enemySprite;
    private ControladorBalas bulletsPlayer;
    private ControladorBalas bulletsEnemy;
    private ControllerEnemies controllerMoreEnemys;
    private Texture balaImagen;
    private Texture fondo;
    private Texture backTexture;
    private controls newControler;
    private Rectangle colisionPlayer;
    private ShapeRenderer shapeRenderer;
    private ClassEnemy enemyObject;
    private int i = 0;
    private float oscilacion;
    private LevelsController controllerLevelsNew;
    private EstadoJuego estadoActual;
    private GameOverOverlay gameOverOverlay;

    private float cont = 0;
    private float superCont = 1;

    private Vector2 movementPlayer;
    private Vector2 movementEnemys;

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;


    private Sound shotPlayer;
    private Music backgroundMusic;


    public PantallaGameplay(Main game){
        this.game = game;
        Gdx.app.log("hola pantalla gameplay", "sopas");
    }

    @Override
    public void show(){
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backMusic.mp3"));

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        shotPlayer = Gdx.audio.newSound(Gdx.files.internal("shotSound.mp3"));

        controllerLevelsNew = new LevelsController();

        estadoActual = EstadoJuego.JUGANDO;

        cameraFirst = new OrthographicCamera();
        cameraSecond = new OrthographicCamera();


        //this is the two viewports
        viewportFirst = new StretchViewport(WORLD_WIDTH , WORLD_HEIGHT, cameraFirst);
        viewportSecond = new StretchViewport(WORLD_WIDTH , WORLD_HEIGHT, cameraSecond);
        gameOverOverlay = new GameOverOverlay(viewportFirst);

        movementPlayer = new Vector2(0f,0f);
        movementEnemys = new Vector2(5f,4f);
        newControler = new controls();


        balaImagen = new Texture("disparoNew.png");
        fondo = new Texture("backSpace.jfif");
        backTexture = new Texture("difuminado.jpg");
        enemySprite = new Sprite (new Texture("Enemy1.png"));
        enemySprite.flip(false,true);
        avion = new Sprite(new Texture("playerPlane.png"));


        bulletsPlayer = new ControladorBalas();
        bulletsEnemy = new ControladorBalas();

        controllerMoreEnemys = new ControllerEnemies();

        colisionPlayer = new Rectangle();

        shapeRenderer = new ShapeRenderer();

        PlayerAuxiliar.setHealth(5);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);



        //update the camera
        cameraFirst.update();
        cameraSecond.update();

        //put the viewport, configure the matrix, and another thing xd
        viewportFirst.apply();
        game.batch.setProjectionMatrix(cameraFirst.combined);
        game.batch.enableBlending();


        game.batch.begin();


        float logicDelta = (estadoActual == EstadoJuego.JUGANDO) ? delta : 0f;

        //controller for the levels and more(like draw enemys, shots, draw the background and move the enemys
        controllerLevelsNew.level1(game.batch, controllerMoreEnemys,bulletsEnemy ,fondo, enemySprite, logicDelta);

        //functions for draw bullets and collides
        bulletsPlayer.drawBulletsAndCollide(game.batch, balaImagen, controllerMoreEnemys);
        bulletsEnemy.drawBulletsEnemies(game.batch, balaImagen, colisionPlayer);

        //functions for player (need rework)
        game.batch.draw(avion, movementPlayer.x, movementPlayer.y, 0.8f, 0.8f );
        colisionPlayer.set(movementPlayer.x + 0.4f - ((0.8f / 2) / 2) , movementPlayer.y, 0.8f / 2, 0.8f);

        if (estadoActual == EstadoJuego.JUGANDO) {
            newControler.controlsKeysShots(bulletsPlayer, movementPlayer, shotPlayer);
            movementPlayer = newControler.controlsKeys(movementPlayer, delta);
        }

        bulletsEnemy.actualizarPantalla(logicDelta, WORLD_WIDTH, WORLD_HEIGHT);
        bulletsPlayer.actualizarPantalla(logicDelta, WORLD_WIDTH,WORLD_HEIGHT);

        if (estadoActual == EstadoJuego.JUGANDO && PlayerAuxiliar.getHealth() <= 0) {
            estadoActual = EstadoJuego.GAME_OVER;
        }


        game.batch.end();
        if (estadoActual == EstadoJuego.GAME_OVER) {
            Gdx.input.setInputProcessor(gameOverOverlay.getStage());
            gameOverOverlay.render(delta);

            if (gameOverOverlay.seQuiereReintentar()) {
                PlayerAuxiliar.setHealth(5);
                controllerLevelsNew = new LevelsController();
                controllerMoreEnemys = new ControllerEnemies();
                gameOverOverlay.reset();
                estadoActual = EstadoJuego.JUGANDO;
                Gdx.input.setInputProcessor(null);
            }

            if (gameOverOverlay.seQuiereIrAlMenu()) {
                game.setScreen(new PantallaInicio(game));
            }
        }



        viewportSecond.apply();
        game.batch.setProjectionMatrix(cameraSecond.combined);

        game.batch.begin();

        game.batch.draw(backTexture, 0,0, WORLD_WIDTH,WORLD_HEIGHT);

        game.batch.end();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        viewportFirst.apply();
        shapeRenderer.setProjectionMatrix(cameraFirst.combined);


    if (estadoActual == EstadoJuego.JUGANDO) {
        if (superCont > 0) {
            superCont -= delta * 0.2;
        } else {
            superCont = 0;
        }


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0f, 0f, 0f, superCont));
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer.end();
    }


        if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            System.out.println(movementPlayer.x + "   " + movementPlayer.y);
            System.out.println(MathUtils.sin(oscilacion));
        }
    }





    @Override
    public void resize(int width, int height) {
        int halfWidth = width / 2;

        viewportFirst.update(halfWidth + halfWidth / 2, height, true);
        viewportFirst.setScreenBounds(0, 0, halfWidth + halfWidth / 2, height);

        viewportSecond.update(halfWidth, height, true);
        viewportSecond.setScreenBounds(halfWidth + halfWidth / 2, 0, halfWidth / 2, height);
    }


    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}



    @Override
    public void dispose() {
        if (avion != null) avion.getTexture().dispose();
        if (balaImagen != null) balaImagen.dispose();
        if (fondo != null) fondo.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (backgroundMusic != null) backgroundMusic.dispose();

    }


}
