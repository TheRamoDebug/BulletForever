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

    public StatsClass(Viewport viewport, SpriteBatch batch) {
        stage = new Stage(viewport, batch);
        skin = new Skin(Gdx.files.internal("ui/star-soldier-ui.json"));
        table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(0);

        bitmapFont = skin.getFont("title");
        bitmapFont.getData().setScale(0.35f, 0.3f);
        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Label text = new Label("HELLO WORLD", skin);
        table.add(text);

        stage.addActor(table);
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
