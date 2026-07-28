package com.mijuego;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    private float movementBackground = 0;
    private float positionBackground1 = 0;
    private float positionBackground2 = -9f;

    private boolean level1 = true;
    private float cont = 0;
    private float enemyTotal = 25;
    private float superCont = 1;

    private Vector2 movementPlayer;
    private Vector2 movementEnemys;

    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;



    private Music backgroundMusic;


    public PantallaGameplay(Main game){
        this.game = game;
        Gdx.app.log("hola pantalla gameplay", "sopas");
    }

    @Override
    public void show(){
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3"));

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();


        cameraFirst = new OrthographicCamera();
        cameraSecond = new OrthographicCamera();


        //this is the two viewports
        viewportFirst = new StretchViewport(WORLD_WIDTH , WORLD_HEIGHT, cameraFirst);
        viewportSecond = new StretchViewport(WORLD_WIDTH , WORLD_HEIGHT, cameraSecond);

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

        PlayerAuxiliar.setHealth(10);
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

        movementBackground += delta * 20;

        game.batch.draw(fondo, 0, -movementBackground, WORLD_WIDTH, WORLD_HEIGHT);
        game.batch.draw(fondo, 0, 9f - movementBackground, WORLD_WIDTH, WORLD_HEIGHT);

        if (movementBackground >= 9f) {
            movementBackground = 0;
        }


        //calculate the oscillation
        oscilacion += delta * 0.5;

        cont += delta;

        if(cont > 0.5 && enemyTotal >= 0){
            cont = 0;
            enemyTotal -= 1;
            enemyObject = new ClassEnemy(100, 50, 4, movementEnemys, 3f, oscilacion);
            controllerMoreEnemys.addEnemy(enemyObject);
        }

        controllerMoreEnemys.movementEnemies(delta, oscilacion, game.batch, enemySprite, bulletsEnemy);



        bulletsPlayer.drawBulletsAndCollide(game.batch, balaImagen, controllerMoreEnemys);
        bulletsEnemy.drawBulletsEnemies(game.batch, balaImagen, colisionPlayer);


        if(PlayerAuxiliar.getHealth() >= 0) {
            //it's the player plane with his collision
            game.batch.draw(avion, movementPlayer.x, movementPlayer.y, 0.8f, 0.8f );
            colisionPlayer.set(movementPlayer.x + 0.4f - ((0.8f / 2) / 2) , movementPlayer.y, 0.8f / 2, 0.8f);
            newControler.controlsKeysShots(bulletsPlayer, movementPlayer);
            movementPlayer = newControler.controlsKeys(movementPlayer, delta);
        }



        bulletsEnemy.actualizarPantalla(delta, WORLD_WIDTH, WORLD_HEIGHT);
        bulletsPlayer.actualizarPantalla(delta, WORLD_WIDTH,WORLD_HEIGHT);

        game.batch.end();



        viewportSecond.apply();
        game.batch.setProjectionMatrix(cameraSecond.combined);

        game.batch.begin();

        game.batch.draw(backTexture, 0,0, WORLD_WIDTH,WORLD_HEIGHT);

        game.batch.end();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        viewportFirst.apply();
        shapeRenderer.setProjectionMatrix(cameraFirst.combined);



        if ( superCont > 0){
            superCont -= delta * 0.2;
        }else{
            superCont = 0;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0f, 0f, 0f, superCont));
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer.end();



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
