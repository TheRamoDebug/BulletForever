package io.github.com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mijuego.PantallaInicio;

public class Main extends Game {

    public SpriteBatch batch;

    @Override
    public void create(){
        batch = new SpriteBatch();

        this.setScreen(new PantallaInicio(this));
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
    }

}
