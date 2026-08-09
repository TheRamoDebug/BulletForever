package io.github.com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mijuego.ScreenGameplay;
import com.mijuego.ScreenMenu;

public class Main extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    @Override
    public void create(){
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        this.setScreen(new ScreenMenu(this));
    }

    @Override
    public void render(){
        ScreenUtils.clear(0, 0, 0, 1);
        super.render();
    }



    @Override
    public void dispose(){
        super.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }

}
