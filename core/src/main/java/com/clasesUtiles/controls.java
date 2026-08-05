package com.clasesUtiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class controls {

    private float velocity = 10f;

    public void controlsKeysShots(ControladorBalas bulletsPlayer, Vector2 movementPlayer, Sound shotSound){
        //controls to shot oh hell na
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            bulletsPlayer.disparar(movementPlayer.x + 0.2f, movementPlayer.y, 1f,0f,12f);
            shotSound.play(0.2f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.N)){
            bulletsPlayer.disparar(movementPlayer.x  + 0.6f, movementPlayer.y, 1f,0f,12f);
            shotSound.play(0.2f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.B)){
            bulletsPlayer.disparar(movementPlayer.x + 0.4f, movementPlayer.y, 1f,0f,16f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)){
            velocity = 2f;
        }else{
            velocity = 8f;
        }
    }




    public Vector2 controlsKeys(Vector2 movement, Float delta){

        if (Gdx.input.isKeyPressed(Input.Keys.D) && movement.x < 14.5){
            movement.x += velocity * delta;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && movement.x > 0){
            movement.x -= velocity * delta;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && movement.y < 8){
            movement.y += velocity * delta;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && movement.y > 0){
            movement.y -= velocity * delta;

        }


        return movement;
    }




}
