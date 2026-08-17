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
