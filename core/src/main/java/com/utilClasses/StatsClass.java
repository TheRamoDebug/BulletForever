package com.utilClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;


public class StatsClass implements Disposable {
    private Stage stage;
    private Skin skin;
    private Table table;
    private BitmapFont bitmapFont;
    private Label text;
    private Label text2;
    private int i = Player.getCoins();

    public StatsClass(Viewport viewport, SpriteBatch batch) {
        stage = new Stage(viewport, batch);
        skin = new Skin(Gdx.files.internal("ui/star-soldier-ui.json"));
        table = new Table();
        table.setFillParent(true);
        table.top();

        bitmapFont = skin.getFont("title");
        bitmapFont.getData().setScale(150f, 50f);
        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        text = new Label("", skin);
        text.setFontScale(3);
        table.add(text).row();

        text2 = new Label("", skin);
        text2.setFontScale(3);
        table.add(text2).row();
        stage.addActor(table);
    }


    public void actu(){
        text.setText("COINS: " + i);
        text2.setText("SCORE: " + i * 10);

    }


    public void addI(){
        i += 1;
        Player.score();
    }


    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }



    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
