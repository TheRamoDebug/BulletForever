package com.mijuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverOverlay {
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private float fadeAlpha = 0f;
    private Skin skin;
    private BitmapFont font;
    private TextButton retryButton;
    private TextButton menuButton;
    private boolean quiereReintentar = false;
    private boolean quiereMenu = false;
    private com.badlogic.gdx.graphics.g2d.SpriteBatch batch;

    public GameOverOverlay(Viewport viewport) {
        stage = new Stage(viewport);
        shapeRenderer = new ShapeRenderer();
        batch = new com.badlogic.gdx.graphics.g2d.SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(0.025f);
        skin = new Skin();
        skin.add("default-font", font);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        skin.add("default", buttonStyle);

        retryButton = new TextButton("REINTENTAR", skin);
        retryButton.setPosition(6.5f,4f);
        menuButton = new TextButton("MENU PRINCIPAL", skin);
        menuButton.setPosition(6.5f, 2.5f);

        stage.addActor(retryButton);
        stage.addActor(menuButton);

        retryButton.addListener(new ClickListener() {
        @Override
            public void clicked(InputEvent event, float x, float y){
            quiereReintentar = true;
        }});

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                quiereMenu = true;
            }
        });

    }
    public void render(float delta) {
        if(fadeAlpha < 0.6f) {
            fadeAlpha += delta * 0.5f;
        }
      Gdx.gl.glEnable(com.badlogic.gdx.graphics.GL20.GL_BLEND);
      Gdx.gl.glBlendFunc(com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA, com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA);
      shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
      shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
      shapeRenderer.setColor(new Color(0f, 0f, 0f, fadeAlpha));
      shapeRenderer.rect(0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
      shapeRenderer.end();


     batch.setProjectionMatrix(stage.getCamera().combined);
     batch.begin();
     font.draw(batch, "GAME OVER", 5.5f, 7f);
     batch.end();
     stage.act(delta);
     stage.draw();
    }

 public Stage getStage() {
        return stage;
 }

 public boolean seQuiereReintentar() {
        return quiereReintentar;
 }

 public boolean seQuiereIrAlMenu(){
        return quiereMenu;
 }

 public void reset() {
        fadeAlpha = 0f;
        quiereMenu = false;
        quiereReintentar = false;
 }

 public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
 }

 public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
        batch.dispose();
        shapeRenderer.dispose();
 }

}
